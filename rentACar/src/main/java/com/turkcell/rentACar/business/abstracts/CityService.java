package com.turkcell.rentACar.business.abstracts;

import java.util.List;

import com.turkcell.rentACar.business.dtos.city.CityListDto;
import com.turkcell.rentACar.business.dtos.city.GetCityDto;
import com.turkcell.rentACar.business.requests.city.CreateCityRequest;
import com.turkcell.rentACar.business.requests.city.UpdateCityRequest;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;

public interface CityService {

	Result add(CreateCityRequest createCityRequest);

	Result delete(int id);

	Result update(UpdateCityRequest updateCityRequest);

	DataResult<GetCityDto> getById(int id);

	DataResult<List<CityListDto>> getAll();

	void checkIfCityExistsById(int id);
}
