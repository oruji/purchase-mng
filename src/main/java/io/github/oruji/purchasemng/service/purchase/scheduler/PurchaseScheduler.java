package io.github.oruji.purchasemng.service.purchase.scheduler;

import java.time.LocalDateTime;

import io.github.oruji.purchasemng.config.PurchaseProperties;
import io.github.oruji.purchasemng.entity.purchase.PurchaseStatus;
import io.github.oruji.purchasemng.service.purchase.PurchaseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PurchaseScheduler {

	private final PurchaseService service;

	private final PurchaseProperties properties;

	@Scheduled(fixedDelayString = "${purchase.reverse.time}")
	public void reverseScheduler() {
		log.info("Reverse scheduler started.");
		try {
			service.getPossibleReverses(PurchaseStatus.INITIATED,
					LocalDateTime.now().minusSeconds(properties.getPurchaseReverseTime() / 1000),
					PageRequest.of(0, properties.getPurchaseReversePageSize())).forEach(service::reverse);
			log.info("Purchase reverse scheduler completed successfully.");
		} catch (Exception e) {
			log.error("Purchase reverse scheduler failed: {}", e.getMessage(), e);
		}
	}

}
