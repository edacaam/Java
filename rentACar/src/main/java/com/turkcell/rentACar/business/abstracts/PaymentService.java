package com.turkcell.rentACar.business.abstracts;

import java.util.List;

import com.turkcell.rentACar.business.dtos.GetPaymentDto;
import com.turkcell.rentACar.business.dtos.PaymentListDto;
import com.turkcell.rentACar.business.requests.CreatePaymentRequest;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;

public interface PaymentService {

	Result delete(int id);

	Result add(CreatePaymentRequest createPaymentRequest);

	DataResult<List<PaymentListDto>> getAll();

	DataResult<GetPaymentDto> getById(int id);
}
