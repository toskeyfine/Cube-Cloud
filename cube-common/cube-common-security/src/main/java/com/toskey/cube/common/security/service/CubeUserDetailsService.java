package com.toskey.cube.common.security.service;

import com.toskey.cube.common.core.constant.CommonConstants;
import com.toskey.cube.common.core.constant.SecurityConstants;
import com.toskey.cube.common.security.principal.LoginUser;
import com.toskey.cube.service.sas.interfaces.dto.UserDTO;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.core.AuthorizationGrantType;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * CubeUserDetailsService
 *
 * @author lis
 * @version 1.0
 * @description TODO
 * @date 2024/6/6 10:49
 */
public interface CubeUserDetailsService extends UserDetailsService {

    default UserDetails buildUserDetails(UserDTO userDTO) {
        Set<String> authoritySet = new HashSet<>();
        if (userDTO.getRoleIds() != null && userDTO.getRoleIds().length > 0) {
            Arrays.stream(userDTO.getRoleIds()).forEach(role -> authoritySet.add(SecurityConstants.AUTHORITY_ROLE_PREFIX + role));
            authoritySet.addAll(Arrays.asList(userDTO.getPermissions()));
        }
        return new LoginUser(userDTO.getId(), userDTO.getUsername(), "{bcrypt}" + userDTO.getPassword(),
                userDTO.getName(), userDTO.getUserType(), userDTO.getGender(), userDTO.getMobile(),
                userDTO.getEmail(), userDTO.getDeptId(), userDTO.getPostId(), userDTO.getTenantId(),
                true, true, true,
                CommonConstants.USER_STATUS_NORMAL.equals(userDTO.getStatus()),
                AuthorityUtils.createAuthorityList(authoritySet.toArray(new String[0])));
    }

}
