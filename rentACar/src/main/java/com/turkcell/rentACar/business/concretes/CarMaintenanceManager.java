package com.turkcell.rentACar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.turkcell.rentACar.business.abstracts.CarMaintenanceService;
import com.turkcell.rentACar.business.abstracts.CarService;
import com.turkcell.rentACar.business.abstracts.RentalCarService;
import com.turkcell.rentACar.business.constants.messages.BusinessMessages;
import com.turkcell.rentACar.business.dtos.CarMaintenanceListDto;
import com.turkcell.rentACar.business.dtos.GetCarDto;
import com.turkcell.rentACar.business.dtos.GetCarMaintenanceDto;
import com.turkcell.rentACar.business.dtos.RentalCarListDto;
import com.turkcell.rentACar.business.requests.CreateCarMaintenanceRequest;
import com.turkcell.rentACar.business.requests.UpdateCarMaintenanceRequest;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;
import com.turkcell.rentACar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentACar.core.utilities.results.SuccessResult;
import com.turkcell.rentACar.dataAccess.abstracts.CarMaintenanceDao;
import com.turkcell.rentACar.entities.concretes.CarMaintenance;

@Service
public class CarMaintenanceManager implements CarMaintenanceService {

	private CarMaintenanceDao carMaintenanceDao;
	private ModelMapperService modelMapperService;
	private RentalCarService rentalCarService;
	private CarService carService;

	@Autowired
	public CarMaintenanceManager(CarMaintenanceDao carMaintenanceDao, ModelMapperService modelMapperService,
			@Lazy RentalCarService rentalCarService, CarService carService) {
		this.carMaintenanceDao = carMaintenanceDao;
		this.modelMapperService = modelMapperService;
		this.rentalCarService = rentalCarService;
		this.carService = carService;
	}

	@Override
	public Result add(CreateCarMaintenanceRequest createCarMaintenanceRequest) {
		checkIfCarIsAvaliable(createCarMaintenanceRequest.getCarId());
		CarMaintenance carMaintenance = this.modelMapperService.forRequest().map(createCarMaintenanceRequest,
				CarMaintenance.class);
		checkIfIsRent(carMaintenance);
		carMaintenanceDao.save(carMaintenance);
		return new SuccessResult(BusinessMessages.CAR_MAINTENANCE_ADDED_SUCCESSFULLY);
	}

	@Override
	public Result delete(int id) {
		this.carMaintenanceDao.deleteById(id);
		return new SuccessResult(BusinessMessages.CAR_MAINTENANCE_DELETED_SUCCESSFULLY);
	}

	@Override
	public Result update(UpdateCarMaintenanceRequest updateCarMaintenanceRequest) {
		CarMaintenance carMaintenance = this.modelMapperService.forRequest().map(updateCarMaintenanceRequest,
				CarMaintenance.class);
		carMaintenanceDao.save(carMaintenance);
		return new SuccessResult(BusinessMessages.CAR_MAINTENANCE_UPDATED_SUCCESSFULLY);
	}

	@Override
	public DataResult<GetCarMaintenanceDto> getById(int id) {
		CarMaintenance carMaintenance = this.carMaintenanceDao.findById(id);
		GetCarMaintenanceDto response = this.modelMapperService.forDto().map(carMaintenance,
				GetCarMaintenanceDto.class);
		return new SuccessDataResult<>(response, BusinessMessages.CAR_MAINTENANCE_GET_SUCCESSFULLY);
	}

	@Override
	public DataResult<List<CarMaintenanceListDto>> getAll() {
		List<CarMaintenance> result = this.carMaintenanceDao.findAll();
		List<CarMaintenanceListDto> response = result.stream().map(
				carMaintenance -> this.modelMapperService.forDto().map(carMaintenance, CarMaintenanceListDto.class))
				.collect(Collectors.toList());
		return new SuccessDataResult<>(response, BusinessMessages.CAR_MAINTENANCE_LISTED_SUCCESSFULLY);
	}

	@Override
	public DataResult<List<CarMaintenanceListDto>> getByCarId(int carId) {
		List<CarMaintenance> result = this.carMaintenanceDao.findByCarId(carId);
		List<CarMaintenanceListDto> response = result.stream().map(
				carMaintenance -> this.modelMapperService.forDto().map(carMaintenance, CarMaintenanceListDto.class))
				.collect(Collectors.toList());
		return new SuccessDataResult<>(response, BusinessMessages.CAR_MAINTENANCE_LISTED_SUCCESSFULLY);
	}

	private void checkIfCarIsAvaliable(int id) {
		DataResult<GetCarDto> result = this.carService.getById(id);
		if (!result.isSuccess()) {
			throw new BusinessException("The car with this id does not exist.");
		}
	}

	private boolean checkIfIsRent(CarMaintenance carMaintenance) {
		DataResult<List<RentalCarListDto>> result = this.rentalCarService.getByCarId(carMaintenance.getCar().getId());
		if (result.getData() == null) {
			return true;
		}
		for (RentalCarListDto rentalCar : result.getData()) {
			if ((rentalCar.getEndDate() != null) && (carMaintenance.getReturnDate().isAfter(rentalCar.getStartingDate())
					&& carMaintenance.getReturnDate().isBefore(rentalCar.getEndDate()))) {
				throw new BusinessException("The car cannot be sent for maintenance because it is on rent.");
			}
			if ((rentalCar.getEndDate() == null) && (carMaintenance.getReturnDate().isAfter(rentalCar.getStartingDate())
					|| carMaintenance.getReturnDate().equals(rentalCar.getStartingDate()))) {
				throw new BusinessException(
						"The car cannot be sent for maintenance because it is on rent. / null end date.");
			}
		}
		return true;
	}

}
