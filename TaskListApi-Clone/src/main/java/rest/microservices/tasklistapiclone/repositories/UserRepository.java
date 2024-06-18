package rest.microservices.tasklistapiclone.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rest.microservices.tasklistapiclone.domain.user.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByEmail(String email);
}
