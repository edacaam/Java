package com.turkcell.rentACar.business.adapters.posAdapters;

import org.springframework.stereotype.Service;

import com.turkcell.rentACar.business.abstracts.PosService;
import com.turkcell.rentACar.business.outServices.IsBankPosManager;
import com.turkcell.rentACar.business.requests.CreatePaymentRequest;
import com.turkcell.rentACar.core.utilities.results.ErrorResult;
import com.turkcell.rentACar.core.utilities.results.Result;
import com.turkcell.rentACar.core.utilities.results.SuccessResult;

@Service
public class IsBankPosAdapter implements PosService {

	@Override
	public Result makePayment(CreatePaymentRequest createPaymentRequest) {
		IsBankPosManager isBankPosManager=new IsBankPosManager();
		boolean posResult= isBankPosManager.makePayment(createPaymentRequest.getCardHolder(),createPaymentRequest.getCardNo(),createPaymentRequest.getCvv(),createPaymentRequest.getMonth(),createPaymentRequest.getYear());
		if(posResult) 
		{
			return new SuccessResult();
		}
		return new ErrorResult() ;
	}

}
