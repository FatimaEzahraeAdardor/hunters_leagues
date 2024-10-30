package org.youcode.hunters_leagues.web.vm.mapper;

import org.mapstruct.Mapper;
import org.youcode.hunters_leagues.domain.User;
import org.youcode.hunters_leagues.web.vm.SignInVm;

@Mapper(componentModel = "spring")
public interface SignInVmMapper {
    User ToUser(SignInVm signInVm);
//    SignInVm ToSignInVm(User user);
}
