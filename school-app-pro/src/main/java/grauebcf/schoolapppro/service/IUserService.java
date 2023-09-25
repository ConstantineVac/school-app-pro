package grauebcf.schoolapppro.service;

import grauebcf.schoolapppro.dto.UserRegisterDTO;
import grauebcf.schoolapppro.model.User;
import grauebcf.schoolapppro.service.exception.UserNotFoundException;

public interface IUserService {

    public User registerUser(UserRegisterDTO userToRegisterDTO);
    public User getUserByEmail(String email) throws UserNotFoundException;
    public boolean isEmailTaken(String email);
}
