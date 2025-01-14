package ir.snapppay.purchasemng.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/resource")
public class TestController {

	@GetMapping("/test")
	public ResponseEntity<String> testResource() {
		return ResponseEntity.ok("Everything is OK!");
	}

}
