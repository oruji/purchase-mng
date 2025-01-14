package ir.snapppay.purchasemng.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserAddRequest {

	@NotBlank
	private String username;

	@NotBlank
	private String password;

}
