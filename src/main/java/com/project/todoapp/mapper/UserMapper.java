package com.project.todoapp.mapper;

import com.project.todoapp.dto.UserDto;
import com.project.todoapp.models.User;
import com.project.todoapp.payload.request.UpdateInfoRequest;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

//@Component
@Mapper(componentModel = "spring")
public interface UserMapper {
  UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

//  @Mapping(source = "userId", target = "id")
//  @Mapping(source = "firstName", target = "firstName")
  UserDto mapToUserDto(User user);

  UpdateInfoRequest mapToUserInfo(User user);
  List<UserDto> toUsersDto(List<User> userList);
}
