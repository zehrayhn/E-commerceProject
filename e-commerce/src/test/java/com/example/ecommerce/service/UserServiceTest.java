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
import org.apache.logging.log4j.core.Logger;
import org.junit.jupiter.api.*;

import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;


import java.util.List;
import java.util.Optional;

import static com.example.ecommerce.TestSupport.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class UserServiceTest {
    private static final Logger logger= (Logger) LogManager.getLogger(UserServiceTest.class);
    private UserDtoConverter converter;
    private UserInformationRepository repository;
    private  UserService userService;

    @BeforeEach
    public void setUp(){

        converter= Mockito.mock(UserDtoConverter.class);
        repository= Mockito.mock(UserInformationRepository.class);
        userService=new UserService(repository,converter);

    }

    @Test
    public void testGetAllUsers_itShouldReturnUserDtoList(){
        List<UserInformation> userInformationList =generateUsers();
        List<UserDto> userDtoList=generateUserDtoList(userInformationList);

        when(repository.findAll()).thenReturn(userInformationList);
     //   when(converter.convert(userInformationList)).thenReturn(userDtoList);

        try (MockedStatic<UserDtoConverter> mockedConverter = mockStatic(UserDtoConverter.class)) {
            mockedConverter.when(()->UserDtoConverter.convert(userInformationList)).thenReturn(userDtoList);

        }

        List<UserDto> result =userService.getAllUsers();

        assertEquals(userDtoList,result);
        Mockito.verify(repository).findAll();
      //  Mockito.verify(converter).convert(userInformationList);

    }

    @Test
    public void testGetUserByMail_whenUserMailExist_itShouldReturnUserDtoList(){
        String mail="mail@gmail.com";
        UserInformation user =generateUser(mail);
        UserDto userDto=generateUserDto(mail);
        logger.debug("buraya geldi??????????????????????????????????????*");
        logger.info("buraya geldi??????????????????????????????????????*");
        when(repository.findByMail(mail)).thenReturn(Optional.of(user));

        try (MockedStatic<UserDtoConverter> mockedConverter = mockStatic(UserDtoConverter.class)) {
            mockedConverter.when(()->UserDtoConverter.convert(user)).thenReturn(userDto);

        }

        UserDto result =userService.getUserByMail(mail);
        System.out.println("Beklenen: " + userDto.getFirstName());
        System.out.println("Gerçek Sonuç: " + result.getFirstName());
        assertEquals(userDto,result);
        Mockito.verify(repository).findByMail(mail);
     //   Mockito.verify(converter).convert(user);

    }


    @Test
    public void testGetUserByMail_whenUserMailExistDoesNotExist_itShouldThrowUserNotFoundException(){
        String mail="mail@gmail.com";
        UserInformation user =generateUser(mail);
        UserDto userDto=generateUserDto(mail);

        when(repository.findByMail(mail)).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class,()->userService.getUserByMail(mail));

//        try (MockedStatic<UserDtoConverter> mockedConverter = mockStatic(UserDtoConverter.class)) {
//            mockedConverter.when(()->UserDtoConverter.convert(user)).thenReturn(userDto);
//
//            // Test logic here...
//        }
//
//        UserDto result =userService.getUserByMail(mail);
//        System.out.println("Beklenen: " + userDto.getFirstName());
//        System.out.println("Gerçek Sonuç: " + result.getFirstName());
//        assertEquals(userDto,result);
        Mockito.verify(repository).findByMail(mail);
        //   Mockito.verify(converter).convert(user);

    }
    @Test
    public void testCreateUser_itShouldReturnCreatedUserDto(){
        String mail="mail@gmail.com";
        CreateUserRequest request=new CreateUserRequest("firstName","lastName","",mail);
        UserInformation user =new UserInformation(mail,"firstName","lastName","",true);
        UserInformation savedUser=new UserInformation(mail,"firstName","lastName","",true,1L);
        UserDto userDto=new UserDto(mail,"firstName","lastName","");

        logger.info("buraya geldi");
        when(repository.save(user)).thenReturn(savedUser);
    //    when(converter.convert(savedUser)).thenReturn(userDto);
        try (MockedStatic<UserDtoConverter> mockedConverter = mockStatic(UserDtoConverter.class)) {
            mockedConverter.when(()->UserDtoConverter.convert(savedUser)).thenReturn(userDto);

            logger.info("buraya geldi");
            // Test logic here...
        }catch (Exception e){

            logger.info("hata"+  e.getMessage());
        }

        System.out.println("Beklenen: " + userDto.getMail());

        UserDto result =userService.createUser(request);
        assertEquals(userDto,result);

        verify(repository).save(user);


    }


    @Test
    public void testUpdateUser_whenUserMailExistAndUserIsActive_itShouldReturnUpdatedUserDto(){
        String mail="mail@gmail.com";
        UpdateUserRequest request=new UpdateUserRequest("firstName2","lastName2","");
        UserInformation user =new UserInformation(mail,"firstName","lastName","",true);
        UserInformation savedUser=new UserInformation(mail,"firstName2","lastName2","",true,1L);
        UserDto userDto=new UserDto(mail,"firstName2","lastName2","");

        when(repository.findByMail(mail)).thenReturn(Optional.of(user));
        when(repository.save(user)).thenReturn(savedUser);

      //  when(converter.convert(savedUser)).thenReturn(userDto);


        try (MockedStatic<UserDtoConverter> mockedConverter = mockStatic(UserDtoConverter.class)) {
            mockedConverter.when(()->UserDtoConverter.convert(savedUser)).thenReturn(userDto);
            UserDto result =userService.updateUser(mail,request);
            assertEquals(userDto,result);

            //static methodları verify etmek için;
          mockedConverter.verify(()->UserDtoConverter.convert(savedUser));
            logger.info("buraya geldi");
        }catch (Exception e){

            logger.info("hata"+  e.getMessage());
        }

        System.out.println("Beklenen: " + userDto.getMail());


        verify(repository).findByMail(mail);
        verify(repository).save(user);
      //  verify(converter).convert(savedUser);


    }

    @Test
    public void testUpdateUser_whenUserMailDoesNotExist_itShouldThrowUserNotFoundException(){
        String mail="mail@gmail.com";
        UpdateUserRequest request=new UpdateUserRequest("firstName2","lastName2","");
        when(repository.findByMail(mail)).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class,()->userService.updateUser(mail,request));

        verify(repository).findByMail(mail);
        verifyNoMoreInteractions(repository);

        try (MockedStatic<UserDtoConverter> mockedConverter = mockStatic(UserDtoConverter.class)) {
            mockedConverter.verifyNoInteractions();
        }

    }

    @Test
    public void testUpdateUser_whenUserMailExistButUserIsNotActive_itShouldThrowUserNotActiveException(){
        String mail="mail@gmail.com";
        UpdateUserRequest request=new UpdateUserRequest("firstName2","lastName2","");
        UserInformation user=new UserInformation(mail,"firstName2","lastName2","",false,1L);
        when(repository.findByMail(mail)).thenReturn(Optional.of(user));
        assertThrows(UserIsNotActiveException.class,()->userService.updateUser(mail,request));



        verify(repository).findByMail(mail);
        verifyNoMoreInteractions(repository);

        try (MockedStatic<UserDtoConverter> mockedConverter = mockStatic(UserDtoConverter.class)) {
            mockedConverter.verifyNoInteractions();
        }

    }

    @Test
    public void testDeactiveUser_whenUserIdExist_itShouldUpdateUserByActive(){
        String mail="mail@gmail.com";
        UserInformation user=new UserInformation(mail,"firstName2","lastName2","",true, userId);
        UserInformation savedUser=new UserInformation(mail,"firstName2","lastName2","",false, userId);

        when(repository.findById(userId)).thenReturn(Optional.of(user));

        userService.deactivateUser(userId);

        verify(repository).findById(userId);
        verify(repository).save(savedUser);

    }


    @Test
    public void testDeactiveUser_whenUserIdDoesNotExist_itShouldThrowUserNotFoundException(){

        when(repository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class,()->userService.deactivateUser(userId));

        verify(repository).findById(userId);
        verifyNoMoreInteractions(repository);


    }

    @Test
    public void testActiveUser_whenUserIdExist_itShouldUpdateUserByActiveTrue(){
        String mail="mail@gmail.com";
        UserInformation user=new UserInformation(mail,"firstName2","lastName2","",false, userId);
        UserInformation savedUser=new UserInformation(mail,"firstName2","lastName2","",true, userId);

        when(repository.findById(userId)).thenReturn(Optional.of(user));

        userService.activeUser(userId);

        verify(repository).findById(userId);
        verify(repository).save(user);

    }

    @Test
    public void testActiveUser_whenUserIdDoesNotExist_itShouldThrowUserNotFoundException(){

        when(repository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class,()->userService.activeUser(userId));

        verify(repository).findById(userId);
        verifyNoMoreInteractions(repository);


    }

    @Test
    public void testDeleteUser_whenUserIdExist_itShouldDeleteUser(){
        String mail="mail@gmail.com";
        UserInformation user=new UserInformation(mail,"firstName2","lastName2","",false, userId);


        when(repository.findById(userId)).thenReturn(Optional.of(user));

        userService.deleteUser(userId);

        verify(repository).findById(userId);
        verify(repository).deleteById(userId);

    }
    @Test
    public void testDeleteUser_whenUserIdNotExist_itShouldThrowUserNotFoundException(){
        String mail="mail@gmail.com";
        UserInformation user=new UserInformation(mail,"firstName2","lastName2","",false, userId);

        when(repository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class,()->userService.deleteUser(userId));

        verify(repository).findById(userId);
        verifyNoMoreInteractions(repository);

    }




}
