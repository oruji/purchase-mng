package io.github.oruji.purchasemng.service.transaction.mapper;

import java.math.BigDecimal;

import io.github.oruji.purchasemng.entity.transaction.Transaction;
import io.github.oruji.purchasemng.entity.transaction.TransactionType;
import io.github.oruji.purchasemng.entity.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface TransactionServiceMapper {

	@Mapping(target = "user", source = "user")
	@Mapping(target = "amount", source = "amount")
	@Mapping(target = "type", source = "type")
	@Mapping(target = "id", ignore = true)
	@Mapping(target = "creationDate", ignore = true)
	@Mapping(target = "modificationDate", ignore = true)
	Transaction toTransaction(User user, BigDecimal amount, TransactionType type);

}
