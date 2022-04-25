package com.turkcell.rentACar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.turkcell.rentACar.business.abstracts.AdditionalServiceService;
import com.turkcell.rentACar.business.constants.messages.BusinessMessages;
import com.turkcell.rentACar.business.dtos.AdditionalServiceListDto;
import com.turkcell.rentACar.business.dtos.GetAdditionalServiceDto;
import com.turkcell.rentACar.business.requests.CreateAdditionalServiceRequest;
import com.turkcell.rentACar.business.requests.UpdateAdditionalServiceRequest;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;
import com.turkcell.rentACar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentACar.core.utilities.results.SuccessResult;
import com.turkcell.rentACar.dataAccess.abstracts.AdditionalServiceDao;
import com.turkcell.rentACar.entities.concretes.AdditionalService;

@Service
public class AdditionalServiceManager implements AdditionalServiceService {

	private AdditionalServiceDao additionalServiceDao;
	private ModelMapperService modelMapperService;

	@Autowired
	public AdditionalServiceManager(AdditionalServiceDao additionalServiceDao, ModelMapperService modelMapperService) {
		this.additionalServiceDao = additionalServiceDao;
		this.modelMapperService = modelMapperService;
	}

	@Override
	public Result add(CreateAdditionalServiceRequest createAdditionalServiceRequest) {

		checkIfAdditionalServiceExistsByName(createAdditionalServiceRequest.getName());

		AdditionalService additionalService = this.modelMapperService.forRequest().map(createAdditionalServiceRequest,
				AdditionalService.class);

		additionalService.setId(0);

		additionalServiceDao.save(additionalService);

		return new SuccessResult(BusinessMessages.ADDITIONAL_SERVICE_ADDED_SUCCESSFULLY);
	}

	@Override
	public Result delete(int id) {

		checkIfAdditionalServiceExistsById(id);

		this.additionalServiceDao.deleteById(id);

		return new SuccessResult(BusinessMessages.ADDITIONAL_SERVICE_DELETED_SUCCESSFULLY);
	}

	@Override
	public Result update(UpdateAdditionalServiceRequest updateAdditionalServiceRequest) {

		checkIfAdditionalServiceExistsById(updateAdditionalServiceRequest.getId());
		checkIfAdditionalServiceAlreadyExists(updateAdditionalServiceRequest.getName(),
				updateAdditionalServiceRequest.getDailyPrice());

		AdditionalService additionalService = this.modelMapperService.forRequest().map(updateAdditionalServiceRequest,
				AdditionalService.class);

		checkIfUpdatedAdditionalServiceExistsByName(additionalService);

		additionalServiceDao.save(additionalService);

		return new SuccessResult(BusinessMessages.ADDITIONAL_SERVICE_UPDATED_SUCCESSFULLY);
	}

	@Override
	public DataResult<GetAdditionalServiceDto> getById(int id) {

		checkIfAdditionalServiceExistsById(id);

		AdditionalService additionalService = this.additionalServiceDao.getById(id);

		GetAdditionalServiceDto response = this.modelMapperService.forDto().map(additionalService,
				GetAdditionalServiceDto.class);

		return new SuccessDataResult<>(response, BusinessMessages.ADDITIONAL_SERVICE_GET_SUCCESSFULLY);
	}

	@Override
	public DataResult<List<AdditionalServiceListDto>> getAll() {

		List<AdditionalService> result = this.additionalServiceDao.findAll();

		List<AdditionalServiceListDto> response = result.stream().map(additionalService -> this.modelMapperService
				.forDto().map(additionalService, AdditionalServiceListDto.class)).collect(Collectors.toList());

		return new SuccessDataResult<>(response, BusinessMessages.ADDITIONAL_SERVICE_LISTED_SUCCESSFULLY);
	}

	@Override
	public DataResult<List<AdditionalServiceListDto>> getAllPaged(int pageNo, int pageSize) {

		Pageable pageable = PageRequest.of(pageNo - 1, pageSize);

		List<AdditionalService> result = this.additionalServiceDao.findAll(pageable).getContent();

		List<AdditionalServiceListDto> response = result.stream().map(additionalService -> this.modelMapperService
				.forDto().map(additionalService, AdditionalServiceListDto.class)).collect(Collectors.toList());

		return new SuccessDataResult<>(response);
	}

	@Override
	public DataResult<List<AdditionalServiceListDto>> getAllSorted(String direction) {

		Sort sort = Sort.by(Sort.Direction.fromString(direction), "dailyPrice");

		List<AdditionalService> result = additionalServiceDao.findAll(sort);

		List<AdditionalServiceListDto> response = result.stream().map(additionalService -> this.modelMapperService
				.forDto().map(additionalService, AdditionalServiceListDto.class)).collect(Collectors.toList());

		return new SuccessDataResult<>(response);
	}

	private void checkIfAdditionalServiceAlreadyExists(String name, double dailyPrice) {
		if (this.additionalServiceDao.existsByNameAndDailyPrice(name, dailyPrice)) {
			throw new BusinessException(BusinessMessages.ADDITIONAL_SERVICE_ALREADY_EXISTS);
		}
	}

	@Override
	public void checkIfAdditionalServiceExistsById(int id) {
		if (!this.additionalServiceDao.existsById(id)) {
			throw new BusinessException(BusinessMessages.ADDITIONAL_SERVICE_NOT_FOUND);
		}
	}

	private void checkIfAdditionalServiceExistsByName(String name) {
		if (this.additionalServiceDao.existsByName(name)) {
			throw new BusinessException(BusinessMessages.ADDITIONAL_SERVICE_NAME_ALREADY_EXISTS);
		}
	}

	private void checkIfUpdatedAdditionalServiceExistsByName(AdditionalService additionalService) {
		AdditionalService existsAdditionalService = this.additionalServiceDao.findByName(additionalService.getName());
		if (existsAdditionalService != null && !existsAdditionalService.getId().equals(additionalService.getId())) {
			throw new BusinessException(BusinessMessages.ADDITIONAL_SERVICE_NAME_ALREADY_EXISTS);
		}
	}
}
