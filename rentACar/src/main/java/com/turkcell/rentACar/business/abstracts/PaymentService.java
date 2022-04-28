package com.turkcell.rentACar.business.abstracts;

import java.util.List;

import com.turkcell.rentACar.api.models.rentalCar.payment.ExtraPaymentModel;
import com.turkcell.rentACar.api.models.rentalCar.payment.PaymentModel;
import com.turkcell.rentACar.business.dtos.payment.GetPaymentDto;
import com.turkcell.rentACar.business.dtos.payment.PaymentListDto;
import com.turkcell.rentACar.business.requests.payment.CreatePaymentRequest;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;
import com.turkcell.rentACar.entities.concretes.Invoice;
import com.turkcell.rentACar.entities.concretes.RentalCar;

public interface PaymentService {
	void addPayment(CreatePaymentRequest createPaymentRequest, Invoice invoice, RentalCar rentalCar);

	Result makePaymentForCorporateCustomer(PaymentModel paymentModel);

	Result makePaymentForIndividualCustomer(PaymentModel paymentModel);

	Result makeExtraPayment(ExtraPaymentModel extraPaymentModel);

	Result delete(int id);

	DataResult<List<PaymentListDto>> getAll();

	DataResult<GetPaymentDto> getById(int id);

}
