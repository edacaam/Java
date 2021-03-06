package com.turkcell.rentACar.business.requests.carDamage;

import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCarDamageRequest {
	@Size(min = 4)
	private String description;
	
	@Positive
	private int carId;
}
