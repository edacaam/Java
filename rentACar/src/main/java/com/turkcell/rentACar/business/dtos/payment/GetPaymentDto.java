package com.turkcell.rentACar.business.dtos.payment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetPaymentDto {

	private int id;

	private double amount;

	private int rentalCarId;

	private int invoiceId;

	private int customerId;
}
