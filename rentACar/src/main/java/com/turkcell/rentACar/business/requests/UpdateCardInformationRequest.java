package com.turkcell.rentACar.business.requests;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCardInformationRequest {

	@NotNull
	@Positive
	private Integer id;

	@NotNull
	private String cardNo;

	@NotNull
	private String cardHolder;

	@NotNull
	@Size(min = 1, max = 2)
	private String expirationMonth;

	@NotNull
	@Size(min = 1, max = 2)
	private String expirationYear;

	@NotNull
	private String cvv;

	@Positive
	private Integer customerId;
}
