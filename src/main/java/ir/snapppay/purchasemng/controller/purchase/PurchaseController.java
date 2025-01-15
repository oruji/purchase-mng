package ir.snapppay.purchasemng.controller.purchase;

import ir.snapppay.purchasemng.controller.purchase.mapper.PurchaseControllerMapper;
import ir.snapppay.purchasemng.dto.purchase.PurchaseCreationRequest;
import ir.snapppay.purchasemng.dto.purchase.PurchaseCreationResponse;
import ir.snapppay.purchasemng.service.purchase.PurchaseService;
import ir.snapppay.purchasemng.service.purchase.model.PurchaseModel;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/purchases")
public class PurchaseController {

	private final PurchaseService service;

	private final PurchaseControllerMapper mapper;

	@PostMapping()
	public ResponseEntity<PurchaseCreationResponse> create(@Valid @RequestBody PurchaseCreationRequest request) {
		log.info("Create Purchase Api called: {}", request);
		PurchaseModel model = service.save(mapper.toPurchaseModel(request));
		PurchaseCreationResponse response = mapper.toCreatePurchaseResponse(model);
		log.info("Purchase Created successfully");
		return ResponseEntity.ok(response);
	}

	@PostMapping("/verify/{trackingCode}")
	public ResponseEntity<PurchaseCreationResponse> verify(@PathVariable String trackingCode) {
		log.info("Verify Purchase Api called: {}", trackingCode);
		PurchaseModel model = service.verify(trackingCode);
		PurchaseCreationResponse response = mapper.toCreatePurchaseResponse(model);
		log.info("Purchase Verified successfully");
		return ResponseEntity.ok(response);
	}

}
