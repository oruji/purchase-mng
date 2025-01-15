package io.github.oruji.purchasemng.repository.transaction;

import io.github.oruji.purchasemng.entity.transaction.Transaction;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

}
