package io.github.oruji.purchasemng.service.transaction.mapper;

import java.math.BigDecimal;

import io.github.oruji.purchasemng.entity.transaction.Transaction;
import io.github.oruji.purchasemng.entity.transaction.TransactionType;
import io.github.oruji.purchasemng.entity.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface TransactionServiceMapper {

	Transaction toTransaction(User user, BigDecimal amount, TransactionType type);

}
