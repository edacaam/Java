package com.turkcell.rentACar.business.abstracts;

import com.turkcell.rentACar.business.requests.payment.CreatePaymentRequest;
import com.turkcell.rentACar.core.utilities.results.Result;

public interface PosService {
	Result makePayment(CreatePaymentRequest createPaymentRequest);
}
