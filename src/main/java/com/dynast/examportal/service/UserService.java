package com.dynast.examportal.service;

import com.dynast.examportal.dto.UserDto;

public interface UserService  {
    Iterable<UserDto> getUsers();
    UserDto createUser(UserDto userDto);
    UserDto createEducator(UserDto userDto);
    UserDto updateUser(UserDto userDto);
    boolean updatePassword(UserDto userDto);
    boolean resetPassword(String emailId);
    UserDto getUserByEmailId(String emailId);
    UserDto getUserById(String userId);
    boolean changeStatus(String userId);
    boolean validateIfExist(String emailId, String mobile);

    void initRoleAndUser();
}
