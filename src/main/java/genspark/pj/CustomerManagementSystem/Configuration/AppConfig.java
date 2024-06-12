package genspark.pj.CustomerManagementSystem.Configuration;

import genspark.pj.CustomerManagementSystem.Entity.Customer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class AppConfig {

    @Bean
    @Scope("prototype")
    public Customer customer() {
        return new Customer();
    }
}