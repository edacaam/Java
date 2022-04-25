package com.turkcell.rentACar.business.dtos;

import com.turkcell.rentACar.entities.concretes.AdditionalService;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderedAdditionalServiceListDto {

	private Integer id;

	private AdditionalService additionalService;
}
