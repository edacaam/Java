package com.turkcell.rentACar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turkcell.rentACar.business.abstracts.IndividualCustomerService;
import com.turkcell.rentACar.business.abstracts.UserService;
import com.turkcell.rentACar.business.constants.messages.BusinessMessages;
import com.turkcell.rentACar.business.dtos.individualCustomer.GetIndividualCustomerDto;
import com.turkcell.rentACar.business.dtos.individualCustomer.IndividualCustomerListDto;
import com.turkcell.rentACar.business.requests.individualCustomer.CreateIndividualCustomerRequest;
import com.turkcell.rentACar.business.requests.individualCustomer.UpdateIndividualCustomerRequest;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;
import com.turkcell.rentACar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentACar.core.utilities.results.SuccessResult;
import com.turkcell.rentACar.dataAccess.abstracts.IndividualCustomerDao;
import com.turkcell.rentACar.entities.concretes.IndividualCustomer;

@Service
public class IndividualCustomerManager implements IndividualCustomerService {
	private IndividualCustomerDao individualCustomerDao;
	private ModelMapperService modelMapperService;
	private UserService userService;

	@Autowired
	public IndividualCustomerManager(IndividualCustomerDao individualCustomerDao, ModelMapperService modelMapperService,
			UserService userService) {
		this.individualCustomerDao = individualCustomerDao;
		this.modelMapperService = modelMapperService;
		this.userService = userService;
	}

	@Override
	public Result add(CreateIndividualCustomerRequest createIndividualCustomerRequest) {

		checkIfIndividualCustomerExistsByNationalIdentity(createIndividualCustomerRequest.getNationalIdentity());
		userService.checkIfUserAlreadyExistsByEmail(createIndividualCustomerRequest.getEmail());

		IndividualCustomer individualCustomer = this.modelMapperService.forRequest()
				.map(createIndividualCustomerRequest, IndividualCustomer.class);

		individualCustomer.setUserId(0);

		individualCustomerDao.save(individualCustomer);

		return new SuccessResult(BusinessMessages.INDIVIDUAL_CUSTOMER_ADDED_SUCCESSFULLY);

	}

	@Override
	public Result delete(int id) {

		checkIfIndividualCustomerExistsById(id);

		this.individualCustomerDao.deleteById(id);

		return new SuccessResult(BusinessMessages.INDIVIDUAL_CUSTOMER_DELETED_SUCCESSFULLY);
	}

	@Override
	public Result update(UpdateIndividualCustomerRequest updateIndividualCustomerRequest) {

		checkIfIndividualCustomerExistsById(updateIndividualCustomerRequest.getUserId());

		IndividualCustomer individualCustomer = this.modelMapperService.forRequest()
				.map(updateIndividualCustomerRequest, IndividualCustomer.class);

		checkIfUpdatedIndividualCustomerExistsByNationalIdentity(individualCustomer);
		checkIfUpdatedIndividualCustomerExistsByEmail(individualCustomer);

		individualCustomerDao.save(individualCustomer);

		return new SuccessResult(BusinessMessages.INDIVIDUAL_CUSTOMER_UPDATED_SUCCESSFULLY);
	}

	@Override
	public DataResult<GetIndividualCustomerDto> getById(int id) {

		checkIfIndividualCustomerExistsById(id);

		IndividualCustomer individualCustomer = this.individualCustomerDao.getById(id);

		GetIndividualCustomerDto response = this.modelMapperService.forDto().map(individualCustomer,
				GetIndividualCustomerDto.class);

		return new SuccessDataResult<>(response, BusinessMessages.INDIVIDUAL_CUSTOMER_GET_SUCCESSFULLY);
	}

	@Override
	public DataResult<List<IndividualCustomerListDto>> getAll() {

		List<IndividualCustomer> result = this.individualCustomerDao.findAll();

		List<IndividualCustomerListDto> response = result.stream().map(corporateCustomer -> this.modelMapperService
				.forDto().map(corporateCustomer, IndividualCustomerListDto.class)).collect(Collectors.toList());

		return new SuccessDataResult<>(response, BusinessMessages.INDIVIDUAL_CUSTOMER_LISTED_SUCCESSFULLY);
	}

	@Override
	public void checkIfIndividualCustomerExistsById(int id) {
		if (!this.individualCustomerDao.existsById(id)) {
			throw new BusinessException(BusinessMessages.INDIVIDUAL_CUSTOMER_NOT_FOUND);
		}
	}

	private void checkIfIndividualCustomerExistsByNationalIdentity(String nationalIdentity) {
		if (this.individualCustomerDao.existsByNationalIdentity(nationalIdentity)) {
			throw new BusinessException(BusinessMessages.INDIVIDUAL_CUSTOMER_NATIONAL_IDENTITY_ALREADY_EXISTS);
		}
	}

	private void checkIfUpdatedIndividualCustomerExistsByNationalIdentity(IndividualCustomer individualCustomer) {

		IndividualCustomer exsistsIndividualCustomer = this.individualCustomerDao
				.findByNationalIdentity(individualCustomer.getNationalIdentity());

		if (exsistsIndividualCustomer != null
				&& !exsistsIndividualCustomer.getUserId().equals(individualCustomer.getUserId())) {
			throw new BusinessException(BusinessMessages.INDIVIDUAL_CUSTOMER_NATIONAL_IDENTITY_ALREADY_EXISTS);
		}
	}

	private void checkIfUpdatedIndividualCustomerExistsByEmail(IndividualCustomer individualCustomer) {

		Integer existsUserId = userService.getUserByEmail(individualCustomer.getEmail()).getUserId();
		if (existsUserId != null && !existsUserId.equals(individualCustomer.getUserId())) {
			throw new BusinessException(BusinessMessages.USER_EMAIL_ALREADY_EXISTS);
		}
	}
}
