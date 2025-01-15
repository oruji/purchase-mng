package io.github.oruji.purchasemng.repository.purchase;

import java.util.Optional;

import io.github.oruji.purchasemng.entity.purchase.Purchase;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Long> {

	Optional<Purchase> findByTrackingCode(String trackingCode);

}
