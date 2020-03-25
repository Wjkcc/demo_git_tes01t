package com.example.demo_git_tes01t;

import com.example.domin.Employee;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DemoGitTes01tApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoGitTes01tApplication.class, args);
    }


    @Bean
    public Employee employee() {
        return new Employee();
    }
}
