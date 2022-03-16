package com.turkcell.rentACar.business.requests;

import java.time.LocalDate;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import com.turkcell.rentACar.entities.concretes.OrderedAdditionalService;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateRentalCarRequest {
	@NotNull
	@Positive
	private int rentalCarId;
	
	private LocalDate startingDate;

	private LocalDate endDate;
	
	@Positive
	private int carId;

	@Positive
	private int cityOfPickUpId;
	
	@Positive
	private int cityOfDeliveryId;
	
	private List<OrderedAdditionalService> orderedAdditionalServices;
}
