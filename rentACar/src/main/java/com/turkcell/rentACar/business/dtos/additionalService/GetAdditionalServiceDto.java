package com.turkcell.rentACar.business.dtos.additionalService;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetAdditionalServiceDto {

	private int id;

	private String name;

	private double dailyPrice;
}
