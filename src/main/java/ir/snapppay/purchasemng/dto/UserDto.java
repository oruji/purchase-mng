package ir.snapppay.purchasemng.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class UserDto {

	@NotEmpty
	private String username;

	@NotEmpty
	private String password;

}
