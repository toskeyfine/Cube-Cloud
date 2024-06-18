package com.toskey.cube.common.core.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.toskey.cube.common.core.constant.DateTimeConstants;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.boot.web.servlet.filter.OrderedCharacterEncodingFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.datetime.standard.DateTimeFormatterRegistrar;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.charset.StandardCharsets;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.TimeZone;

/**
 * JacksonConfig
 *
 * @author toskey
 * @version 1.0
 */
@ConditionalOnClass(ObjectMapper.class)
@AutoConfigureBefore(JacksonAutoConfiguration.class)
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
public class JacksonConfiguration implements WebMvcConfigurer {

	private static final String ASIA_SHANGHAI = "Asia/Shanghai";

	@Bean
	@ConditionalOnMissingBean
	public Jackson2ObjectMapperBuilderCustomizer customizer() {
		return builder -> {
			builder.locale(Locale.CHINA);
			builder.timeZone(TimeZone.getTimeZone(ASIA_SHANGHAI));
			builder.simpleDateFormat(DateTimeConstants.NORM_DATETIME_PATTERN);
			builder.serializerByType(Long.class, ToStringSerializer.instance);
			builder.modules(new JavaTimeModule());
		};
	}

	/**
	 * <ul>
	 * <li>HH:mm:ss -> LocalTime</li>
	 * <li>yyyy-MM-dd -> LocalDate</li>
	 * <li>yyyy-MM-dd HH:mm:ss -> LocalDateTime</li>
	 * </ul>
	 */
	@Override
	public void addFormatters(@NonNull FormatterRegistry registry) {
		DateTimeFormatterRegistrar registrar = new DateTimeFormatterRegistrar();
		registrar.setTimeFormatter(DateTimeFormatter.ofPattern(DateTimeConstants.NORM_TIME_PATTERN));
		registrar.setDateFormatter(DateTimeFormatter.ofPattern(DateTimeConstants.NORM_DATE_PATTERN));
		registrar.setDateTimeFormatter(DateTimeFormatter.ofPattern(DateTimeConstants.NORM_DATETIME_PATTERN));
		registrar.registerFormatters(registry);
	}

	/**
	 * 避免form 提交 context-type 不规范中文乱码
	 * @return Filter
	 */
	@Bean
	public OrderedCharacterEncodingFilter characterEncodingFilter() {
		OrderedCharacterEncodingFilter filter = new OrderedCharacterEncodingFilter();
		filter.setEncoding(StandardCharsets.UTF_8.name());
		filter.setForceEncoding(true);
		filter.setOrder(Ordered.HIGHEST_PRECEDENCE);
		return filter;
	}

}
