package com.dynast.examportal.service;

import com.dynast.examportal.dto.UserDto;

public interface UserService  {
    Iterable<UserDto> getUsers();
    UserDto createUser(UserDto userDto);
    UserDto createEducator(UserDto userDto);
    UserDto updateUser(UserDto userDto);
    Boolean updatePassword(UserDto userDto);
    Boolean resetPassword(String emailId);
    UserDto getUserByEmailId(String emailId);
    UserDto getUserById(String userId);
    Boolean changeStatus(String userId);
    Boolean validateIfExist(String emailId, String mobile);

    void initRoleAndUser();
}
