package com.turkcell.rentACar.business.dtos;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RentalCarListDto {
	private Integer id;

	private LocalDate startingDate;

	private LocalDate endDate;

	private Integer carId;

	private Integer cityOfPickUpId;

	private Integer cityOfDeliveryId;

	private List<OrderedAdditionalServiceListDto> orderedAdditionalServiceListDto;

	private Integer customerUserId;

	private Double startingKilometer;

	private Double endingKilometer;
}
