package com.turkcell.rentACar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turkcell.rentACar.business.abstracts.CorporateCustomerService;
import com.turkcell.rentACar.business.abstracts.UserService;
import com.turkcell.rentACar.business.constants.messages.BusinessMessages;
import com.turkcell.rentACar.business.dtos.corporateCustomer.CorporateCustomerListDto;
import com.turkcell.rentACar.business.dtos.corporateCustomer.GetCorporateCustomerDto;
import com.turkcell.rentACar.business.requests.corporateCustomer.CreateCorporateCustomerRequest;
import com.turkcell.rentACar.business.requests.corporateCustomer.UpdateCorporateCustomerRequest;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;
import com.turkcell.rentACar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentACar.core.utilities.results.SuccessResult;
import com.turkcell.rentACar.dataAccess.abstracts.CorporateCustomerDao;
import com.turkcell.rentACar.entities.concretes.CorporateCustomer;

@Service
public class CorporateCustomerManager implements CorporateCustomerService {

	private CorporateCustomerDao corporateCustomerDao;
	private ModelMapperService modelMapperService;
	private UserService userService;

	@Autowired
	public CorporateCustomerManager(CorporateCustomerDao corporateCustomerDao, ModelMapperService modelMapperService,
			UserService userService) {
		this.corporateCustomerDao = corporateCustomerDao;
		this.modelMapperService = modelMapperService;
		this.userService = userService;
	}

	@Override
	public Result add(CreateCorporateCustomerRequest createCorporateCustomerRequest) {

		checkIfCorporateCustomerExistsByTaxNumber(createCorporateCustomerRequest.getTaxNumber());
		userService.checkIfUserAlreadyExistsByEmail(createCorporateCustomerRequest.getEmail());

		CorporateCustomer corporateCustomer = this.modelMapperService.forRequest().map(createCorporateCustomerRequest,
				CorporateCustomer.class);

		corporateCustomer.setUserId(0);

		corporateCustomerDao.save(corporateCustomer);

		return new SuccessResult(BusinessMessages.CORPORATE_CUSTOMER_ADDED_SUCCESSFULLY);
	}

	@Override
	public Result delete(int id) {

		checkIfCorporateCustomerExistsById(id);

		this.corporateCustomerDao.deleteById(id);

		return new SuccessResult(BusinessMessages.CORPORATE_CUSTOMER_DELETED_SUCCESSFULLY);
	}

	@Override
	public Result update(UpdateCorporateCustomerRequest updateCorporateCustomerRequest) {

		checkIfCorporateCustomerExistsById(updateCorporateCustomerRequest.getUserId());

		CorporateCustomer corporateCustomer = this.modelMapperService.forRequest().map(updateCorporateCustomerRequest,
				CorporateCustomer.class);

		checkIfUpdatedCorporateCustomerExistsByTaxNumber(corporateCustomer);
		checkIfUpdatedCorporateCustomerExistsByEmail(corporateCustomer);

		corporateCustomerDao.save(corporateCustomer);

		return new SuccessResult(BusinessMessages.CORPORATE_CUSTOMER_UPDATED_SUCCESSFULLY);
	}

	@Override
	public DataResult<GetCorporateCustomerDto> getById(int id) {

		checkIfCorporateCustomerExistsById(id);

		CorporateCustomer corporateCustomer = this.corporateCustomerDao.getById(id);

		GetCorporateCustomerDto response = this.modelMapperService.forDto().map(corporateCustomer,
				GetCorporateCustomerDto.class);

		return new SuccessDataResult<>(response, BusinessMessages.CORPORATE_CUSTOMER_GET_SUCCESSFULLY);
	}

	@Override
	public DataResult<List<CorporateCustomerListDto>> getAll() {

		List<CorporateCustomer> result = this.corporateCustomerDao.findAll();

		List<CorporateCustomerListDto> response = result.stream().map(corporateCustomer -> this.modelMapperService
				.forDto().map(corporateCustomer, CorporateCustomerListDto.class)).collect(Collectors.toList());

		return new SuccessDataResult<>(response, BusinessMessages.CORPORATE_CUSTOMER_LISTED_SUCCESSFULLY);
	}

	@Override
	public void checkIfCorporateCustomerExistsById(int id) {
		if (!this.corporateCustomerDao.existsById(id)) {
			throw new BusinessException(BusinessMessages.CORPORATE_CUSTOMER_NOT_FOUND);
		}
	}

	private void checkIfCorporateCustomerExistsByTaxNumber(String taxNumber) {
		if (this.corporateCustomerDao.existsByTaxNumber(taxNumber)) {
			throw new BusinessException(BusinessMessages.CORPORATE_CUSTOMER_TAX_NUMBER_ALREADY_EXISTS);
		}
	}

	private void checkIfUpdatedCorporateCustomerExistsByTaxNumber(CorporateCustomer corporateCustomer) {

		CorporateCustomer exsistsCorporateCustomer = this.corporateCustomerDao
				.findByTaxNumber(corporateCustomer.getTaxNumber());

		if (exsistsCorporateCustomer != null && !exsistsCorporateCustomer.getUserId().equals(corporateCustomer.getUserId())) {
			throw new BusinessException(BusinessMessages.CORPORATE_CUSTOMER_TAX_NUMBER_ALREADY_EXISTS);
		}
	}

	private void checkIfUpdatedCorporateCustomerExistsByEmail(CorporateCustomer corporateCustomer) {

		Integer existsUserId = userService.getUserByEmail(corporateCustomer.getEmail()).getUserId();
		if (existsUserId != null && !existsUserId.equals(corporateCustomer.getUserId())) {
			throw new BusinessException(BusinessMessages.USER_EMAIL_ALREADY_EXISTS);
		}
	}
}
