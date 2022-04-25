package com.turkcell.rentACar.business.requests;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCarMaintenanceRequest {

	private String description;

	@NotNull
	private LocalDate returnDate;

	@NotNull
	@Positive
	private int carId;
}
