package by.jawh.profilemicroservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ProfileMicroserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProfileMicroserviceApplication.class, args);
    }

}
