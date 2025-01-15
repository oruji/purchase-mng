package ir.snapppay.purchasemng.controller.user.mapper;

import ir.snapppay.purchasemng.dto.user.CreateUserRequest;
import ir.snapppay.purchasemng.dto.user.CreateUserResponse;
import ir.snapppay.purchasemng.service.user.model.UserModel;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface UserControllerMapper {

	UserModel toUserModel(CreateUserRequest createUserRequest);

	CreateUserResponse toCreateUserResponse(UserModel userModel);

}
