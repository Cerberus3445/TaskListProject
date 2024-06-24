package com.in28minutes.rest.webservice.in28minutes.app.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.List;

@Entity(name = "user_details") //меняем название сущности в таблице
public class User {

    @Id
    @GeneratedValue
    private Integer id;

    @Size(min = 2, message = "Name should have atleast 2 characters")
    //@JsonProperty("new_name")
    private String name;

    @Past(message = "Birth day should be in the past") //должна быть в прошлом
    //@JsonProperty("birth_day")
    private LocalDate birthDay;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Post> postList;

    public User(Integer id, String name, LocalDate birthDay) {
        this.id = id;
        this.name = name;
        this.birthDay = birthDay;
    }

    public User() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(LocalDate birthDay) {
        this.birthDay = birthDay;
    }

    public List<Post> getPostList() {
        return postList;
    }

    public void setPostList(List<Post> postList) {
        this.postList = postList;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", birthDay=" + birthDay +
                '}';
    }
}
