package com.turkcell.rentACar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.turkcell.rentACar.business.abstracts.CarDamageService;
import com.turkcell.rentACar.business.abstracts.CarService;
import com.turkcell.rentACar.business.constants.messages.BusinessMessages;
import com.turkcell.rentACar.business.dtos.CarDamageListDto;
import com.turkcell.rentACar.business.dtos.GetCarDamageDto;
import com.turkcell.rentACar.business.requests.CreateCarDamageRequest;
import com.turkcell.rentACar.business.requests.UpdateCarDamageRequest;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;
import com.turkcell.rentACar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentACar.core.utilities.results.SuccessResult;
import com.turkcell.rentACar.dataAccess.abstracts.CarDamageDao;
import com.turkcell.rentACar.entities.concretes.CarDamage;

@Service
public class CarDamageManager implements CarDamageService {

	private CarDamageDao carDamageDao;
	private ModelMapperService modelMapperService;
	private CarService carService;

	@Autowired
	public CarDamageManager(CarDamageDao carDamageDao, ModelMapperService modelMapperService, CarService carService) {
		this.carDamageDao = carDamageDao;
		this.modelMapperService = modelMapperService;
		this.carService = carService;
	}

	@Override
	public Result add(CreateCarDamageRequest createCarDamageRequest) {

		carService.checkIfCarExistsById(createCarDamageRequest.getCarId());

		CarDamage carDamage = this.modelMapperService.forRequest().map(createCarDamageRequest, CarDamage.class);
		carDamage.setId(0);
		carDamageDao.save(carDamage);

		return new SuccessResult(BusinessMessages.CAR_DAMAGE_ADDED_SUCCESSFULLY);
	}

	@Override
	public Result delete(int id) {

		checkIfCarDamageExistsById(id);

		this.carDamageDao.deleteById(id);

		return new SuccessResult(BusinessMessages.CAR_DAMAGE_DELETED_SUCCESSFULLY);
	}

	@Override
	public Result update(UpdateCarDamageRequest updateCarDamageRequest) {

		carService.checkIfCarExistsById(updateCarDamageRequest.getCarId());

		checkIfCarDamageExistsById(updateCarDamageRequest.getId());

		CarDamage carDamage = this.modelMapperService.forRequest().map(updateCarDamageRequest, CarDamage.class);

		carDamageDao.save(carDamage);

		return new SuccessResult(BusinessMessages.CAR_DAMAGE_UPDATED_SUCCESSFULLY);
	}

	@Override
	public DataResult<GetCarDamageDto> getById(int id) {

		checkIfCarDamageExistsById(id);

		CarDamage carDamage = this.carDamageDao.getById(id);

		GetCarDamageDto response = this.modelMapperService.forDto().map(carDamage, GetCarDamageDto.class);

		return new SuccessDataResult<>(response, BusinessMessages.CAR_DAMAGE_GET_SUCCESSFULLY);
	}

	@Override
	public DataResult<List<CarDamageListDto>> getAll() {

		List<CarDamage> result = this.carDamageDao.findAll();

		List<CarDamageListDto> response = result.stream()
				.map(carDamage -> this.modelMapperService.forDto().map(carDamage, CarDamageListDto.class))
				.collect(Collectors.toList());

		return new SuccessDataResult<>(response, BusinessMessages.CAR_DAMAGE_LISTED_SUCCESSFULLY);
	}

	@Override
	public DataResult<List<CarDamageListDto>> getAllPaged(int pageNo, int pageSize) {

		Pageable pageable = PageRequest.of(pageNo - 1, pageSize);

		List<CarDamage> result = this.carDamageDao.findAll(pageable).getContent();

		List<CarDamageListDto> response = result.stream()
				.map(carDamage -> this.modelMapperService.forDto().map(carDamage, CarDamageListDto.class))
				.collect(Collectors.toList());

		return new SuccessDataResult<>(response, BusinessMessages.CAR_DAMAGE_LISTED_SUCCESSFULLY);
	}

	@Override
	public DataResult<List<CarDamageListDto>> getAllSorted(Direction direction) {

		Sort sort = Sort.by(direction, "description");

		List<CarDamage> result = this.carDamageDao.findAll(sort);

		List<CarDamageListDto> response = result.stream()
				.map(carDamage -> this.modelMapperService.forDto().map(carDamage, CarDamageListDto.class))
				.collect(Collectors.toList());

		return new SuccessDataResult<>(response, BusinessMessages.CAR_DAMAGE_LISTED_SUCCESSFULLY);
	}

	private void checkIfCarDamageExistsById(int id) {
		if (!this.carDamageDao.existsById(id)) {
			throw new BusinessException(BusinessMessages.CAR_DAMAGE_NOT_FOUND);
		}
	}
}
