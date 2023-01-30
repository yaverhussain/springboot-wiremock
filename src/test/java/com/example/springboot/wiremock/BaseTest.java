package com.example.springboot.wiremock;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.Options;

@ContextConfiguration(initializers = BaseTest.Initializer.class)
@ExtendWith(SpringExtension.class)
@ActiveProfiles(profiles = { "integration-test" })
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = {"management.server.port=0"})
public class BaseTest {
    
    
    @Autowired
    protected ObjectMapper objectMapper;
    
    public static WireMockServer wireMockServer;
    
    static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

        @Override
        public void initialize(ConfigurableApplicationContext context) {

            
            wireMockServer = new WireMockServer(Options.DYNAMIC_PORT);
            wireMockServer.start();
            
            TestPropertyValues.of(
                    "person.remote.resource.url=" + wireMockServer.baseUrl()
                    )
            .applyTo(context.getEnvironment());
        }
    }
}
