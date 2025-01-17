package io.github.oruji.purchasemng.controller.user.mapper;

import io.github.oruji.purchasemng.dto.user.UserRegisterRequest;
import io.github.oruji.purchasemng.dto.user.UserRegisterResponse;
import io.github.oruji.purchasemng.service.user.model.UserModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface UserControllerMapper {

	@Mapping(target = "balance", ignore = true)
	UserModel toUserModel(UserRegisterRequest userRegisterRequest);

	UserRegisterResponse toCreateUserResponse(UserModel userModel);

}
