package com.fatih;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("business")
@Getter
@Setter
public class BusinessConfiguration {
    private Integer seedPerPit;
}
