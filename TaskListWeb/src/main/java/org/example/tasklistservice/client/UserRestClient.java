package org.example.tasklistservice.client;
import feign.FeignException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.example.tasklistservice.domain.user.User;
import org.example.tasklistservice.dto.PasswordDto;
import org.example.tasklistservice.dto.UserDto;
import org.example.tasklistservice.exception.ServerException;
import org.example.tasklistservice.exception.UserException;
import org.example.tasklistservice.proxy.FeignProxy;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
@RequiredArgsConstructor
@Component
public class UserRestClient {

    private final ModelMapper modelMapper;

    private final PasswordEncoder passwordEncoder;

    private final Logger logger = LoggerFactory.getLogger(UserRestClient.class);

    private final FeignProxy feignProxy;

    //@CircuitBreaker(name = "default", fallbackMethod = "hardcodedResponse")
    public User getUser(int id){
        logger.info("Get user {}", id);
        try {
            UserDto userDto = feignProxy.getUser(id);
            return modelMapper.map(userDto, User.class);
        } catch (FeignException e){
            throw new UserException(e.getMessage());
        }
    }

    //@CircuitBreaker(name = "default", fallbackMethod = "hardcodedResponse")
    public void createUser(User user){
        logger.info("Create user {}", user);
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            UserDto userDto = modelMapper.map(user, UserDto.class);
            feignProxy.createUser(userDto);
        } catch (FeignException e){
            throw new UserException(e.getMessage());
        }
    }

    //@CircuitBreaker(name = "default", fallbackMethod = "hardcodedResponse")
    public void updateUser(User user, int id){
        logger.info("Update user {} with id {}", user, id);
        try {
            User getUser = getUser(id);
            user.setPassword(getUser.getPassword());
            user.setRole(getUser.getRole());
            UserDto userDto = modelMapper.map(user, UserDto.class);
            feignProxy.updateUser(id, userDto);
        } catch (FeignException e){
            throw new UserException(e.getMessage());
        }
    }

    //@CircuitBreaker(name = "default", fallbackMethod = "hardcodedResponse")
    public void deleteUser(int id){
        logger.info("Delete user with {}", id);
        try {
            feignProxy.deleteUser(id);
        } catch (FeignException e){
            throw new UserException(e.getMessage());
        }
    }

    //@CircuitBreaker(name = "default", fallbackMethod = "hardcodedResponse")
    public User findByEmail(String email) {
        logger.info("Find user by email {}", email);
        try {
            UserDto userDto = feignProxy.getUserByEmail(email);
            return modelMapper.map(userDto, User.class);
        } catch (FeignException e){
            throw new UserException(e.getMessage());
        }
    }

    //@CircuitBreaker(name = "default", fallbackMethod = "hardcodedResponse")
    public void updatePassword(int id, String password){
        logger.info("Update password for user with {} id and password {}", id, password);
        try {
            PasswordDto passwordDto = new PasswordDto(passwordEncoder.encode(password));
            feignProxy.updatePassword(id, passwordDto);
        } catch (FeignException e){
            throw new UserException(e.getMessage());
        }
    }

    private void hardcodedResponse(Exception ex){
        throw new ServerException();
    }
}
