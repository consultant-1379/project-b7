package com.ericsson.projectb7.configuration;

import com.offbytwo.jenkins.JenkinsServer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.URISyntaxException;

@Configuration
@Component
public class JenkinsServiceConfiguration {

    @Value("${jenkins.url}")
    private String jenkinsUrl;

    @Value("${jenkins.username}")
    private String jenkinsUserName;

    @Value("${jenkins.passwordOrToken}")
    private String jenkinsPassword;

    @Bean
    @Qualifier("jenkinsServerConfiguration")
    public JenkinsServer getJenkinsService() throws URISyntaxException {
       return new JenkinsServer(new URI(jenkinsUrl),jenkinsUserName,jenkinsPassword);
    }
}
