package com.turkcell.rentACar.business.abstracts;

import java.util.List;

import org.springframework.data.domain.Sort.Direction;

import com.turkcell.rentACar.business.dtos.carDamage.CarDamageListDto;
import com.turkcell.rentACar.business.dtos.carDamage.GetCarDamageDto;
import com.turkcell.rentACar.business.requests.carDamage.CreateCarDamageRequest;
import com.turkcell.rentACar.business.requests.carDamage.UpdateCarDamageRequest;
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
