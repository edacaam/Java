package com.turkcell.rentACar.business.abstracts;

import java.util.List;

import com.turkcell.rentACar.business.dtos.AdditionalServiceListDto;
import com.turkcell.rentACar.business.dtos.GetAdditionalServiceDto;
import com.turkcell.rentACar.business.requests.CreateAdditionalServiceRequest;
import com.turkcell.rentACar.business.requests.UpdateAdditionalServiceRequest;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;

public interface AdditionalServiceService {
	Result add(CreateAdditionalServiceRequest createAdditionalServiceRequest);

	Result delete(int id);

	Result update(UpdateAdditionalServiceRequest updateAdditionalServiceRequest);

	DataResult<GetAdditionalServiceDto> getById(int id);

	DataResult<List<AdditionalServiceListDto>> getAll();

	DataResult<List<AdditionalServiceListDto>> getAllPaged(int pageNo, int pageSize);

	DataResult<List<AdditionalServiceListDto>> getAllSorted(String direction);

	void checkIfAdditionalServiceExistsById(int id);
}
