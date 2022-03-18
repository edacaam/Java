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
import com.turkcell.rentACar.business.dtos.CarDamageListDto;
import com.turkcell.rentACar.business.dtos.GetCarDamageDto;
import com.turkcell.rentACar.business.dtos.RentalCarListDto;
import com.turkcell.rentACar.business.requests.CreateCarDamageRequest;
import com.turkcell.rentACar.business.requests.UpdateCarDamageRequest;
import com.turkcell.rentACar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;
import com.turkcell.rentACar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentACar.core.utilities.results.SuccessResult;
import com.turkcell.rentACar.dataAccess.abstracts.CarDamageDao;
import com.turkcell.rentACar.entities.concretes.CarDamage;
import com.turkcell.rentACar.entities.concretes.RentalCar;

@Service
public class CarDamageManager implements CarDamageService {

	private CarDamageDao carDamageDao;
	private ModelMapperService modelMapperService;

	@Autowired
	public CarDamageManager(CarDamageDao carDamageDao, ModelMapperService modelMapperService) {
		this.carDamageDao = carDamageDao;
		this.modelMapperService = modelMapperService;
	}

	@Override
	public Result add(CreateCarDamageRequest createCarDamageRequest) {
		CarDamage carDamage = this.modelMapperService.forRequest().map(createCarDamageRequest, CarDamage.class);
		carDamageDao.save(carDamage);
		return new SuccessResult("Car damage added successfully.");
	}

	@Override
	public Result delete(int id) {
		this.carDamageDao.deleteById(id);
		return new SuccessResult("Car damage deleted successfully.");
	}

	@Override
	public Result update(UpdateCarDamageRequest updateCarDamageRequest) {
		CarDamage carDamage = this.modelMapperService.forRequest().map(updateCarDamageRequest, CarDamage.class);
		carDamageDao.save(carDamage);
		return new SuccessResult("Car damage updated successfully.");
	}

	@Override
	public DataResult<GetCarDamageDto> getById(int id) {
		CarDamage carDamage = this.carDamageDao.getById(id);
		GetCarDamageDto response = this.modelMapperService.forDto().map(carDamage, GetCarDamageDto.class);
		return new SuccessDataResult<GetCarDamageDto>(response, "Car damage has been received successfully.");
	}

	@Override
	public DataResult<List<CarDamageListDto>> getAll() {
		List<CarDamage> result = this.carDamageDao.findAll();
		List<CarDamageListDto> response = result.stream()
				.map(carDamage -> this.modelMapperService.forDto().map(carDamage, CarDamageListDto.class))
				.collect(Collectors.toList());
		return new SuccessDataResult<List<CarDamageListDto>>(response, "Car damages listed successfully.");
	}

	@Override
	public DataResult<List<CarDamageListDto>> getAllPaged(int pageNo, int pageSize) {
		Pageable pageable = PageRequest.of(pageNo - 1, pageSize);

		List<CarDamage> result = this.carDamageDao.findAll(pageable).getContent();

		List<CarDamageListDto> response = result.stream()
				.map(carDamage -> this.modelMapperService.forDto().map(carDamage, CarDamageListDto.class))
				.collect(Collectors.toList());

		return new SuccessDataResult<List<CarDamageListDto>>(response, "Car damages listed successfully.");
	}

	@Override
	public DataResult<List<CarDamageListDto>> getAllSorted(Direction direction) {
	
		Sort sort = Sort.by(direction, "description");

		List<CarDamage> result = this.carDamageDao.findAll(sort);

		List<CarDamageListDto> response = result.stream()
				.map(carDamage -> this.modelMapperService.forDto().map(carDamage, CarDamageListDto.class))
				.collect(Collectors.toList());

		return new SuccessDataResult<List<CarDamageListDto>>(response, "Car damages listed successfully.");
	}

}
