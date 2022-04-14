package com.turkcell.rentACar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.turkcell.rentACar.business.abstracts.CarMaintenanceService;
import com.turkcell.rentACar.business.abstracts.CarService;
import com.turkcell.rentACar.business.abstracts.OrderedAdditionalServiceService;
import com.turkcell.rentACar.business.abstracts.RentalCarService;
import com.turkcell.rentACar.business.constants.messages.BusinessMessages;
import com.turkcell.rentACar.business.dtos.CarMaintenanceListDto;
import com.turkcell.rentACar.business.dtos.GetCarDto;
import com.turkcell.rentACar.business.dtos.GetRentalCarDto;
import com.turkcell.rentACar.business.dtos.RentalCarListDto;
import com.turkcell.rentACar.business.requests.CreateRentalCarRequest;
import com.turkcell.rentACar.business.requests.EndOfRentRequest;
import com.turkcell.rentACar.business.requests.UpdateRentalCarRequest;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.ErrorDataResult;
import com.turkcell.rentACar.core.utilities.results.Result;
import com.turkcell.rentACar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentACar.core.utilities.results.SuccessResult;
import com.turkcell.rentACar.dataAccess.abstracts.RentalCarDao;
import com.turkcell.rentACar.entities.concretes.RentalCar;

@Service
public class RentalCarManager implements RentalCarService {

	private RentalCarDao rentalCarDao;
	private ModelMapperService modelMapperService;
	private CarMaintenanceService carMaintenanceService;
	private CarService carService;
	private OrderedAdditionalServiceService orderedAdditionalServiceService;

	@Autowired
	public RentalCarManager(RentalCarDao rentalCarDao, ModelMapperService modelMapperService,
			CarMaintenanceService carMaintenanceService, CarService carService,
			OrderedAdditionalServiceService orderedAdditionalServiceService) {
		this.rentalCarDao = rentalCarDao;
		this.modelMapperService = modelMapperService;
		this.carMaintenanceService = carMaintenanceService;
		this.carService = carService;
		this.orderedAdditionalServiceService = orderedAdditionalServiceService;
	}

	@Override
	public Result add(CreateRentalCarRequest createRentalCarRequest) {

		checkIfCarIsExists(createRentalCarRequest.getCarId());

		RentalCar rentalCar = this.modelMapperService.forRequest().map(createRentalCarRequest, RentalCar.class);

		rentalCar.getOrderedAdditionalServices()
				.forEach(orderedAdditionalService -> orderedAdditionalService.setRentalCar(rentalCar));
		checkIfCarIsInMaintenance(rentalCar);

		rentalCar.setStartingKilometer(carService.getById(rentalCar.getCar().getId()).getData().getKilometer());
		rentalCar.setRentalCarId(0);

		this.rentalCarDao.save(rentalCar);
		return new SuccessResult(BusinessMessages.RENTAL_CAR_ADDED_SUCCESSFULLY);
	}

	@Override
	public DataResult<GetRentalCarDto> getById(int rentalId) {

		RentalCar result = this.rentalCarDao.getById(rentalId);

		GetRentalCarDto response = this.modelMapperService.forDto().map(result, GetRentalCarDto.class);

		return new SuccessDataResult<>(response, BusinessMessages.RENTAL_CAR_GET_SUCCESSFULLY);
	}

	@Override
	public DataResult<List<RentalCarListDto>> getAll() {

		List<RentalCar> result = this.rentalCarDao.findAll();

		List<RentalCarListDto> response = result.stream()
				.map(rentalCar -> this.modelMapperService.forDto().map(rentalCar, RentalCarListDto.class))
				.collect(Collectors.toList());

		return new SuccessDataResult<>(response, BusinessMessages.RENTAL_CAR_LISTED_SUCCESSFULLY);
	}

	@Override
	public DataResult<List<RentalCarListDto>> getAllPaged(int pageNo, int pageSize) {

		Pageable pageable = PageRequest.of(pageNo - 1, pageSize);

		List<RentalCar> result = this.rentalCarDao.findAll(pageable).getContent();

		List<RentalCarListDto> response = result.stream()
				.map(rentalCar -> this.modelMapperService.forDto().map(rentalCar, RentalCarListDto.class))
				.collect(Collectors.toList());

		return new SuccessDataResult<>(response, BusinessMessages.RENTAL_CAR_LISTED_SUCCESSFULLY);
	}

	@Override
	public DataResult<List<RentalCarListDto>> getAllSorted(Direction direction) {

		Sort sort = Sort.by(direction, "endDate");

		List<RentalCar> result = this.rentalCarDao.findAll(sort);

		List<RentalCarListDto> response = result.stream()
				.map(rentalCar -> this.modelMapperService.forDto().map(rentalCar, RentalCarListDto.class))
				.collect(Collectors.toList());

		return new SuccessDataResult<>(response, BusinessMessages.RENTAL_CAR_LISTED_SUCCESSFULLY);
	}

	@Override
	public DataResult<List<RentalCarListDto>> getByCarId(int carId) {
		List<RentalCar> result = this.rentalCarDao.findByCarId(carId);

		if (result.isEmpty()) {
			return new ErrorDataResult<>("Rental cars could not be listed.");
		}

		List<RentalCarListDto> response = result.stream()
				.map(rentalCar -> this.modelMapperService.forDto().map(rentalCar, RentalCarListDto.class))
				.collect(Collectors.toList());
		return new SuccessDataResult<>(response, BusinessMessages.RENTAL_CAR_LISTED_SUCCESSFULLY);
	}

	@Override
	public Result update(UpdateRentalCarRequest updateRentalCarRequest) {

		checkIfRentalCarIsExists(updateRentalCarRequest.getRentalCarId());
		checkIfCarIsExists(updateRentalCarRequest.getCarId());

		RentalCar rentalCarUpdate = this.modelMapperService.forRequest().map(updateRentalCarRequest, RentalCar.class);

		orderedAdditionalServiceService.deleteAll(rentalCarUpdate.getRentalCarId());

		rentalCarUpdate.getOrderedAdditionalServices()
				.forEach(orderedAdditionalService -> orderedAdditionalService.setRentalCar(rentalCarUpdate));
		rentalCarUpdate
				.setStartingKilometer(carService.getById(rentalCarUpdate.getCar().getId()).getData().getKilometer());

		this.rentalCarDao.save(rentalCarUpdate);
		return new SuccessResult(BusinessMessages.RENTAL_CAR_UPDATED_SUCCESSFULLY);
	}

	@Override
	public Result delete(int id) {
		checkIfRentalCarIsExists(id);
		this.rentalCarDao.deleteById(id);
		return new SuccessResult(BusinessMessages.RENTAL_CAR_DELETED_SUCCESSFULLY);
	}

	@Override
	public Result endOfRent(EndOfRentRequest endOfRentRequest) {

		RentalCar rentalCar = rentalCarDao.getById(endOfRentRequest.getId());

		rentalCar.setEndingKilometer(endOfRentRequest.getEndingKilometer());

		carService.updateKilometer(rentalCar.getCar().getId(), rentalCar.getEndingKilometer());

		rentalCarDao.save(rentalCar);

		return new SuccessResult("Ending kilometer recorded, car rental is over.");
	}

	public boolean checkIfRentalCarIsExists(int rentalCarId) {
		if (this.rentalCarDao.findById(rentalCarId) == null) {
			throw new BusinessException("No rental car found with the id");
		}
		return true;
	}

	private void checkIfCarIsExists(int carId) {

		GetCarDto result = this.carService.getById(carId).getData();

		if (result == null) {
			throw new BusinessException(BusinessMessages.CAR_NOT_FOUND);
		}
	}

	private void checkIfCarIsInMaintenance(RentalCar rentalCar) {
		List<CarMaintenanceListDto> result = this.carMaintenanceService.getByCarId(rentalCar.getCar().getId())
				.getData();
		for (CarMaintenanceListDto carMaintenanceDto : result) {
			if ((carMaintenanceDto.getReturnDate() != null)
					&& (rentalCar.getStartingDate().isBefore(carMaintenanceDto.getReturnDate())
							|| rentalCar.getEndDate().isBefore(carMaintenanceDto.getReturnDate()))) {
				throw new BusinessException("This car cannot be rented as it is under maintenance");
			}
		}
	}
}
