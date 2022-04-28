package com.turkcell.rentACar.api.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.turkcell.rentACar.business.abstracts.CardInformationService;
import com.turkcell.rentACar.business.dtos.cardInformation.CardInformationListDto;
import com.turkcell.rentACar.business.dtos.cardInformation.GetCardInformationDto;
import com.turkcell.rentACar.business.requests.cardInformation.CreateCardInformationRequest;
import com.turkcell.rentACar.business.requests.cardInformation.UpdateCardInformationRequest;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;

@RestController
@RequestMapping("/api/cardInformations")
public class CardInformationsController {

	private CardInformationService cardInformationService;

	@Autowired
	public CardInformationsController(CardInformationService cardInformationService) {
		this.cardInformationService = cardInformationService;
	}

	@PostMapping("/add")
	public Result add(@RequestBody @Valid CreateCardInformationRequest createCardInformationRequest) {
		return cardInformationService.add(createCardInformationRequest);
	}

	@DeleteMapping("/delete/{id}")
	public Result delete(@RequestParam int id) {
		return cardInformationService.delete(id);
	}

	@PutMapping("/update")
	public Result update(@RequestBody @Valid UpdateCardInformationRequest updateCardInformationRequest) {
		return this.cardInformationService.update(updateCardInformationRequest);
	}

	@GetMapping("/getById/{id}")
	public DataResult<GetCardInformationDto> getById(@RequestParam int id) {
		return cardInformationService.getById(id);
	}

	@GetMapping("/getAll")
	public DataResult<List<CardInformationListDto>> getAll() {
		return cardInformationService.getAll();
	}

	@GetMapping("/getAllPages")
	public DataResult<List<CardInformationListDto>> getAllPaged(int pageNo, int pageSize) {
		return cardInformationService.getAllPaged(pageNo, pageSize);
	}

	@GetMapping("/getAllSorted")
	public DataResult<List<CardInformationListDto>> getAllSorted(String direction) {
		return cardInformationService.getAllSorted(direction);
	}
}
