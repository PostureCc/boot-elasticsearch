package com.chan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

//@EnableElasticsearchRepositories(basePackages = "com.chan.repo")
@SpringBootApplication
public class BootElasticsearchApplication {

    public static void main(String[] args) {
        System.setProperty("es.set.netty.runtime.available.processors", "false");
        SpringApplication.run(BootElasticsearchApplication.class, args);
    }

}
