package com.turkcell.rentACar.business.abstracts;

import java.util.List;

import org.springframework.data.domain.Sort.Direction;

import com.turkcell.rentACar.business.dtos.CarDamageListDto;
import com.turkcell.rentACar.business.dtos.GetCarDamageDto;
import com.turkcell.rentACar.business.requests.CreateCarDamageRequest;
import com.turkcell.rentACar.business.requests.UpdateCarDamageRequest;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;

public interface CarDamageService {
	
	Result add(CreateCarDamageRequest createCarDamageRequest);

	Result delete(int id);

	Result update(UpdateCarDamageRequest updateCarDamageRequest);

	DataResult<GetCarDamageDto> getById(int id);

	DataResult<List<CarDamageListDto>> getAll();

	DataResult<List<CarDamageListDto>> getAllPaged(int pageNo, int pageSize);

	DataResult<List<CarDamageListDto>> getAllSorted(Direction direction);
}
