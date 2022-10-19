package com.fatih;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(SpringExtension.class)
@EnableConfigurationProperties(BusinessConfiguration.class)
@ContextConfiguration(classes = BusinessConfiguration.class,
        initializers = ConfigDataApplicationContextInitializer.class)
class BusinessConfigurationTest {
    @Autowired
    private BusinessConfiguration businessConfiguration;

    @Test
    void test_it_should_load_seeds_per_pit() {
        // Assert
        assertThat(businessConfiguration.getSeedPerPit()).isEqualTo(6);
    }

}
