package org.alongclub.awalong;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class UrpApplication {

    public static void main(String[] args) {
        SpringApplication.run(UrpApplication.class, args);
    }
}