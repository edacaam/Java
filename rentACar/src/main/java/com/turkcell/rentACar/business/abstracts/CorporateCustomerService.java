package com.turkcell.rentACar.business.abstracts;

import java.util.List;

import com.turkcell.rentACar.business.dtos.CorporateCustomerListDto;
import com.turkcell.rentACar.business.dtos.GetCorporateCustomerDto;
import com.turkcell.rentACar.business.requests.CreateCorporateCustomerRequest;
import com.turkcell.rentACar.business.requests.UpdateCorporateCustomerRequest;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;

public interface CorporateCustomerService {
	Result add(CreateCorporateCustomerRequest createCorporateCustomerRequest);

	Result delete(int id);

	Result update(UpdateCorporateCustomerRequest updateCorporateCustomerRequest);

	DataResult<GetCorporateCustomerDto> getById(int id);

	DataResult<List<CorporateCustomerListDto>> getAll();
}
