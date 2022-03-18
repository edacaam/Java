package com.turkcell.rentACar.business.dtos;

import java.time.LocalDate;
import java.util.List;

import com.turkcell.rentACar.entities.concretes.Car;
import com.turkcell.rentACar.entities.concretes.City;
import com.turkcell.rentACar.entities.concretes.Customer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetRentalCarDto {
	private int rentalCarId;

	private LocalDate startingDate;

	private LocalDate endDate;

	private Car car;

	private City cityOfPickUp;

	private City cityOfDelivery;

	private List<AdditionalServiceListDto> additionalServiceListDtos;
	
	private Customer customer;
	
	private Double startingKilometer;

	private Double endingKilometer;
}
