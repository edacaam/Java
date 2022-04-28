package com.turkcell.rentACar.business.dtos.color;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetColorDto {

	private Integer id;

	private String colorName;
}
