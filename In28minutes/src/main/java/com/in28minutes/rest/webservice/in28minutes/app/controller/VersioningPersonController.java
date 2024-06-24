package com.in28minutes.rest.webservice.in28minutes.app.controller;

import com.in28minutes.rest.webservice.in28minutes.app.entity.Name;
import com.in28minutes.rest.webservice.in28minutes.app.entity.PersonV1;
import com.in28minutes.rest.webservice.in28minutes.app.entity.PersonV2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VersioningPersonController { //типы версионирования

    @GetMapping("/v1/person")
    public PersonV1 getFirstVersionOfPerson(){
        return new PersonV1("Bob Charlie");
    }

    @GetMapping("/v2/person")
    public PersonV2 getSecondVersionOfPerson(){
        return new PersonV2(new Name("Bob", "Charlie"));
    }

    @GetMapping(path = "/person", params = "version=1")
    public PersonV1 getFirstVersionOfPersonWithParameter(){
        return new PersonV1("Bob Charlie");
    }

    @GetMapping(path = "/person", params = "version=2")
    public PersonV2 getSecondVersionOfPersonWithParameter(){
        return new PersonV2(new Name("Bob", "Charlie"));
    }

    @GetMapping(path = "/person", headers = "X-API-VERSION=1")
    public PersonV1 getFirstVersionOfPersonWithHeader(){
        return new PersonV1("Bob Charlie");
    }

    @GetMapping(path = "/person", headers = "vesss=2") //header можно любой ставить
    public PersonV2 getSecondVersionOfPersonWithHeader(){
        return new PersonV2(new Name("Bob", "Charlie"));
    }

    @GetMapping(path = "/person", produces = "application/vnd.company.app-v1+json") //в Accept кладем значение в producer
    public PersonV1 getFirstVersionOfPersonAccept(){
        return new PersonV1("Bob Charlie");
    }

    @GetMapping(path = "/person", produces = "application/vnd.company.app-v2+json")
    public PersonV2 getSecondVersionOfPersonAccept(){
        return new PersonV2(new Name("Bob", "Charlie"));
    }
}
