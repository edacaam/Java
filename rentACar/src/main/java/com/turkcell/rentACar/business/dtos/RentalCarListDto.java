package com.turkcell.rentACar.business.dtos;

import java.time.LocalDate;
import java.util.List;

import com.turkcell.rentACar.entities.concretes.City;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RentalCarListDto {
	private int rentalCarId;

	private LocalDate startingDate;

	private LocalDate endDate;

	private int carId;
	
	private City cityOfPickUp;
	
	private City cityOfDelivery;
	
	private List<AdditionalServiceListDto> additionalServiceListDtos;
	
	private int customerUserId;
	
	private Double startingKilometer;

	private Double endingKilometer;
}
