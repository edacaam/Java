package com.turkcell.rentACar.business.requests;



import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateAdditionalServiceRequest {
	@NotNull
	private String name;
	@NotNull
	@Min(0)
	private double dailyPrice;
}
