package ir.snapppay.purchasemng;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/first-rest")
public class MyFirstRestController {

	@GetMapping("/first")
	public String firstEndPoint() {
		return "I'm first endpoint";
	}

	@GetMapping("/second")
	public String secondEndPoint() {
		return "I'm second endpoint";
	}

}
