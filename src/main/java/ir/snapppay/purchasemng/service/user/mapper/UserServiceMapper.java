package ir.snapppay.purchasemng.service.user.mapper;

import ir.snapppay.purchasemng.entity.user.User;
import ir.snapppay.purchasemng.service.user.model.UserModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface UserServiceMapper {

	@Mapping(target = "username", source = "model.username")
	@Mapping(target = "password", source = "password")
	@Mapping(target = "creationDate", ignore = true)
	@Mapping(target = "modificationDate", ignore = true)
	@Mapping(target = "id", ignore = true)
	User toUser(UserModel model, String password);

	UserModel toUserModel(User savedUser);

}
