package services;

import config.ConnectionProvider;
import models.User;
import repositories.UserRepository;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void createUser(User user) {
        try(Connection connection = ConnectionProvider.getConnection()) {
            userRepository.createUser(user, connection);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<User> getUsers() {
        try(Connection connection = ConnectionProvider.getConnection()) {
            Optional<List<User>> usersOptional = userRepository.getUsers(connection);
            if (usersOptional.isPresent()) {
                return usersOptional.get();
            } else {
                throw new RuntimeException();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        throw new RuntimeException();
    }

}
