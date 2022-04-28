package com.turkcell.rentACar.business.abstracts;

import java.util.List;

import org.springframework.data.domain.Sort;

import com.turkcell.rentACar.api.models.rentalCar.rentalCar.CreateRentalCarModel;
import com.turkcell.rentACar.api.models.rentalCar.rentalCar.UpdateRentalCarModel;
import com.turkcell.rentACar.business.dtos.rentalCar.GetRentalCarDto;
import com.turkcell.rentACar.business.dtos.rentalCar.RentalCarListDto;
import com.turkcell.rentACar.business.requests.rentalCar.EndOfRentRequest;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;
import com.turkcell.rentACar.entities.concretes.RentalCar;

public interface RentalCarService {

	DataResult<RentalCar> addForCorporateCustomer(CreateRentalCarModel createRentalCarModel);

	DataResult<RentalCar> addForIndividualCustomer(CreateRentalCarModel createRentalCarModel);

	Result update(UpdateRentalCarModel updateRentalCarModel);

	Result delete(int id);

	Result endOfRent(EndOfRentRequest endOfRentRequest);

	DataResult<GetRentalCarDto> getById(int id);

	DataResult<List<RentalCarListDto>> getAll();

	DataResult<List<RentalCarListDto>> getAllPaged(int pageNo, int pageSize);

	DataResult<List<RentalCarListDto>> getAllSorted(Sort.Direction direction);

	DataResult<List<RentalCarListDto>> getByCarId(int carId);

	void checkIfRentalCarIsExistsById(int rentalCarId);
}
