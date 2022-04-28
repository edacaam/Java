package com.turkcell.rentACar.business.requests.rentalCar;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateRentalCarRequest {
	@NotNull
	@Positive
	private int id;

	@NotNull
	private LocalDate startingDate;

	@NotNull
	private LocalDate endDate;

	@Positive
	private int carId;

	@Positive
	private int cityOfPickUpId;

	@Positive
	private int cityOfDeliveryId;

}
