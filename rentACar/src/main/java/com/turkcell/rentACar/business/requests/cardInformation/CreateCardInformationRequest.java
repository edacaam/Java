package com.turkcell.rentACar.business.requests.cardInformation;

import javax.validation.constraints.Positive;

import com.sun.istack.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCardInformationRequest {

	@NotNull
	private String cardNo;

	@NotNull
	private String cardHolder;

	@NotNull
	private String expirationMonth;

	@NotNull
	private String expirationYear;

	@NotNull
	private String cvv;

	@Positive
	private int customerId;
}
