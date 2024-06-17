package org.example.tasklistservice.client;
import feign.FeignException;
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
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;

@RequiredArgsConstructor
@Component
public class UserRestClient {

    private final ModelMapper modelMapper;

    private final PasswordEncoder passwordEncoder;

    private final Logger logger = LoggerFactory.getLogger(UserRestClient.class);

    private final FeignProxy feignProxy;

    //@CircuitBreaker(name = "default", fallbackMethod = "hardcodedResponse")
    public User getUser(int id){
        logger.info("Get user with %d id".formatted(id));
        try {
            UserDto userDto = feignProxy.getUser(id);
            return modelMapper.map(userDto, User.class);
        } catch (FeignException e){
            throw new UserException(e.getMessage());
        }
    }

    public void createUser(User user){
        logger.info("Create user: " + user.toString());
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            UserDto userDto = modelMapper.map(user, UserDto.class);
            feignProxy.createUser(userDto);
        } catch (FeignException e){
            throw new UserException(e.getMessage());
        }
    }

    public void updateUser(User user, int id){
        logger.info("Update user: " + user.toString());
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
        logger.info("Delete user with %d id".formatted(id));
        try {
            feignProxy.deleteUser(id);
        } catch (FeignException e){
            throw new UserException(e.getMessage());
        }
    }

    //@CircuitBreaker(name = "default", fallbackMethod = "hardcodedResponse")
    public User findByEmail(String email) {
        logger.info("Find user with %s email".formatted(email));
        try {
            UserDto userDto = feignProxy.getUserByEmail(email);
            return modelMapper.map(userDto, User.class);
        } catch (FeignException e){
            throw new UserException(e.getMessage());
        }
    }

    //@CircuitBreaker(name = "default", fallbackMethod = "hardcodedResponse")
    public void updatePassword(int id, String password){
        logger.info("Update password for user with %d id".formatted(id));
        try {
            PasswordDto passwordDto = new PasswordDto(passwordEncoder.encode(password));
            feignProxy.updatePassword(id, passwordDto);
        } catch (FeignException e){
            throw new UserException(e.getMessage());
        }
    }

    public ServerException hardcodedResponse(Exception e) throws ServerException {
        throw new ServerException();
    }
}
