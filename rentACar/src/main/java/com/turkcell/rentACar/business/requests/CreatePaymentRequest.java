package com.turkcell.rentACar.business.requests;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreatePaymentRequest {
	@NotNull
	private String cardNo;

	@NotNull
	@Size(min = 5)
	private String cardHolder;

	@NotNull
	private String month;
	
	@NotNull
	private String year;

	@NotNull
	@Size(min = 100, max = 999)
	private String cvv;
}
