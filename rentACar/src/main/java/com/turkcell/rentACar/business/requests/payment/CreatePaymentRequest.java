package com.turkcell.rentACar.business.requests.payment;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;

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
	private String cardHolder;

	@Range(min = 01, max = 12)
	private String month;

	@Range(min = 22)
	private String year;

	@NotNull
	@Range(min = 100, max = 999)
	private String cvv;
}
