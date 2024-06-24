package com.in28minutes.rest.webservice.in28minutes.app.repository;

import com.in28minutes.rest.webservice.in28minutes.app.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

}
