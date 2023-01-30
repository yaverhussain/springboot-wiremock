package com.example.springboot.wiremock.services;

import com.example.springboot.wiremock.model.Person;

public interface IPersonService {

    Person fetchPersonRemotely(long personId);

}
