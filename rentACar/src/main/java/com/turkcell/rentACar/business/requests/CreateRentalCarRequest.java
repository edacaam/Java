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
public class CreateRentalCarRequest {
	
	@NotNull
	private LocalDate startingDate;
	@NotNull
	private LocalDate endDate;

	@Positive
	@NotNull
	private int carId;

	@Positive
	@NotNull
	private int cityOfPickUpId;

	@Positive
	@NotNull
	private int cityOfDeliveryId;

	@NotNull
	@Positive
	private int customerUserId;

	@NotNull
	private List<OrderedAdditionalService> orderedAdditionalServices;
}
