package grauebcf.schoolapppro.service;

import grauebcf.schoolapppro.dto.UserRegisterDTO;
import grauebcf.schoolapppro.model.User;
import grauebcf.schoolapppro.repository.UserRepository;
import grauebcf.schoolapppro.service.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class UserService implements IUserService{

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Transactional
    @Override
    public User registerUser(UserRegisterDTO userToRegister) {
        // Log userToRegister here to check its values.
        User user = convertToUser(userToRegister);
        User savedUser = userRepository.save(user);
        // Log savedUser and its properties to verify.
        return savedUser;
    }


    @Override
    public User getUserByEmail(String email) throws UserNotFoundException {
        User user = userRepository.getUserByEmail(email);
        if (user == null) {
            throw new UserNotFoundException("User with email: " + email + "not found.");
        }
        return user;
    }

    @Override
    public boolean isEmailTaken(String email) {
        return userRepository.emailExists(email);
    }

    private static User convertToUser(UserRegisterDTO userDTO) {
        return new User(userDTO.getEmail(), userDTO.getPassword());
    }

}
