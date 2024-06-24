package com.in28minutes.rest.webservice.in28minutes.app.controller;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.in28minutes.rest.webservice.in28minutes.app.entity.SomeBean;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class FilteringController {

    @GetMapping("/filtering")
    public MappingJacksonValue filtering(){
        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(new SomeBean("value1", "value2", "value3"));

        SimpleBeanPropertyFilter simpleBeanPropertyFilter = SimpleBeanPropertyFilter.filterOutAllExcept("field1", "field2"); //поля, которые останутся после фильтрации
        FilterProvider filters = new SimpleFilterProvider().addFilter("SomeBeanFilter", simpleBeanPropertyFilter); //ищет фильт, указанный в классе аннотацией @JsonFilter
        mappingJacksonValue.setFilters(filters);
        return mappingJacksonValue;
    }

    @GetMapping("/filtering-list")
    public MappingJacksonValue filteringList(){
       List<SomeBean> list = List.of(new SomeBean("value1", "value2", "value3"), new SomeBean("value4", "value5", "value6"));
        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(list);

        SimpleBeanPropertyFilter simpleBeanPropertyFilter = SimpleBeanPropertyFilter.filterOutAllExcept("field1", "field3");
        FilterProvider filters = new SimpleFilterProvider().addFilter("SomeBeanFilter", simpleBeanPropertyFilter);
        mappingJacksonValue.setFilters(filters);
        return mappingJacksonValue;
    }
}
