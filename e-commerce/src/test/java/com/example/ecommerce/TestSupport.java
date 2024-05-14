package com.example.ecommerce;

import com.example.ecommerce.dto.UserDto;
import com.example.ecommerce.model.UserInformation;

import java.util.List;
import java.util.Random;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;



public class TestSupport {

    public static  Long userId=100L;
     public static List<UserInformation> generateUsers(){
        return IntStream.range(0,5).mapToObj(i -> new UserInformation(
                 i+"@gmail.com",
                 "firstName"+i,
                 "lastName"+i,
                 "",
                 new Random(2).nextBoolean())
                 ).collect(Collectors.toList());
     }

     public static List<UserDto> generateUserDtoList(List<UserInformation> userInformationList){

         return userInformationList.stream().map(from->new UserDto(from.getMail(),from.getFirstName(), from.getLastName(), from.getMiddleName()))
                 .collect(Collectors.toList());
     }

     public static UserInformation generateUser(String mail){
         return new UserInformation(
                 mail,
                 "firstName"+userId,
                 "lastName"+userId,
                 "",
                 true,
                 (long) userId);
     }

     public static UserDto generateUserDto(String mail){
         return new UserDto(mail,
                 "firstName"+userId,
                 "lastName"+userId,
                 "");
     }


}
