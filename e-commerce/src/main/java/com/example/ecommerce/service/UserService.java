package com.example.ecommerce.service;

import com.example.ecommerce.dto.CreateUserRequest;
import com.example.ecommerce.dto.UpdateUserRequest;
import com.example.ecommerce.dto.UserDto;
import com.example.ecommerce.dto.UserDtoConverter;
import com.example.ecommerce.exception.UserIsNotActiveException;
import com.example.ecommerce.exception.UserNotFoundException;
import com.example.ecommerce.model.UserInformation;
import com.example.ecommerce.repository.UserInformationRepository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private static final Logger logger = LogManager.getLogger(UserService.class);


    private final UserInformationRepository userInformationRepository;
    private final UserDtoConverter userDtoConverter;

    public UserService(UserInformationRepository userInformationRepository, UserDtoConverter userDtoConverter) {

        this.userInformationRepository = userInformationRepository;
        this.userDtoConverter = userDtoConverter;
    }

    public List<UserDto> getAllUsers() {
        return userDtoConverter.convert(userInformationRepository.findAll());
    }

    public UserDto getUserByMail(String mail) {
        UserInformation userInformation = findUserByMail(mail);
        logger.info("userınformatin mail: " + userInformation.getMail() + userInformation.getFirstName() + userInformation.getLastName() + userInformation.getActive());
        return userDtoConverter.convert(userInformation);

    }

    public UserDto createUser(CreateUserRequest userRequest) {
        UserInformation userInformation = new UserInformation(userRequest.getMail(),
                userRequest.getFirstName(),
                userRequest.getLastName(),
                userRequest.getMiddleName(),
                true);

        logger.info("UserInformation mail: " + userInformation.getMail() + ", firstName: " + userInformation.getFirstName() + ", lastName: " + userInformation.getLastName() + ", active: " + userInformation.getActive());

        Optional<UserInformation> savedUser = Optional.ofNullable(userInformationRepository.save(userInformation));
        return savedUser.map(UserDtoConverter::convert).orElse(null);
    }

    public UserDto updateUser(String mail, UpdateUserRequest updateUserRequest) {

        //  User user=findUserById(id);
        UserInformation userInformation = findUserByMail(mail);
        if (!userInformation.getActive()) {

            //    logger.warn(String.format("the user wanted update is not active",mail));
            throw new UserIsNotActiveException("the user wanted update is not active!");
        }
//        UserInformation updatedUserInformation=new UserInformation(userInformation.getMail(),
//                updateUserRequest.getFirstName(),
//                updateUserRequest.getLastName(),
//                updateUserRequest.getMiddleName(),
//                true, userInformation.getId());
        userInformation.setFirstName(updateUserRequest.getFirstName());
        userInformation.setLastName(updateUserRequest.getLastName());
        userInformation.setMiddleName(updateUserRequest.getMiddleName());

        return UserDtoConverter.convert(userInformationRepository.save(userInformation));

    }

    public void deactivateUser(Long id) {
        changeActiveUser(id, false);
    }

    public void activeUser(Long id) {

        changeActiveUser(id, true);
    }

    public void changeActiveUser(Long id, Boolean isActive) {

        Optional<UserInformation> optionalUserInformation = findUserById(id);
        //UserInformation userInformation=findUserById(id);
        if (optionalUserInformation.isPresent()) {
            UserInformation user = optionalUserInformation.get();
            user.setActive(isActive);
            userInformationRepository.save(user);
        }
//        UserInformation updatedUserInformation=new UserInformation(userInformation.getMail(),
//                userInformation.getLastName(),
//                userInformation.getFirstName(),
//                userInformation.getMiddleName(),
//                isActive, userInformation.getId());

    }

    private UserInformation findUserByMail(String mail) {
        return userInformationRepository.findByMail(mail).orElseThrow(() -> new UserNotFoundException("User couldn't be found by following id: " + mail));
    }

    private Optional<UserInformation> findUserById(Long id) {

//        logger.info("Searching for user with id: {}", id);
//        UserInformation user = userInformationRepository.findById(id)
//                .orElseThrow(() -> new UserNotFoundException("User couldn't be found by following id: " + id));
//        logger.info("User found: {}", user);
//        return user;
        return Optional.ofNullable(userInformationRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User couldn't be found by following id: " + id)));
    }


    public void deleteUser(Long id) {
        findUserById(id);
        userInformationRepository.deleteById(id);
    }
}
