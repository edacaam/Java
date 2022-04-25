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

import com.turkcell.rentACar.api.models.rentalCar.CreateRentalCarModel;
import com.turkcell.rentACar.api.models.rentalCar.UpdateRentalCarModel;
import com.turkcell.rentACar.business.abstracts.RentalCarService;
import com.turkcell.rentACar.business.dtos.GetRentalCarDto;
import com.turkcell.rentACar.business.dtos.RentalCarListDto;
import com.turkcell.rentACar.business.requests.EndOfRentRequest;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;

@RestController
@RequestMapping("/api/rentalCars")
public class RentalCarsController {
	private RentalCarService rentalCarService;

	@Autowired
	public RentalCarsController(RentalCarService rentalCarService) {
		this.rentalCarService = rentalCarService;
	}

	@PostMapping("/addForCorporate")
	Result addForCorporate(@RequestBody @Valid CreateRentalCarModel createRentalCarModel) {
		return this.rentalCarService.addForCorporateCustomer(createRentalCarModel);
	}

	@PostMapping("/addForIndividual")
	Result addForIndividual(@RequestBody @Valid CreateRentalCarModel createRentalCarModel) {
		return this.rentalCarService.addForIndividualCustomer(createRentalCarModel);
	}

	@GetMapping("/getByRentalId/{rentalId}")
	DataResult<GetRentalCarDto> getByRentalId(@RequestParam("id") int id) {
		return this.rentalCarService.getById(id);
	}

	@GetMapping("/getAll")
	DataResult<List<RentalCarListDto>> getAll() {
		return this.rentalCarService.getAll();
	}

	@GetMapping("/getAllPaged")
	DataResult<List<RentalCarListDto>> getAllPaged(@RequestParam("pageNo") int pageNo,
			@RequestParam("pageSize") int pageSize) {
		return this.rentalCarService.getAllPaged(pageNo, pageSize);
	}

	@GetMapping("/getAllSorted")
	DataResult<List<RentalCarListDto>> getAllSorted(@RequestParam("direction") Sort.Direction direction) {
		return this.rentalCarService.getAllSorted(direction);
	}

	@PutMapping("/update")
	Result update(@RequestBody @Valid UpdateRentalCarModel updateRentalCarModel) {
		return this.rentalCarService.update(updateRentalCarModel);
	}

	@DeleteMapping("/delete")
	Result delete(int id) {
		return this.rentalCarService.delete(id);
	}

	@PutMapping("/endOfRent")
	public Result endOfRent(@RequestBody @Valid EndOfRentRequest endOfRentRequest) {
		return this.rentalCarService.endOfRent(endOfRentRequest);
	}
}
