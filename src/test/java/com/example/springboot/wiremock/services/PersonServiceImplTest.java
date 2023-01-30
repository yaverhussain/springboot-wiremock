package com.example.springboot.wiremock.services;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import com.example.springboot.wiremock.BaseTest;
import com.example.springboot.wiremock.model.Person;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class PersonServiceImplTest extends BaseTest {

    @Autowired
    private IPersonService personService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void TestRemotePersonFetch() throws JsonProcessingException {
        long personId = 24L;

        Person mockPersonResponse = new Person();
        mockPersonResponse.setAge(24);
        mockPersonResponse.setFirstName("Alan");
        mockPersonResponse.setLastName("lamb");

        wireMockServer.stubFor(get(urlEqualTo("/person/id/" + personId)).willReturn(aResponse()
                .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).withBody(objectMapper.writeValueAsString(mockPersonResponse))));

        Person person = personService.fetchPersonRemotely(personId);
        assertNotNull(person);
        assertEquals(mockPersonResponse.getAge(), person.getAge());
        assertEquals(mockPersonResponse.getFirstName(), person.getFirstName());
        assertEquals(mockPersonResponse.getLastName(), person.getLastName());
    }
}
