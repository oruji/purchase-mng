package ir.snapppay.purchasemng.dto.token;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TokenRequest {

	@NotBlank
	private String username;

	@NotBlank
	private String password;

}
