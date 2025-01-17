package io.github.oruji.purchasemng.repository.purchase;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import io.github.oruji.purchasemng.entity.purchase.Purchase;
import io.github.oruji.purchasemng.entity.purchase.PurchaseStatus;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Long> {

	Optional<Purchase> findByTrackingCode(String trackingCode);

	List<Purchase> findByStatusAndModificationDateIsBefore(PurchaseStatus status, LocalDateTime localDateTime,
			Pageable pageable);

}
