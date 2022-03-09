package com.turkcell.rentACar.business.requests;

import java.time.LocalDate;

import javax.validation.constraints.NotBlank;
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
	@NotBlank
	private int rentalCarId;

	private LocalDate startingDate;

	private LocalDate endDate;
}
