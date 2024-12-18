package org.youcode.hunters_leagues.web.vm.mapper;

import org.mapstruct.Mapper;
import org.youcode.hunters_leagues.domain.User;
import org.youcode.hunters_leagues.web.vm.UserResponseVm;

@Mapper(componentModel = "spring")
public interface UserResponseVmMapper {
    User toUser(UserResponseVm userResponseVm);
    UserResponseVm toUserVm(User user);
}
