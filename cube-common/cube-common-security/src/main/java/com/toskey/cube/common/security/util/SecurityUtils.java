package com.toskey.cube.common.security.util;

import com.toskey.cube.common.security.principal.LoginUser;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * 身份认证工具类
 *
 * @author toskey
 * @version 1.0.0
 */
public class SecurityUtils {

	/**
	 * 获取Authentication
	 */
	public static Authentication getAuthentication() {
		return SecurityContextHolder.getContext().getAuthentication();
	}

	/**
	 * 获取用户
	 *
	 * @param authentication
	 *
	 * @return OAuth2User
	 */
	public static LoginUser getUser(Authentication authentication) {
		if (authentication != null) {
			Object principal = authentication.getPrincipal();
			if (principal instanceof LoginUser) {
				return (LoginUser) principal;
			}
		}
		return null;
	}

	/**
	 * 获取用户
	 */
	public static LoginUser getUser() {
		Authentication authentication = getAuthentication();
		return getUser(authentication);
	}

	public static String getUsername() {
		return Objects.nonNull(getUser()) ? getUser().getUsername() : null;
	}

	public static String getUserId() {
		return Objects.nonNull(getUser()) ? getUser().getId() : null;
	}

	/**
	 * 获取用户角色信息
	 * @return 角色集合
	 */
	public static List<String> getRoles() {
		Authentication authentication = getAuthentication();
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

		List<String> roleIds = new ArrayList<>();
		authorities.stream().filter(granted -> StringUtils.startsWith(granted.getAuthority(), "ROLE_"))
				.forEach(granted -> {
					String id = StringUtils.removeStart(granted.getAuthority(), "ROLE_");
					roleIds.add(id);
				});
		return roleIds;
	}

}