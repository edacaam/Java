package com.turkcell.rentACar.business.adapters.posAdapters;

import org.springframework.stereotype.Service;

import com.turkcell.rentACar.business.abstracts.PosService;
import com.turkcell.rentACar.business.constants.messages.BusinessMessages;
import com.turkcell.rentACar.business.outServices.IsBankPosManager;
import com.turkcell.rentACar.business.requests.payment.CreatePaymentRequest;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.results.Result;
import com.turkcell.rentACar.core.utilities.results.SuccessResult;

@Service
public class IsBankPosAdapter implements PosService {

	@Override
	public Result makePayment(CreatePaymentRequest createPaymentRequest) {
		IsBankPosManager isBankPosManager = new IsBankPosManager();
		boolean posResult = isBankPosManager.makePayment(createPaymentRequest.getCardHolder(),
				createPaymentRequest.getCardNo(), createPaymentRequest.getCvv(), createPaymentRequest.getMonth(),
				createPaymentRequest.getYear());
		if (posResult) {
			return new SuccessResult();
		}
		throw new BusinessException(BusinessMessages.IS_BANK_PAYMENT_FAILED);
	}

}
