package com.turkcell.rentACar.business.dtos;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetRentalCarDto {
	private int id;

	private LocalDate startingDate;

	private LocalDate endDate;

	private Integer carId;

	private Integer cityOfPickUpId;

	private Integer cityOfDeliveryId;

	private List<OrderedAdditionalServiceListDto> orderedAdditionalServiceList;

	private Integer customerUserId;

	private Double startingKilometer;

	private Double endingKilometer;
}
