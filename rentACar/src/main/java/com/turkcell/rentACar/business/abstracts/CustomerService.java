package com.turkcell.rentACar.business.abstracts;

import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.entities.concretes.Customer;

public interface CustomerService {

	DataResult<Customer> getById(int id);

	void checkIfCustomerExistsById(int id);
}
