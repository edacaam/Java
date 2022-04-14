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

import com.turkcell.rentACar.business.abstracts.CityService;
import com.turkcell.rentACar.business.dtos.CityListDto;
import com.turkcell.rentACar.business.dtos.GetCityDto;
import com.turkcell.rentACar.business.requests.CreateCityRequest;
import com.turkcell.rentACar.business.requests.UpdateCityRequest;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;

@RestController
@RequestMapping("/api/cities")
public class CitiesController {

	private CityService cityService;

	public CitiesController(CityService cityService) {
		this.cityService = cityService;
	}

	@GetMapping("/getAll")
	public DataResult<List<CityListDto>> getAll() {
		return cityService.getAll();
	}

	@PostMapping("/add")
	public Result add(@RequestBody @Valid CreateCityRequest createCityRequest) {
		return this.cityService.add(createCityRequest);
	}

	@GetMapping("/getById/{id}")
	public DataResult<GetCityDto> getById(@RequestParam int id) {
		return this.cityService.getById(id);
	}

	@DeleteMapping("/delete/{id}")
	public Result delete(@RequestParam int id) {
		return this.cityService.delete(id);
	}

	@PutMapping("/update")
	public Result update(@RequestBody @Valid UpdateCityRequest updateCityRequest) {
		return this.cityService.update(updateCityRequest);
	}
}
