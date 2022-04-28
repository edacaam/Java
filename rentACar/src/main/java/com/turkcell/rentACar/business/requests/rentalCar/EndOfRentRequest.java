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
public class EndOfRentRequest {

	@NotNull
	@Positive
	private int rentalCarId;

	@NotNull
	@Positive
	private Double endingKilometer;

	@NotNull
	private LocalDate endingDate;
}
