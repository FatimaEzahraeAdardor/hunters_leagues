package org.youcode.hunters_leagues.web.vm.mapper;

import org.mapstruct.Mapper;
import org.youcode.hunters_leagues.domain.User;
import org.youcode.hunters_leagues.web.vm.SignUpVm;

@Mapper(componentModel = "spring")
public interface SignUpVmMapper {
    User ToUser(SignUpVm signUpVm);
//    SignUpVm ToSignUpVm(User user);
}
