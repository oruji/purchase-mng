package io.github.oruji.purchasemng.controller.purchase;

import io.github.oruji.purchasemng.controller.purchase.mapper.PurchaseControllerMapper;
import io.github.oruji.purchasemng.dto.ApiResponse;
import io.github.oruji.purchasemng.dto.purchase.PurchaseRequest;
import io.github.oruji.purchasemng.dto.purchase.PurchaseResponse;
import io.github.oruji.purchasemng.service.purchase.PurchaseService;
import io.github.oruji.purchasemng.service.purchase.model.PurchaseModel;
import io.github.oruji.purchasemng.utility.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static io.github.oruji.purchasemng.utility.ResponseUtil.createResponse;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/purchases")
@Tag(name = "Purchase Management", description = "APIs for managing purchases")
public class PurchaseController {

	private final PurchaseService purchaseService;

	private final PurchaseControllerMapper purchaseControllerMapper;

	private final JwtUtil jwtUtil;

	@Operation(summary = "Order a new purchase", description = "Orders a new purchase with the provided details.")
	@PostMapping
	public ResponseEntity<ApiResponse<PurchaseResponse>> order(
			@RequestHeader("Authorization") String authorizationHeader,
			@Valid @RequestBody PurchaseRequest purchaseRequest) {
		log.info("Order Purchase API called for request: {}", purchaseRequest);

		String username = jwtUtil.getUsernameFromToken(authorizationHeader);
		PurchaseModel purchaseModel = purchaseControllerMapper.toPurchaseModel(purchaseRequest, username);

		PurchaseModel createdPurchase = purchaseService.order(purchaseModel);
		PurchaseResponse response = purchaseControllerMapper.toCreatePurchaseResponse(createdPurchase);

		log.info("Order created successfully for user: {}", username);
		return createResponse(response, HttpStatus.OK);
	}

	@Operation(summary = "Verify purchase", description = "Verifies the purchase after creating it.")
	@PostMapping("/verify/{trackingCode}")
	public ResponseEntity<ApiResponse<PurchaseResponse>> verify(@PathVariable String trackingCode) {
		log.info("Verify Purchase API called for tracking code: {}", trackingCode);

		PurchaseModel verifiedPurchase = purchaseService.verify(trackingCode);
		PurchaseResponse response = purchaseControllerMapper.toCreatePurchaseResponse(verifiedPurchase);

		log.info("Purchase verified successfully for tracking code: {}", trackingCode);
		return createResponse(response, HttpStatus.OK);
	}

}