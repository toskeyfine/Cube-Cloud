package com.toskey.cube.gateway.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * CaptchaProperties
 *
 * @author lis
 * @version 1.0
 * @description TODO
 * @date 2024/7/11 17:08
 */
@ConfigurationProperties("cube.gateway.captcha")
public class CaptchaProperties {

    private boolean enabled;

    private List<CaptchaUrl> excludeUrls = new ArrayList<>();

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public List<CaptchaUrl> getExcludeUrls() {
        return excludeUrls;
    }

    public void setExcludeUrls(List<CaptchaUrl> excludeUrls) {
        this.excludeUrls = excludeUrls;
    }

    public static class CaptchaUrl {

        private String url;

        private boolean enabled;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public boolean isEnabled() {
            return enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }
    }
}
