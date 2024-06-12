package com.toskey.cube.common.resource.server.principal;

import com.toskey.cube.sas.interfaces.dto.RoleDTO;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * LoginUser
 *
 * @author lis
 * @version 1.0
 * @description TODO
 * @date 2024/6/6 10:03
 */
@Getter
public class LoginUser extends User implements OAuth2AuthenticatedPrincipal {

    private final String id;

    private final String name;

    private final String userType;

    private final int age;

    private final String gender;

    private final String mobile;

    private final String email;

    private final String avatar;

    private final String dept;

    private final String post;

    private final List<RoleDTO> roles;

    private final Map<String, Object> attributes = new HashMap<>();

    public LoginUser(String id, String username, String password, String name, String userType,
                     int age, String gender, String mobile, String email, String avatar,
                     String dept, String post, List<RoleDTO> roles, boolean enabled,
                     boolean accountNonExpired, boolean credentialNonExpired, boolean accountNonLock,
                     Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialNonExpired, accountNonLock, authorities);
        this.id = id;
        this.name = name;
        this.userType = userType;
        this.age = age;
        this.gender = gender;
        this.mobile = mobile;
        this.email = email;
        this.avatar = avatar;
        this.dept = dept;
        this.post = post;
        this.roles = roles;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return this.attributes;
    }

    @Override
    public String getName() {
        return this.name;
    }
}
