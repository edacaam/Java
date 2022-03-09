package com.turkcell.rentACar.business.abstracts;

import java.util.List;

import org.springframework.data.domain.Sort;

import com.turkcell.rentACar.business.dtos.GetRentalCarDto;
import com.turkcell.rentACar.business.dtos.RentalCarListDto;
import com.turkcell.rentACar.business.requests.CreateRentalCarRequest;
import com.turkcell.rentACar.business.requests.UpdateRentalCarRequest;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;

public interface RentalCarService {
	Result add(CreateRentalCarRequest createRentalCarRequest);

	Result update(UpdateRentalCarRequest updateRentalCarRequest);

	Result delete(int id);

	DataResult<GetRentalCarDto> getById(int id);

	DataResult<List<RentalCarListDto>> getAll();

	DataResult<List<RentalCarListDto>> getAllPaged(int pageNo, int pageSize);

	DataResult<List<RentalCarListDto>> getAllSorted(Sort.Direction direction);

	DataResult<List<RentalCarListDto>> getByCarId(int carId);
}
