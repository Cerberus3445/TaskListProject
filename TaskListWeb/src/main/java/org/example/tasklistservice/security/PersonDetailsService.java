package org.example.tasklistservice.security;

import org.example.tasklistservice.client.UserRestClient;
import org.example.tasklistservice.domain.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class PersonDetailsService implements UserDetailsService {

    private final UserRestClient userRestClient;

    @Autowired
    public PersonDetailsService(@Lazy UserRestClient userRestClient) {
        this.userRestClient = userRestClient;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User person = userRestClient.findByEmail(username);

        if (person == null)
            throw new UsernameNotFoundException("User not found");

        return new PersonDetails(person);
    }
}