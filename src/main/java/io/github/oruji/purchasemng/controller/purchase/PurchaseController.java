package io.github.oruji.purchasemng.controller.purchase;

import io.github.oruji.purchasemng.controller.purchase.mapper.PurchaseControllerMapper;
import io.github.oruji.purchasemng.dto.ApiResponse;
import io.github.oruji.purchasemng.dto.purchase.PurchaseCreationRequest;
import io.github.oruji.purchasemng.dto.purchase.PurchaseCreationResponse;
import io.github.oruji.purchasemng.service.purchase.PurchaseService;
import io.github.oruji.purchasemng.service.purchase.model.PurchaseModel;
import io.github.oruji.purchasemng.utility.JwtUtil;
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

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/purchases")
public class PurchaseController {

	private final PurchaseService service;

	private final PurchaseControllerMapper mapper;

	private final JwtUtil jwtUtil;

	@PostMapping()
	public ResponseEntity<ApiResponse<PurchaseCreationResponse>> create(
			@RequestHeader("Authorization") String authorizationHeader,
			@Valid @RequestBody PurchaseCreationRequest request) {
		log.info("Create Purchase Api called: {}", request);
		String username = jwtUtil.getUsernameFromToken(authorizationHeader);
		PurchaseModel model = service.save(mapper.toPurchaseModel(request, username));
		PurchaseCreationResponse response = mapper.toCreatePurchaseResponse(model);
		log.info("Purchase Created successfully");
		ApiResponse<PurchaseCreationResponse> apiResponse = new ApiResponse<>(HttpStatus.OK.value(),
				HttpStatus.OK.name(), response);
		return ResponseEntity.ok(apiResponse);
	}

	@PostMapping("/verify/{trackingCode}")
	public ResponseEntity<ApiResponse<PurchaseCreationResponse>> verify(@PathVariable String trackingCode) {
		log.info("Verify Purchase Api called: {}", trackingCode);
		PurchaseModel model = service.verify(trackingCode);
		PurchaseCreationResponse response = mapper.toCreatePurchaseResponse(model);
		log.info("Purchase Verified successfully");
		ApiResponse<PurchaseCreationResponse> apiResponse = new ApiResponse<>(HttpStatus.OK.value(),
				HttpStatus.OK.name(), response);
		return ResponseEntity.ok(apiResponse);
	}

}
