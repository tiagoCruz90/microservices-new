package config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity serverHttpSecurity) {
       serverHttpSecurity.csrf(ServerHttpSecurity.CsrfSpec::disable) //we are only using postman to test the api
               .authorizeExchange(exchange -> exchange.pathMatchers("/eureka/**") //allow eureka to be accessed without authentication
                       .permitAll() //allow all
                       .anyExchange() //any other request
                       .authenticated()) //must be authenticated
               .oauth2ResourceServer(oAuth2ResourceServer -> oAuth2ResourceServer.jwt(Customizer.withDefaults()) ); //use jwt
        return serverHttpSecurity.build();
    }
}
