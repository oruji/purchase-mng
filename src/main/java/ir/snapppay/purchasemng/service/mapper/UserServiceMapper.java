package ir.snapppay.purchasemng.service.mapper;

import ir.snapppay.purchasemng.dto.UserDto;
import ir.snapppay.purchasemng.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface UserServiceMapper {

	@Mapping(target = "username", source = "dto.username")
	@Mapping(target = "password", source = "encode")
	@Mapping(target = "userId", ignore = true)
	User toUser(UserDto dto, String encode);

}
