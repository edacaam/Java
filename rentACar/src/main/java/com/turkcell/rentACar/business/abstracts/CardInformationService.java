package com.turkcell.rentACar.business.abstracts;

import java.util.List;

import com.turkcell.rentACar.business.dtos.CardInformationListDto;
import com.turkcell.rentACar.business.dtos.GetCardInformationDto;
import com.turkcell.rentACar.business.requests.CreateCardInformationRequest;
import com.turkcell.rentACar.business.requests.UpdateCardInformationRequest;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;

public interface CardInformationService {
	Result add(CreateCardInformationRequest createCardInformationRequest);

	Result delete(int id);

	Result update(UpdateCardInformationRequest updateCardInformationRequest);

	DataResult<GetCardInformationDto> getById(int id);

	DataResult<List<CardInformationListDto>> getAll();

	DataResult<List<CardInformationListDto>> getAllPaged(int pageNo, int pageSize);

	DataResult<List<CardInformationListDto>> getAllSorted(String direction);

}
