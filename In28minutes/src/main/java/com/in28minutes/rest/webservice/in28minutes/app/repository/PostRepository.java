package com.in28minutes.rest.webservice.in28minutes.app.repository;

import com.in28minutes.rest.webservice.in28minutes.app.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {

}
