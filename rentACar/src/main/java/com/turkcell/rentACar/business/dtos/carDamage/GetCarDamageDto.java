package com.turkcell.rentACar.business.dtos.carDamage;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetCarDamageDto {

	private int id;

	private String description;

	private int carId;
}
