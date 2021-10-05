package com.diplomacy.peaceindex.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;

// `GlobalMethodSecurityConfiguration`을 상속받고 사용할 방법을 `@EnableGlobalMethodSecurity`어노테이션 옵션으로 설정해준다.
// 이제 `BoardApiController`에서 `@Secured`어노테이션을 사용할 수 있다.
@Configuration
@EnableGlobalMethodSecurity(
        prePostEnabled = true,
        securedEnabled = true,
        jsr250Enabled = true)
public class MethodSecurityConfig
        extends GlobalMethodSecurityConfiguration {
}
