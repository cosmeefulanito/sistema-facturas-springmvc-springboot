package com.springmvcjpa.primerproyectospringmvcyjpa;

import com.springmvcjpa.primerproyectospringmvcyjpa.authHandler.LoginSucessHandler;
import com.springmvcjpa.primerproyectospringmvcyjpa.models.service.JpaUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@EnableGlobalMethodSecurity(securedEnabled = true)
@Configuration
public class SpringSecurityConfig {
    @Autowired
    private LoginSucessHandler successHandler;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    //@Autowired
    //private DataSource dataSource;

    @Autowired
    private JpaUserDetailsService userDetailsService;


    /*
    @Bean
    public UserDetailsService userDetailsService()throws Exception{

        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User
                .withUsername("user")
                .password(passwordEncoder.encode("user"))
                .roles("USER")
                .build());
        manager.createUser(User
                .withUsername("admin")
                .password(passwordEncoder.encode("admin"))
                .roles("ADMIN","USER")
                .build());
        manager.createUser(User.withUsername("andres")
                .password(passwordEncoder.encode("andres"))
                .roles("ADMIN", "USER")
                .build());

        return manager;
    }
     */




    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/", "/css/**", "/js/**", "/images/**", "/listar", "/locale").permitAll()
                /*.antMatchers("/ver/**").hasAnyRole("USER")
                .antMatchers("/uploads/**").hasAnyRole("USER")
                .antMatchers("/form/**").hasAnyRole("ADMIN")
                .antMatchers("/eliminar/**").hasAnyRole("ADMIN")
                .antMatchers("/factura/**").hasAnyRole("ADMIN")*/
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .successHandler(successHandler)
                    .loginPage("/login")
                    .permitAll()
                .and()
                .logout().permitAll()
                .and()
                .exceptionHandling().accessDeniedPage("/error_403");
        return http.build();
    }



    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder build) throws Exception{
        //JPA
        build.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);

        //JDBC
        /*build.jdbcAuthentication()
                .dataSource(dataSource)
                .passwordEncoder(passwordEncoder)
                .usersByUsernameQuery("select username, password, enabled from users where username = ?")
                .authoritiesByUsernameQuery("select u.username, a.authority from authorities a inner join users u ON (a.user_id = u.id) where u.username=?");

         */
    }


}
