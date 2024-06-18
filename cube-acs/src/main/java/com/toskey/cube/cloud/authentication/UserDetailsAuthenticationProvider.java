package com.toskey.cube.cloud.authentication;

import com.toskey.cube.cloud.exception.SmsCodeNotMatchException;
import com.toskey.cube.common.core.constant.CacheConstants;
import com.toskey.cube.common.core.constant.SecurityConstants;
import com.toskey.cube.common.core.exception.FrameworkException;
import com.toskey.cube.common.core.util.SpringContextHolder;
import com.toskey.cube.common.core.util.StringUtils;
import com.toskey.cube.common.core.util.WebUtils;
import com.toskey.cube.common.security.service.PasswordUserDetailsServiceImpl;
import com.toskey.cube.common.security.service.SmsUserDetailsServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.util.Assert;

/**
 * 重写DaoAuthenticationProvider
 * 实现短信验证码校验
 *
 * @author toskey
 * @version 1.0.0
 */
public class UserDetailsAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

    private static final String USER_NOT_FOUND_PASSWORD = "userNotFoundPassword";
    private PasswordEncoder passwordEncoder;
    private volatile String userNotFoundEncodedPassword;
    private UserDetailsService userDetailsService;
    private UserDetailsPasswordService userDetailsPasswordService;

    private RedisTemplate<String, Object> redisTemplate;

    public UserDetailsAuthenticationProvider() {
        this(PasswordEncoderFactories.createDelegatingPasswordEncoder());
    }

    public UserDetailsAuthenticationProvider(PasswordEncoder passwordEncoder) {
        this.setPasswordEncoder(passwordEncoder);
    }

    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        if (authentication.getCredentials() == null) {
            this.logger.debug("Failed to authenticate since no credentials provided");
            throw new BadCredentialsException(this.messages.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
        } else {
            HttpServletRequest request = WebUtils.getRequest();
            if (request == null) {
                this.logger.debug("Failed to find request.");
                throw new FrameworkException("Failed to find request.");
            }
            // 短信验证码模式只处理验证码
            String grantType = request.getParameter(OAuth2ParameterNames.GRANT_TYPE);
            if (SecurityConstants.GRANT_TYPE_SMS.equals(grantType)) {
                String key = CacheConstants.CACHE_LOGIN_SMS_CODE_PREFIX + authentication.getPrincipal();
                String code = (String) redisTemplate.opsForValue().get(key);
                if (!StringUtils.equals(code, (String) authentication.getCredentials())) {
                    throw new SmsCodeNotMatchException("sms code is not matches.");
                }
                redisTemplate.delete(key);
            } else {
                // 默认密码校验
                String presentedPassword = authentication.getCredentials().toString();
                if (!this.passwordEncoder.matches(presentedPassword, userDetails.getPassword())) {
                    this.logger.debug("Failed to authenticate since password does not match stored value");
                    throw new BadCredentialsException(this.messages.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
                }
            }
        }
    }

    protected void doAfterPropertiesSet() {
        Assert.notNull(this.userDetailsService, "A UserDetailsService must be set");
    }

    @Override
    protected final UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        this.prepareTimingAttackProtection();
        HttpServletRequest request = WebUtils.getRequest();
        if (request == null) {
            throw new FrameworkException("Failed to find request.");
        }
        // 根据不同的认证类型，获取对应的UserDetailsService实现
        String grantType = request.getParameter(OAuth2ParameterNames.GRANT_TYPE);
        if (StringUtils.equals(SecurityConstants.GRANT_TYPE_SMS, grantType)) {
            this.userDetailsService = SpringContextHolder.getBean(SmsUserDetailsServiceImpl.class);
        } else if (StringUtils.equals(AuthorizationGrantType.PASSWORD.getValue(), grantType)) {
            this.userDetailsService = SpringContextHolder.getBean(PasswordUserDetailsServiceImpl.class);
        } else {
            this.userDetailsService = null;
        }
        try {
            UserDetails loadedUser = this.getUserDetailsService().loadUserByUsername(username);
            if (loadedUser == null) {
                throw new InternalAuthenticationServiceException("UserDetailsService returned null, which is an interface contract violation");
            } else {
                return loadedUser;
            }
        } catch (UsernameNotFoundException ex) {
            this.mitigateAgainstTimingAttack(authentication);
            throw ex;
        } catch (InternalAuthenticationServiceException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new InternalAuthenticationServiceException(ex.getMessage(), ex);
        }
    }

    protected Authentication createSuccessAuthentication(Object principal, Authentication authentication, UserDetails user) {
        boolean upgradeEncoding = this.userDetailsPasswordService != null && this.passwordEncoder.upgradeEncoding(user.getPassword());
        if (upgradeEncoding) {
            String presentedPassword = authentication.getCredentials().toString();
            String newPassword = this.passwordEncoder.encode(presentedPassword);
            user = this.userDetailsPasswordService.updatePassword(user, newPassword);
        }

        return super.createSuccessAuthentication(principal, authentication, user);
    }

    private void prepareTimingAttackProtection() {
        if (this.userNotFoundEncodedPassword == null) {
            this.userNotFoundEncodedPassword = this.passwordEncoder.encode(USER_NOT_FOUND_PASSWORD);
        }

    }

    private void mitigateAgainstTimingAttack(UsernamePasswordAuthenticationToken authentication) {
        if (authentication.getCredentials() != null) {
            String presentedPassword = authentication.getCredentials().toString();
            this.passwordEncoder.matches(presentedPassword, this.userNotFoundEncodedPassword);
        }

    }

    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        Assert.notNull(passwordEncoder, "passwordEncoder cannot be null");
        this.passwordEncoder = passwordEncoder;
        this.userNotFoundEncodedPassword = null;
    }

    protected PasswordEncoder getPasswordEncoder() {
        return this.passwordEncoder;
    }

    protected UserDetailsService getUserDetailsService() {
        return this.userDetailsService;
    }

    public void setUserDetailsPasswordService(UserDetailsPasswordService userDetailsPasswordService) {
        this.userDetailsPasswordService = userDetailsPasswordService;
    }

    public void setRedisTemplate(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }
}
