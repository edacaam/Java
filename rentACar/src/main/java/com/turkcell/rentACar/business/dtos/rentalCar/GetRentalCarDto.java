package com.turkcell.rentACar.business.dtos.rentalCar;

import java.time.LocalDate;
import java.util.List;

import com.turkcell.rentACar.business.dtos.orderedAdditionalService.OrderedAdditionalServiceListDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetRentalCarDto {
	private Integer id;

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
