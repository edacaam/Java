package com.turkcell.rentACar.business.abstracts;

import java.util.List;

import com.turkcell.rentACar.business.dtos.car.CarListDto;
import com.turkcell.rentACar.business.dtos.car.GetCarDto;
import com.turkcell.rentACar.business.requests.car.CreateCarRequest;
import com.turkcell.rentACar.business.requests.car.UpdateCarRequest;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;

public interface CarService {
	Result add(CreateCarRequest createCarRequest);

	Result delete(int id);

	Result update(UpdateCarRequest updateCarRequest);

	Result updateKilometer(int id, double kilometer);

	DataResult<List<CarListDto>> getByDailyPrice(double dailyPrice);

	DataResult<GetCarDto> getById(int id);

	DataResult<List<CarListDto>> getAll();

	DataResult<List<CarListDto>> getAllPaged(int pageNo, int pageSize);

	DataResult<List<CarListDto>> getAllSorted(String direction);

	void checkIfCarExistsById(int id);
}
