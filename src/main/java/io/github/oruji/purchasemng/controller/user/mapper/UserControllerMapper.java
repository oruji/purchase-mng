package io.github.oruji.purchasemng.controller.user.mapper;

import io.github.oruji.purchasemng.dto.user.CreateUserRequest;
import io.github.oruji.purchasemng.dto.user.CreateUserResponse;
import io.github.oruji.purchasemng.service.user.model.UserModel;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface UserControllerMapper {

	UserModel toUserModel(CreateUserRequest createUserRequest);

	CreateUserResponse toCreateUserResponse(UserModel userModel);

}
