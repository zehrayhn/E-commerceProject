package com.example.ecommerce.dto;

import com.example.ecommerce.model.User;
import com.example.ecommerce.model.UserInformation;
import com.example.ecommerce.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserDtoConverter{
//    public static UserDto convert(User from){
//        return new UserDto(from.getMail(),from.getFirstName(), from.getLastName(), from.getMiddleName());
//    }
private static final Logger logger=  LogManager.getLogger(UserService.class);
    public static UserDto convert(UserInformation from){
     //   logger.info(from.getMail()+from.getFirstName());
        return new UserDto(from.getMail(),from.getFirstName(), from.getLastName(), from.getMiddleName());
    }


    public static List<UserDto> convert(List<UserInformation> fromList){
        return  fromList.stream().map(from->new UserDto(from.getMail(),from.getFirstName(), from.getLastName(), from.getMiddleName()))
                .collect(Collectors.toList());
    }



}
