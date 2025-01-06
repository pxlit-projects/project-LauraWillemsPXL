package be.pxl.services;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Post Service
 */

@SpringBootApplication
@EnableDiscoveryClient
public class PostServiceApplication
{
    public static void main( String[] args )
    {
        SpringApplication.run(PostServiceApplication.class, args);
    }
}
