package com.turkcell.rentACar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.turkcell.rentACar.business.abstracts.CardInformationService;
import com.turkcell.rentACar.business.abstracts.CustomerService;
import com.turkcell.rentACar.business.constants.messages.BusinessMessages;
import com.turkcell.rentACar.business.dtos.cardInformation.CardInformationListDto;
import com.turkcell.rentACar.business.dtos.cardInformation.GetCardInformationDto;
import com.turkcell.rentACar.business.requests.cardInformation.CreateCardInformationRequest;
import com.turkcell.rentACar.business.requests.cardInformation.UpdateCardInformationRequest;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;
import com.turkcell.rentACar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentACar.core.utilities.results.SuccessResult;
import com.turkcell.rentACar.dataAccess.abstracts.CardInformationDao;
import com.turkcell.rentACar.entities.concretes.CardInformation;

@Service
public class CardInformationManager implements CardInformationService {

	private CardInformationDao cardInformationDao;
	private ModelMapperService modelMapperService;
	private CustomerService customerService;

	@Autowired
	public CardInformationManager(CardInformationDao cardInformationDao, ModelMapperService modelMapperService,
			CustomerService customerService) {
		this.cardInformationDao = cardInformationDao;
		this.modelMapperService = modelMapperService;
		this.customerService = customerService;
	}

	@Override
	public Result add(CreateCardInformationRequest createCardInformationRequest) {

		customerService.checkIfCustomerExistsById(createCardInformationRequest.getCustomerId());

		CardInformation cardInformation = this.modelMapperService.forRequest().map(createCardInformationRequest,
				CardInformation.class);

		cardInformation.setCustomer(customerService.getById(createCardInformationRequest.getCustomerId()).getData());
		cardInformation.setId(0);

		this.cardInformationDao.save(cardInformation);

		return new SuccessResult(BusinessMessages.CARD_INFORMATION_ADDED_SUCCESSFULLY);
	}

	@Override
	public Result delete(int id) {

		checkIfCardInformationExistsById(id);

		this.cardInformationDao.deleteById(id);

		return new SuccessResult(BusinessMessages.CARD_INFORMATION_DELETED_SUCCESSFULLY);
	}

	@Override
	public Result update(UpdateCardInformationRequest updateCardInformationRequest) {

		checkIfCardInformationExistsById(updateCardInformationRequest.getId());
		customerService.checkIfCustomerExistsById(updateCardInformationRequest.getCustomerId());

		CardInformation cardInformation = this.modelMapperService.forRequest().map(updateCardInformationRequest,
				CardInformation.class);

		cardInformation.setCustomer(customerService.getById(updateCardInformationRequest.getCustomerId()).getData());

		this.cardInformationDao.save(cardInformation);

		return new SuccessResult(BusinessMessages.CARD_INFORMATION_UPDATED_SUCCESSFULLY);
	}

	@Override
	public DataResult<GetCardInformationDto> getById(int id) {

		checkIfCardInformationExistsById(id);

		CardInformation cardInformation = cardInformationDao.getById(id);

		GetCardInformationDto response = this.modelMapperService.forDto().map(cardInformation,
				GetCardInformationDto.class);

		return new SuccessDataResult<>(response, BusinessMessages.CARD_INFORMATION_GET_SUCCESSFULLY);
	}

	@Override
	public DataResult<List<CardInformationListDto>> getAll() {

		List<CardInformation> result = this.cardInformationDao.findAll();

		List<CardInformationListDto> response = result.stream().map(
				cardInformation -> this.modelMapperService.forDto().map(cardInformation, CardInformationListDto.class))
				.collect(Collectors.toList());

		return new SuccessDataResult<>(response, BusinessMessages.CARD_INFORMATION_LISTED_SUCCESSFULLY);
	}

	@Override
	public DataResult<List<CardInformationListDto>> getAllPaged(int pageNo, int pageSize) {

		Pageable pageable = PageRequest.of(pageNo - 1, pageSize);

		List<CardInformation> result = this.cardInformationDao.findAll(pageable).getContent();

		List<CardInformationListDto> response = result.stream().map(
				cardInformation -> this.modelMapperService.forDto().map(cardInformation, CardInformationListDto.class))
				.collect(Collectors.toList());

		return new SuccessDataResult<>(response);
	}

	@Override
	public DataResult<List<CardInformationListDto>> getAllSorted(String direction) {

		Sort sort = Sort.by(Sort.Direction.fromString(direction), "cardNo");

		List<CardInformation> result = cardInformationDao.findAll(sort);

		List<CardInformationListDto> response = result.stream().map(
				cardInformation -> this.modelMapperService.forDto().map(cardInformation, CardInformationListDto.class))
				.collect(Collectors.toList());

		return new SuccessDataResult<>(response);
	}

	private void checkIfCardInformationExistsById(int id) {
		if (!this.cardInformationDao.existsById(id)) {
			throw new BusinessException(BusinessMessages.CARD_INFORMATION_NOT_FOUND);
		}
	}
}
