package com.example.springboot.wiremock.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.springboot.wiremock.model.Person;

@Service
public class PersonServiceImpl implements IPersonService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${person.remote.resource.url}")
    private String remoteUserUrl;

    @Override
    public Person fetchPersonRemotely(long personId) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer Test");
        HttpEntity<Person> entity = new HttpEntity<>(headers);

        ResponseEntity<Person> response = restTemplate.exchange(remoteUserUrl + "/person/id/{id}", HttpMethod.GET, entity, new ParameterizedTypeReference<Person>() {
        }, personId);

        return response.getBody();
    }

}
