package com.turkcell.rentACar.business.concretes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turkcell.rentACar.business.abstracts.CustomerService;
import com.turkcell.rentACar.business.constants.messages.BusinessMessages;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentACar.dataAccess.abstracts.CustomerDao;
import com.turkcell.rentACar.entities.concretes.Customer;

@Service
public class CustomerManager implements CustomerService {

	private CustomerDao customerDao;

	@Autowired
	public CustomerManager(CustomerDao customerDao) {
		this.customerDao = customerDao;
	}

	@Override
	public DataResult<Customer> getById(int id) {
		Customer customer = this.customerDao.getById(id);
		return new SuccessDataResult<>(customer, BusinessMessages.CUSTOMER_GET_SUCCESSFULLY);
	}

	@Override
	public void checkIfCustomerExistsById(int id) {
		if (!this.customerDao.existsById(id)) {
			throw new BusinessException(BusinessMessages.CUSTOMER_NOT_FOUND);
		}
	}
}
