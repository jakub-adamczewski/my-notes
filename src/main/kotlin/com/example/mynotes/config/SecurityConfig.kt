package com.example.mynotes.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.filter.OncePerRequestFilter
import org.zalando.problem.spring.web.advice.security.SecurityProblemSupport

@Configuration
@Import(SecurityProblemSupport::class)
class SecurityConfig : WebSecurityConfigurerAdapter() {

    @Autowired
    private lateinit var problemSupport: SecurityProblemSupport

    @Autowired
    @Qualifier("bearer_filter")
    private lateinit var bearerTokenFilter: OncePerRequestFilter

    @Value("\${server.servlet.context-path:''}")
    private lateinit var contextPath: String

    override fun configure(http: HttpSecurity) {
        http.authorizeRequests()
            .antMatchers(contextPath).permitAll()
            .antMatchers("$contextPath/health").permitAll()
            .anyRequest().authenticated()

        http.antMatcher("/**").authorizeRequests()
            .and()
            .csrf().disable()
            .addFilterBefore(bearerTokenFilter, UsernamePasswordAuthenticationFilter::class.java)
            .exceptionHandling()
            .authenticationEntryPoint(problemSupport)
            .accessDeniedHandler(problemSupport)
            .and()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
    }
}