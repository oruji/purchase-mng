package ir.snapppay.purchasemng.entity.user;

import ir.snapppay.purchasemng.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "USERS")
public class User extends BaseEntity {

	@NotNull
	@Column(nullable = false, unique = true, length = 20)
	private String username;

	@NotNull
	@Column(nullable = false)
	private String password;

}
