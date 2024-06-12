package com.toskey.cube.common.core.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * TreeUtils
 *
 * @author toskey
 * @version 1.0
 * @since 2024/6/5
 */
public class TreeUtils {

    /**
     * Build Tree node list without root key
     *
     * @param list data list
     * @param key child node get key function
     * @param treeConnectKey parent node get connection key function
     * @param children parent node get children function
     * @param consumer parent node set children function
     *
     * @return tree node list
     */
    public <T> List<T> buildTree(List<T> list, Function<T, ?> key, Function<T, ?> treeConnectKey,
                                 Function<T, Collection<T>> children, Consumers<T, T> consumer) {
        List<T> tree = new ArrayList<>();
        for (var node : list) {
            boolean isChild = false;
            for (var m : list) {
                if (treeConnectKey.apply(node).equals(key.apply(m))) {
                    if (children.apply(m) == null) {
                        consumer.accept(m, new ArrayList<>(List.of(node)));
                    } else {
                        List<T> childrenList = (List<T>) children.apply(m);
                        childrenList.add(node);
                    }
                    isChild = true;
                    break;
                }
            }
            if (!isChild) {
                tree.add(node);
            }
        }
        return tree;
    }

    /**
     * Build Tree node list with root node key
     *
     * @param list              data list
     * @param key               leaf node get key function
     * @param treeConnectKey    parent node get connection key function
     * @param treeSortKey       sort key
     * @param consumer          parent node set children function
     *
     * @return tree node list
     *
     * @code List tree = TreeUtils.buildTree(menuList, Menu::getKey, Menu::getParentKey, "0", Menu::getSortNum, Menu::SetChildren)
     */
    public <T> List<T> buildTreeByStream(List<T> list, Function<T, ?> key, Function<T, ?> treeConnectKey, String rootPId,
                                         Function<T, ? extends Comparable> treeSortKey, Consumers<T, T> consumer) {
        if (CollectionUtils.isEmpty(list)) {
            return Collections.emptyList();
        }

        List<T> tree = Collections.synchronizedList(new ArrayList<>());
        final Map<?, List<T>> collection = list.parallelStream().collect(Collectors.groupingBy(treeConnectKey));
        list.parallelStream().filter(m -> {
            final boolean flag = !StringUtils.equals((String) treeConnectKey.apply(m), rootPId);
            if (!flag) {
                tree.add(m);
            }
            return flag;
        }).forEach(m -> {
            if (treeSortKey != null) {
                collection.get(key.apply(m)).sort(Comparator.comparing(treeSortKey));
            }
            consumer.accept(m, collection.get(key.apply(m)));
        });
        if (treeSortKey != null) {
            tree.sort(Comparator.comparing(treeSortKey));
            return (List<T>) tree.parallelStream()
                    .peek(b -> {
                        collection.get(key.apply(b)).sort(Comparator.comparing(treeSortKey));
                        consumer.accept(b, collection.get(key.apply(b)));
                    })
                    .toList();
        } else {
            return (List<T>) tree.parallelStream()
                    .peek(b -> consumer.accept(b, collection.get(key.apply(b))))
                    .toList();
        }
    }

    @FunctionalInterface
    public interface Consumers<M, N> {

        void accept(M m, List<N> n);

    }

}
