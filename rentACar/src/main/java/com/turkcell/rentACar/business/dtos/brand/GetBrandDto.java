package com.turkcell.rentACar.business.dtos.brand;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetBrandDto {

	private Integer id;

	private String brandName;
}
