package com.toskey.cube.common.security.principal;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;

import java.util.Collection;
import java.util.HashMap;
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

    private final String gender;

    private final String mobile;

    private final String email;

    private final String dept;

    private final String post;

    private final String tenantId;

    private final Map<String, Object> attributes = new HashMap<>();

    public LoginUser(String id, String username, String password, String name, String userType,
                     String gender, String mobile, String email, String dept, String post,
                     String tenantId, boolean enabled, boolean accountNonExpired,
                     boolean credentialNonExpired, boolean accountNonLock,
                     Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialNonExpired, accountNonLock, authorities);
        this.id = id;
        this.name = name;
        this.userType = userType;
        this.gender = gender;
        this.mobile = mobile;
        this.email = email;
        this.dept = dept;
        this.post = post;
        this.tenantId = tenantId;
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
