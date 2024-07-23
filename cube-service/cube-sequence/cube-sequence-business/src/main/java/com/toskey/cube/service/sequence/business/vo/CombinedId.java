package com.toskey.cube.service.sequence.business.vo;

import com.toskey.cube.service.sequence.business.constant.enums.SequenceCodeType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * 组合ID缓存
 *
 * @author toskey
 * @version 1.0.0
 */
@Getter
@Setter
@AllArgsConstructor(staticName = "of")
public class CombinedId implements Serializable {

    private boolean hasSection;

    private List<CombinedProperty> properties;

    @Getter
    @AllArgsConstructor(staticName = "of")
    public static class CombinedProperty implements Serializable {


        private SequenceCodeType type;

        private String content;

        private int ordered;

    }

}
