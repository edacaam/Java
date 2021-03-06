package com.turkcell.rentACar.business.requests.invoice;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateInvoiceRequest {

	@NotNull
	@Positive
	private int id;

	@NotNull
	@Size(min = 2, max = 20)
	private String invoiceNumber;

	@NotNull
	@Positive
	private int rentalCarId;
}
