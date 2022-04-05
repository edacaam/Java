package com.turkcell.rentACar.business.concretes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turkcell.rentACar.business.abstracts.OrderedAdditionalServiceService;
import com.turkcell.rentACar.business.constants.messages.BusinessMessages;
import com.turkcell.rentACar.core.utilities.results.Result;
import com.turkcell.rentACar.core.utilities.results.SuccessResult;
import com.turkcell.rentACar.dataAccess.abstracts.OrderedAdditonalServiceDao;

@Service
public class OrderedAdditionalServiceManager implements OrderedAdditionalServiceService {

	private OrderedAdditonalServiceDao orderedAdditionalServiceDao;
	
	@Autowired
	public OrderedAdditionalServiceManager(OrderedAdditonalServiceDao orderedAdditionalServiceDao) {
		this.orderedAdditionalServiceDao = orderedAdditionalServiceDao;
	}

	@Override
	public Result deleteAll(int rentalCarId) {
		orderedAdditionalServiceDao.deleteAll(orderedAdditionalServiceDao.findByRentalCar_RentalCarId(rentalCarId));
		return new SuccessResult(BusinessMessages.ORDERED_ADDITIONAL_SERVICE_DELETED_SUCCESSFULLY);
		
	}



}
