package io.github.oruji.purchasemng.entity.purchase;


import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class PurchaseItem {

	private String name;

	private Integer count;

}