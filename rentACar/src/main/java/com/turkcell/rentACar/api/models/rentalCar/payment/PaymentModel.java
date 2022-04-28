package com.turkcell.rentACar.api.models.rentalCar.payment;

import javax.validation.Valid;

import com.turkcell.rentACar.api.models.rentalCar.rentalCar.CreateRentalCarModel;
import com.turkcell.rentACar.business.requests.payment.CreatePaymentRequest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentModel {
	@Valid
	CreateRentalCarModel rentalCarModel;

	@Valid
	CreatePaymentRequest paymentRequest;
}
