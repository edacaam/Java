package com.turkcell.rentACar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.turkcell.rentACar.business.abstracts.BrandService;
import com.turkcell.rentACar.business.abstracts.CarService;
import com.turkcell.rentACar.business.abstracts.ColorService;
import com.turkcell.rentACar.business.constants.messages.BusinessMessages;
import com.turkcell.rentACar.business.dtos.car.CarListDto;
import com.turkcell.rentACar.business.dtos.car.GetCarDto;
import com.turkcell.rentACar.business.requests.car.CreateCarRequest;
import com.turkcell.rentACar.business.requests.car.UpdateCarRequest;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;
import com.turkcell.rentACar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentACar.core.utilities.results.SuccessResult;
import com.turkcell.rentACar.dataAccess.abstracts.CarDao;
import com.turkcell.rentACar.entities.concretes.Car;

@Service
public class CarManager implements CarService {

	private CarDao carDao;
	private ModelMapperService modelMapperService;
	private BrandService brandService;
	private ColorService colorService;

	@Autowired
	public CarManager(CarDao carDao, ModelMapperService modelMapperService, BrandService brandService,
			ColorService colorService) {
		this.carDao = carDao;
		this.modelMapperService = modelMapperService;
		this.brandService = brandService;
		this.colorService = colorService;
	}

	@Override
	public Result add(CreateCarRequest createCarRequest) {

		colorService.checkIfColorExistsById(createCarRequest.getColorId());
		brandService.checkIfBrandExistsById(createCarRequest.getBrandId());

		Car car = this.modelMapperService.forRequest().map(createCarRequest, Car.class);

		car.setId(0);

		carDao.save(car);

		return new SuccessResult(BusinessMessages.CAR_ADDED_SUCCESSFULLY);
	}

	@Override
	public Result delete(int id) {

		checkIfCarExistsById(id);

		this.carDao.deleteById(id);

		return new SuccessResult(BusinessMessages.CAR_DELETED_SUCCESSFULLY);
	}

	@Override
	public Result update(UpdateCarRequest updateCarRequest) {

		colorService.checkIfColorExistsById(updateCarRequest.getColorId());
		brandService.checkIfBrandExistsById(updateCarRequest.getBrandId());
		checkIfCarExistsById(updateCarRequest.getId());

		Car car = this.modelMapperService.forRequest().map(updateCarRequest, Car.class);

		carDao.save(car);

		return new SuccessResult(BusinessMessages.CAR_UPDATED_SUCCESSFULLY);
	}

	@Override
	public DataResult<GetCarDto> getById(int id) {

		checkIfCarExistsById(id);

		Car car = this.carDao.getById(id);

		GetCarDto response = this.modelMapperService.forDto().map(car, GetCarDto.class);

		return new SuccessDataResult<>(response, BusinessMessages.CAR_GET_SUCCESSFULLY);
	}

	@Override
	public DataResult<List<CarListDto>> getByDailyPrice(double dailyPrice) {

		List<Car> result = this.carDao.findByDailyPriceLessThanEqual(dailyPrice);

		List<CarListDto> response = result.stream()
				.map(car -> this.modelMapperService.forDto().map(car, CarListDto.class)).collect(Collectors.toList());

		return new SuccessDataResult<>(response, BusinessMessages.CAR_LISTED_SUCCESSFULLY);
	}

	@Override
	public DataResult<List<CarListDto>> getAll() {

		List<Car> result = this.carDao.findAll();

		List<CarListDto> response = result.stream()
				.map(car -> this.modelMapperService.forDto().map(car, CarListDto.class)).collect(Collectors.toList());

		return new SuccessDataResult<>(response, BusinessMessages.CAR_LISTED_SUCCESSFULLY);
	}

	@Override
	public DataResult<List<CarListDto>> getAllPaged(int pageNo, int pageSize) {

		Pageable pageable = PageRequest.of(pageNo - 1, pageSize);

		List<Car> result = this.carDao.findAll(pageable).getContent();

		List<CarListDto> response = result.stream()
				.map(car -> this.modelMapperService.forDto().map(car, CarListDto.class)).collect(Collectors.toList());

		return new SuccessDataResult<>(response);
	}

	@Override
	public DataResult<List<CarListDto>> getAllSorted(String direction) {

		Sort sort = Sort.by(Sort.Direction.fromString(direction), "dailyPrice");

		List<Car> result = carDao.findAll(sort);

		List<CarListDto> response = result.stream()
				.map(car -> this.modelMapperService.forDto().map(car, CarListDto.class)).collect(Collectors.toList());

		return new SuccessDataResult<>(response);
	}

	@Override
	public Result updateKilometer(int id, double kilometer) {
		checkIfCarExistsById(id);

		Car car = carDao.getById(id);

		car.setKilometer(kilometer);

		carDao.save(car);

		return new SuccessResult(BusinessMessages.CAR_KILOMETER_UPDATED_SUCCESSFULLY);
	}

	@Override
	public void checkIfCarExistsById(int id) {
		if (!this.carDao.existsById(id)) {
			throw new BusinessException(BusinessMessages.CAR_NOT_FOUND);
		}
	}
}
