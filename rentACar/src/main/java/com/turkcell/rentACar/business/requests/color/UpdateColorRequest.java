package com.turkcell.rentACar.business.requests.color;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateColorRequest {

	@NotNull
	@Positive
	private int id;

	@NotNull
	@Size(min = 2, max = 50)
	private String colorName;
}
