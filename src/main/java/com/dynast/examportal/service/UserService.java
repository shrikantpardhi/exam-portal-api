package com.dynast.examportal.service;

import com.dynast.examportal.dto.UserDto;

public interface UserService  {

    Iterable<UserDto> getAllUser();

    UserDto registerNewUser(UserDto user);

    UserDto updateUser(UserDto user);

    UserDto fetchUser(String emailId);

    UserDto getUserDetail(String userName);

    Boolean validateIfExist(String emailId, String mobile);

    void initRoleAndUser();
}
