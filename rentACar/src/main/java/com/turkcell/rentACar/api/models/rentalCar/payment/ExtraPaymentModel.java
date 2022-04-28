package com.turkcell.rentACar.api.models.rentalCar.payment;

import javax.validation.Valid;

import com.turkcell.rentACar.business.requests.payment.CreatePaymentRequest;
import com.turkcell.rentACar.business.requests.rentalCar.EndOfRentRequest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExtraPaymentModel {
	@Valid
	EndOfRentRequest endOfRentRequest;

	@Valid
	CreatePaymentRequest paymentRequest;
}
