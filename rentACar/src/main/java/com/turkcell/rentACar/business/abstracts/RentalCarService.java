package com.turkcell.rentACar.business.abstracts;

import java.util.List;

import org.springframework.data.domain.Sort;

import com.turkcell.rentACar.api.models.rentalCar.CreateRentalCarModel;
import com.turkcell.rentACar.api.models.rentalCar.UpdateRentalCarModel;
import com.turkcell.rentACar.business.dtos.GetRentalCarDto;
import com.turkcell.rentACar.business.dtos.RentalCarListDto;
import com.turkcell.rentACar.business.requests.EndOfRentRequest;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;

public interface RentalCarService {

	Result addForCorporateCustomer(CreateRentalCarModel createRentalCarModel);

	Result addForIndividualCustomer(CreateRentalCarModel createRentalCarModel);

	Result update(UpdateRentalCarModel updateRentalCarModel);

	Result delete(int id);

	Result endOfRent(EndOfRentRequest endOfRentRequest);

	DataResult<GetRentalCarDto> getById(int id);

	DataResult<List<RentalCarListDto>> getAll();

	DataResult<List<RentalCarListDto>> getAllPaged(int pageNo, int pageSize);

	DataResult<List<RentalCarListDto>> getAllSorted(Sort.Direction direction);

	DataResult<List<RentalCarListDto>> getByCarId(int carId);
}
