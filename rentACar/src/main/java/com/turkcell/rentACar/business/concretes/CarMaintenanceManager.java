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

		carService.checkIfCarExistsById(createCarMaintenanceRequest.getCarId());
		checkIfCarAlreadyUnderMaintenance(createCarMaintenanceRequest);

		CarMaintenance carMaintenance = this.modelMapperService.forRequest().map(createCarMaintenanceRequest,
				CarMaintenance.class);

		checkIfCarIsRented(carMaintenance);

		carMaintenance.setId(0);

		carMaintenanceDao.save(carMaintenance);

		return new SuccessResult(BusinessMessages.CAR_MAINTENANCE_ADDED_SUCCESSFULLY);
	}

	@Override
	public Result delete(int id) {

		checkIfCarMaintenanceExistsById(id);

		this.carMaintenanceDao.deleteById(id);

		return new SuccessResult(BusinessMessages.CAR_MAINTENANCE_DELETED_SUCCESSFULLY);
	}

	@Override
	public Result update(UpdateCarMaintenanceRequest updateCarMaintenanceRequest) {

		carService.checkIfCarExistsById(updateCarMaintenanceRequest.getCarId());
		checkIfCarMaintenanceExistsById(updateCarMaintenanceRequest.getId());
		checkIfUpdatedCarAlreadyUnderMaintenance(updateCarMaintenanceRequest);

		CarMaintenance carMaintenance = this.modelMapperService.forRequest().map(updateCarMaintenanceRequest,
				CarMaintenance.class);

		checkIfCarIsRented(carMaintenance);

		carMaintenanceDao.save(carMaintenance);

		return new SuccessResult(BusinessMessages.CAR_MAINTENANCE_UPDATED_SUCCESSFULLY);
	}

	@Override
	public DataResult<GetCarMaintenanceDto> getById(int id) {

		checkIfCarMaintenanceExistsById(id);

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

		carService.checkIfCarExistsById(carId);

		List<CarMaintenance> result = this.carMaintenanceDao.findByCarId(carId);

		List<CarMaintenanceListDto> response = result.stream().map(
				carMaintenance -> this.modelMapperService.forDto().map(carMaintenance, CarMaintenanceListDto.class))
				.collect(Collectors.toList());

		return new SuccessDataResult<>(response, BusinessMessages.CAR_MAINTENANCE_LISTED_SUCCESSFULLY);
	}

	private void checkIfCarMaintenanceExistsById(int id) {
		if (!this.carMaintenanceDao.existsById(id)) {
			throw new BusinessException(BusinessMessages.CAR_MAINTENANCE_NOT_FOUND);
		}
	}

	private void checkIfCarIsRented(CarMaintenance carMaintenance) {
		List<RentalCarListDto> result = this.rentalCarService.getByCarId(carMaintenance.getCar().getId()).getData();

		if (result != null) {
			for (RentalCarListDto rentalCarDto : result) {
				if (carMaintenance.getReturnDate().isAfter(rentalCarDto.getStartingDate())
						|| carMaintenance.getReturnDate().isEqual(rentalCarDto.getStartingDate())
								&& carMaintenance.getReturnDate().isBefore(rentalCarDto.getEndDate())
						|| carMaintenance.getReturnDate().isEqual(rentalCarDto.getEndDate())) {
					throw new BusinessException(BusinessMessages.CAR_RENTED_IN_MAINTENANCE_DATE);
				}
			}
		}
	}

	private void checkIfCarAlreadyUnderMaintenance(CreateCarMaintenanceRequest createCarMaintenanceRequest) {
		List<CarMaintenance> carMaintenances = carMaintenanceDao.findByCarId(createCarMaintenanceRequest.getCarId());
		for (CarMaintenance carMaintenance : carMaintenances) {

			if (carMaintenance.getReturnDate() == null
					|| carMaintenance.getReturnDate().isAfter(createCarMaintenanceRequest.getReturnDate())) {
				throw new BusinessException(BusinessMessages.CAR_ALREADY_UNDER_MAINTENANCE);
			}
		}
	}

	private void checkIfUpdatedCarAlreadyUnderMaintenance(UpdateCarMaintenanceRequest updateCarMaintenanceRequest) {
		List<CarMaintenance> carMaintenances = carMaintenanceDao.findByCarId(updateCarMaintenanceRequest.getCarId());
		for (CarMaintenance carMaintenance : carMaintenances) {

			if (!updateCarMaintenanceRequest.getId().equals(carMaintenance.getId())
					&& (carMaintenance.getReturnDate() == null
							|| carMaintenance.getReturnDate().isAfter(updateCarMaintenanceRequest.getReturnDate()))) {
				throw new BusinessException(BusinessMessages.CAR_ALREADY_UNDER_MAINTENANCE);
			}
		}
	}
}
