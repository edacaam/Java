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
import com.turkcell.rentACar.business.abstracts.RentalCarService;
import com.turkcell.rentACar.business.dtos.CarMaintenanceListDto;
import com.turkcell.rentACar.business.dtos.GetCarDto;
import com.turkcell.rentACar.business.dtos.GetRentalCarDto;
import com.turkcell.rentACar.business.dtos.RentalCarListDto;
import com.turkcell.rentACar.business.requests.CreateRentalCarRequest;
import com.turkcell.rentACar.business.requests.UpdateRentalCarRequest;
import com.turkcell.rentACar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.ErrorDataResult;
import com.turkcell.rentACar.core.utilities.results.Result;
import com.turkcell.rentACar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentACar.core.utilities.results.SuccessResult;
import com.turkcell.rentACar.dataAccess.abstracts.RentalCarDao;
import com.turkcell.rentACar.entities.concretes.RentalCar;
import com.turkcell.rentACar.exceptions.concretes.BusinessException;

@Service
public class RentalCarManager implements RentalCarService {
	private RentalCarDao rentalCarDao;
	private ModelMapperService modelMapperService;
	private CarMaintenanceService carMaintenanceService;
	private CarService carService;

	@Autowired
	public RentalCarManager(RentalCarDao rentalCarDao, ModelMapperService modelMapperService,
			CarMaintenanceService carMaintenanceService, CarService carService) {
		this.rentalCarDao = rentalCarDao;
		this.modelMapperService = modelMapperService;
		this.carMaintenanceService = carMaintenanceService;
		this.carService = carService;
	}

	@Override
	public Result add(CreateRentalCarRequest createRentalCarRequest) {
		checkIfCarIsAvaliable(createRentalCarRequest.getCarId());

		RentalCar rentalCar = this.modelMapperService.forRequest().map(createRentalCarRequest, RentalCar.class);

		checkIfInMaintenance(rentalCar);

		this.rentalCarDao.save(rentalCar);
		return new SuccessResult("Rental car added successfully.");
	}
	
	@Override
	public DataResult<GetRentalCarDto> getById(int rentalId) {

		RentalCar result = this.rentalCarDao.findById(rentalId);

		if (result == null) {
			return new ErrorDataResult<GetRentalCarDto>("No car found with the id");
		}

		GetRentalCarDto response = this.modelMapperService.forDto().map(result, GetRentalCarDto.class);

		return new SuccessDataResult<GetRentalCarDto>(response, "Rental car has been received successfully.");
	}

	@Override
	public DataResult<List<RentalCarListDto>> getAll() {

		List<RentalCar> result = this.rentalCarDao.findAll();

		if (result.isEmpty()) {
			return new ErrorDataResult<List<RentalCarListDto>>("There is no rental car.");
		}

		List<RentalCarListDto> response = result.stream()
				.map(rentalCar -> this.modelMapperService.forDto().map(rentalCar, RentalCarListDto.class))
				.collect(Collectors.toList());

		return new SuccessDataResult<List<RentalCarListDto>>(response, "Rental car has been received successfully.");
	}

	@Override
	public DataResult<List<RentalCarListDto>> getAllPaged(int pageNo, int pageSize) {

		Pageable pageable = PageRequest.of(pageNo - 1, pageSize);

		List<RentalCar> result = this.rentalCarDao.findAll(pageable).getContent();

		if (result.isEmpty()) {
			return new ErrorDataResult<List<RentalCarListDto>>("Rental cars could not be listed");
		}

		List<RentalCarListDto> response = result.stream()
				.map(rentalCar -> this.modelMapperService.forDto().map(rentalCar, RentalCarListDto.class))
				.collect(Collectors.toList());

		return new SuccessDataResult<List<RentalCarListDto>>(response, "Rental cars listed successfully.");
	}

	@Override
	public DataResult<List<RentalCarListDto>> getAllSorted(Direction direction) {

		Sort sort = Sort.by(direction, "endDate");

		List<RentalCar> result = this.rentalCarDao.findAll(sort);

		if (result.isEmpty()) {
			return new ErrorDataResult<List<RentalCarListDto>>("Rental cars could not be listed.");
		}

		List<RentalCarListDto> response = result.stream()
				.map(rentalCar -> this.modelMapperService.forDto().map(rentalCar, RentalCarListDto.class))
				.collect(Collectors.toList());

		return new SuccessDataResult<List<RentalCarListDto>>(response, "Rental cars listed successfully.");
	}

	@Override
	public  DataResult<List<RentalCarListDto>> getByCarId(int carId) {
		List<RentalCar> result = this.rentalCarDao.findByCar_CarId(carId);
		
		if (result.isEmpty()) {
			return new ErrorDataResult<List<RentalCarListDto>>("Rental cars could not be listed.");
		}
		
		List<RentalCarListDto> response = result.stream()
				.map(rentalCar -> this.modelMapperService.forDto().map(rentalCar, RentalCarListDto.class))
				.collect(Collectors.toList());
		return new SuccessDataResult<List<RentalCarListDto>>(response, "Rental cars listed successfully.");
	}

	@Override
	public Result update(UpdateRentalCarRequest updateRentalCarRequest) {

		RentalCar rentalCar = this.rentalCarDao.findById(updateRentalCarRequest.getRentalCarId());

		checkIfParameterIsNull(updateRentalCarRequest, rentalCar);

		RentalCar rentalCarUpdate = this.modelMapperService.forRequest().map(updateRentalCarRequest, RentalCar.class);

		this.rentalCarDao.save(rentalCarUpdate);
		return new SuccessResult("Rental car is updated.");
	}

	private UpdateRentalCarRequest checkIfParameterIsNull(UpdateRentalCarRequest updateRentalCarRequest,
			RentalCar rentalCar) {
		
		if (updateRentalCarRequest.getStartingDate() == null) {
			updateRentalCarRequest.setStartingDate(rentalCar.getStartingDate());
		}
		
		if (updateRentalCarRequest.getEndDate() == null) {
			updateRentalCarRequest.setEndDate(rentalCar.getEndDate());
		}
		
		return updateRentalCarRequest;
	}

	@Override
	public Result delete(int id) {
		checkIfRentalCar(id);
		this.rentalCarDao.deleteById(id);
		return new SuccessResult("Renatl car deleted successfully.");
	}

	public boolean checkIfRentalCar(int rentalId) {
		if (this.rentalCarDao.findById(rentalId) == null) {
			throw new BusinessException("No rental car found with the id");
		}
		
		return true;
	}
	private void checkIfCarIsAvaliable(int carId) {

		GetCarDto result = this.carService.getById(carId).getData();

		if (result == null) {
			throw new BusinessException("No car found with this id.");
		}
	}

	private void checkIfInMaintenance(RentalCar rentalCar) {
		List<CarMaintenanceListDto> result = this.carMaintenanceService.getByCarId(rentalCar.getCar().getCarId())
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
