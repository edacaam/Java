package com.turkcell.rentACar.api.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.turkcell.rentACar.business.abstracts.CarService;
import com.turkcell.rentACar.business.dtos.car.CarListDto;
import com.turkcell.rentACar.business.dtos.car.GetCarDto;
import com.turkcell.rentACar.business.requests.car.CreateCarRequest;
import com.turkcell.rentACar.business.requests.car.UpdateCarRequest;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;

@RestController
@RequestMapping("/api/cars")
public class CarsController {

	private CarService carService;

	public CarsController(CarService carService) {
		this.carService = carService;
	}

	@GetMapping("/getAll")
	public DataResult<List<CarListDto>> getAll() {
		return carService.getAll();
	}

	@PostMapping("/add")
	public Result add(@RequestBody @Valid CreateCarRequest createCarRequest) {
		return carService.add(createCarRequest);
	}

	@GetMapping("/getById/{id}")
	public DataResult<GetCarDto> getById(@RequestParam int id) {
		return carService.getById(id);
	}

	@DeleteMapping("/delete/{id}")
	public Result delete(@RequestParam int id) {
		return carService.delete(id);
	}

	@PutMapping("/update")
	public Result update(@RequestBody @Valid UpdateCarRequest updateCarRequest) {
		return this.carService.update(updateCarRequest);
	}

	@GetMapping("/getByDailyPrice/{dailyPrice}")
	public DataResult<List<CarListDto>> getByDailyPrice(@RequestParam double dailyPrice) {
		return this.carService.getByDailyPrice(dailyPrice);
	}
	
	@GetMapping("/getAllPages")
	public DataResult<List<CarListDto>> getAllPaged(int pageNo, int pageSize) {
		return carService.getAllPaged(pageNo, pageSize);
	}

	@GetMapping("/getAllSorted")
	public DataResult<List<CarListDto>> getAllSorted(String direction) {
		return carService.getAllSorted(direction);
	}
}
