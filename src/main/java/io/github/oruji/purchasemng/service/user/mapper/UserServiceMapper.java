package io.github.oruji.purchasemng.service.user.mapper;

import io.github.oruji.purchasemng.entity.user.User;
import io.github.oruji.purchasemng.service.user.model.UserModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface UserServiceMapper {

	@Mapping(target = "username", source = "model.username")
	@Mapping(target = "password", source = "password")
	@Mapping(target = "initialBalance", source = "model.initialBalance")
	@Mapping(target = "creationDate", ignore = true)
	@Mapping(target = "modificationDate", ignore = true)
	@Mapping(target = "id", ignore = true)
	@Mapping(target = "balance", ignore = true)
	@Mapping(target = "version", ignore = true)
	User toUser(UserModel model, String password);

	UserModel toUserModel(User savedUser);

}
