package com.turkcell.rentACar.api.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.turkcell.rentACar.business.abstracts.CarDamageService;
import com.turkcell.rentACar.business.dtos.carDamage.CarDamageListDto;
import com.turkcell.rentACar.business.dtos.carDamage.GetCarDamageDto;
import com.turkcell.rentACar.business.requests.carDamage.CreateCarDamageRequest;
import com.turkcell.rentACar.business.requests.carDamage.UpdateCarDamageRequest;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;

@RestController
@RequestMapping("/api/carDamages")
public class CarDamagesController {
	private CarDamageService carDamageService;

	@Autowired
	public CarDamagesController(CarDamageService carDamageService) {
		this.carDamageService = carDamageService;
	}

	@PostMapping("/add")
	public Result add(@RequestBody @Valid CreateCarDamageRequest createCarDamageRequest) {
		return carDamageService.add(createCarDamageRequest);
	}

	@DeleteMapping("/delete/{id}")
	public Result delete(@RequestParam int id) {
		return carDamageService.delete(id);
	}

	@PutMapping("/update")
	public Result update(@RequestBody @Valid UpdateCarDamageRequest updateCarDamageRequest) {
		return this.carDamageService.update(updateCarDamageRequest);
	}

	@GetMapping("/getAll")
	public DataResult<List<CarDamageListDto>> getAll() {
		return carDamageService.getAll();
	}

	@GetMapping("/getById/{id}")
	public DataResult<GetCarDamageDto> getById(@RequestParam int id) {
		return carDamageService.getById(id);
	}

	@GetMapping("/getAllPaged")
	DataResult<List<CarDamageListDto>> getAllPaged(@RequestParam("pageNo") int pageNo,
			@RequestParam("pageSize") int pageSize) {
		return this.carDamageService.getAllPaged(pageNo, pageSize);
	}

	@GetMapping("/getAllSorted")
	DataResult<List<CarDamageListDto>> getAllSorted(@RequestParam("direction") Sort.Direction direction) {
		return this.carDamageService.getAllSorted(direction);
	}
}
