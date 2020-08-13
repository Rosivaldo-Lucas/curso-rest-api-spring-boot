package com.ufpb.crdb.config;

import com.ufpb.crdb.filters.TokenFilter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterJWTConfig {
  
  @Bean
  public FilterRegistrationBean<TokenFilter> filterJWT() {
    FilterRegistrationBean<TokenFilter> filterRB = new FilterRegistrationBean<TokenFilter>();
  
    filterRB.setFilter(new TokenFilter());
    filterRB.addUrlPatterns("/usuarios", "/disciplinas/*");

    return filterRB;
	}
}
