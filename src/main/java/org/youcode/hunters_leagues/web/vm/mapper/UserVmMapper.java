package org.youcode.hunters_leagues.web.vm.mapper;

import org.mapstruct.Mapper;
import org.youcode.hunters_leagues.domain.User;
import org.youcode.hunters_leagues.web.vm.UserVm;

@Mapper(componentModel = "spring")
public interface UserVmMapper {
    User toUser(UserVm userVm);
//    UserVmMapper toUserVm(User user);
}
