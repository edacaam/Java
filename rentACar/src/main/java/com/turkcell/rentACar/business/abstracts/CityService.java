package com.turkcell.rentACar.business.abstracts;

import java.util.List;

import com.turkcell.rentACar.business.dtos.CityListDto;
import com.turkcell.rentACar.business.dtos.GetCityDto;
import com.turkcell.rentACar.business.requests.CreateCityRequest;
import com.turkcell.rentACar.business.requests.UpdateCityRequest;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;

public interface CityService {
	
	Result add(CreateCityRequest createCityRequest);
	Result delete(int id);
	Result update(UpdateCityRequest updateCityRequest);
	DataResult<GetCityDto> getById(int id);
	DataResult<List<CityListDto>> getAll();
}
