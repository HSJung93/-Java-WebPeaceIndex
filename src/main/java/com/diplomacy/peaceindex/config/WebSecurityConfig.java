package com.diplomacy.peaceindex.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import javax.sql.DataSource;

// `@Configuration` 어노테이션을 추가해주면 `userDetailsService()`에서 `@Bean` 어노테이션을 사용할 수 있다.
// 그러면 다른 클래스에서 `@AutoWired` 어노테이션을 사용해서 이 메소드를 사용할 수 있다.
// 현 프로젝트에서는 마리아 DB에 만든 사용자 테이블에서 사용자를 조회해서 로그인 처리를 할 것이므로 `userDetailsService()`가 필요하지 않다.
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    // application.properties에 정의된 데이터 소스를 사용하게 한다. 이 데이터 소스를 `jdbcAuthentication().dataSource()`에 전달하면 알아서 인증처리를 해준다.
    @Autowired
    private DataSource dataSource;

    // `configure()` 메소드에서는 `HttpSecurity`를 인자로 받아서 보안 설정을 해준다.
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                // `.csrf.disable()`로 테스트를 가능하게 한다.
                .csrf().disable()
                .authorizeRequests()
                    // `.permitAll()`를 `antMatchers`에 더하여 api 테스트를 가능하게 한다.
                    .antMatchers("/", "/account/register", "/css/**", "/api/**").permitAll()
                    .anyRequest().authenticated()
                    .and()
                // 로그인 페이지 설정
                .formLogin()
                    // 다른 페이지를 가면 자동으로 템플릿에 맞춘 로그인 페이지로 리다이렉트하도록 한다.
                    .loginPage("/account/login")
                    .permitAll()
                    .and()
                .logout()
                    .permitAll();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth)
            throws Exception {
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .passwordEncoder(passwordEncoder())
                .usersByUsernameQuery("select username, password, enabled "
                        + "from user "
                        + "where username = ?")
                .authoritiesByUsernameQuery("select u.username, r.name "
                        + "from user_role ur inner join user u on ur.user_id = u.id "
                        + "inner join role r on ur.role_id = r.id "
                        + "where u.username = ?");
    }
    // Authentication은 인증으로 로그인 관련된 처리, Authorization은 로그인 이후에 권한 처리를 의미한다.
// OneToOne : 사용자(user) - 사용자 상세 정보(user_detail), OneToMany: 사용자(user) - 여러 게시글(board), ManyToOne: board - user, ManyToMany: user - role 사용자와 권한 테이블을 연결하는 매핑 테이블 필요.

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
