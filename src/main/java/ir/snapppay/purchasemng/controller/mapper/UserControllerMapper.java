package ir.snapppay.purchasemng.controller.mapper;

import ir.snapppay.purchasemng.dto.UserAddRequest;
import ir.snapppay.purchasemng.dto.UserAddResponse;
import ir.snapppay.purchasemng.service.model.UserModel;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface UserControllerMapper {

	UserModel toUserModel(UserAddRequest userAddRequest);

	UserAddResponse toUserAddResponse(UserModel userModel);

}
