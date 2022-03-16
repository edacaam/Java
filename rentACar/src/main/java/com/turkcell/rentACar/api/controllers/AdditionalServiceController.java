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

import com.turkcell.rentACar.business.abstracts.AdditionalServiceService;
import com.turkcell.rentACar.business.dtos.AdditionalServiceListDto;
import com.turkcell.rentACar.business.dtos.GetAdditionalServiceDto;
import com.turkcell.rentACar.business.requests.CreateAdditionalServiceRequest;
import com.turkcell.rentACar.business.requests.UpdateAdditionalServiceRequest;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;

@RestController
@RequestMapping("/api/additionalServices")
public class AdditionalServiceController {
	private AdditionalServiceService additionalServiceService;

	public AdditionalServiceController(AdditionalServiceService additionalServiceService) {
		this.additionalServiceService = additionalServiceService;
	}

	@GetMapping("/getAll")
	public DataResult<List<AdditionalServiceListDto>> getAll() {
		return additionalServiceService.getAll();
	}

	@PostMapping("/add")
	public Result add(@RequestBody @Valid CreateAdditionalServiceRequest createAdditionalServiceRequest) {
		return additionalServiceService.add(createAdditionalServiceRequest);
	}

	@GetMapping("/getById/{id}")
	public DataResult<GetAdditionalServiceDto> getById(@RequestParam int id) {
		return additionalServiceService.getById(id);
	}

	@DeleteMapping("/delete/{id}")
	public Result delete(@RequestParam int id) {
		return additionalServiceService.delete(id);
	}

	@PutMapping("/update")
	public Result update(@RequestBody @Valid UpdateAdditionalServiceRequest updateAdditionalServiceRequest) {
		return this.additionalServiceService.update(updateAdditionalServiceRequest);
	}
	
	@GetMapping("/getAllPages")
	public DataResult<List<AdditionalServiceListDto>> getAllPaged(int pageNo, int pageSize) {
		return additionalServiceService.getAllPaged(pageNo, pageSize);
	}

	@GetMapping("/getAllSorted")
	public DataResult<List<AdditionalServiceListDto>> getAllSorted(String direction) {
		return additionalServiceService.getAllSorted(direction);
	}
}
