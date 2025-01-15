package ir.snapppay.purchasemng.controller.purchase;

import ir.snapppay.purchasemng.controller.purchase.mapper.PurchaseControllerMapper;
import ir.snapppay.purchasemng.dto.purchase.CreatePurchaseRequest;
import ir.snapppay.purchasemng.dto.purchase.CreatePurchaseResponse;
import ir.snapppay.purchasemng.service.purchase.PurchaseService;
import ir.snapppay.purchasemng.service.purchase.model.PurchaseModel;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/purchases")
public class PurchaseController {

	private final PurchaseService service;

	private final PurchaseControllerMapper mapper;

	@PostMapping("")
	public ResponseEntity<CreatePurchaseResponse> create(@Valid @RequestBody CreatePurchaseRequest request) {
		log.info("Create Purchase Api called: {}", request);
		PurchaseModel purchaseModel = service.save(mapper.toPurchaseModel(request));
		CreatePurchaseResponse response = mapper.toCreatePurchaseResponse(purchaseModel);
		log.info("Purchase Created successfully");
		return ResponseEntity.ok(response);
	}

}
