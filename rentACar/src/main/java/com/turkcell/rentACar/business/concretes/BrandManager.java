package com.turkcell.rentACar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turkcell.rentACar.business.abstracts.BrandService;
import com.turkcell.rentACar.business.constants.messages.BusinessMessages;
import com.turkcell.rentACar.business.dtos.brand.BrandListDto;
import com.turkcell.rentACar.business.dtos.brand.GetBrandDto;
import com.turkcell.rentACar.business.requests.brand.CreateBrandRequest;
import com.turkcell.rentACar.business.requests.brand.UpdateBrandRequest;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;
import com.turkcell.rentACar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentACar.core.utilities.results.SuccessResult;
import com.turkcell.rentACar.dataAccess.abstracts.BrandDao;
import com.turkcell.rentACar.entities.concretes.Brand;

@Service
public class BrandManager implements BrandService {

	private BrandDao brandDao;
	private ModelMapperService modelMapperService;

	@Autowired
	public BrandManager(BrandDao brandDao, ModelMapperService modelMapperService) {
		this.brandDao = brandDao;
		this.modelMapperService = modelMapperService;
	}

	@Override
	public DataResult<List<BrandListDto>> getAll() {

		List<Brand> result = this.brandDao.findAll();

		List<BrandListDto> response = result.stream()
				.map(brand -> this.modelMapperService.forDto().map(brand, BrandListDto.class))
				.collect(Collectors.toList());

		return new SuccessDataResult<>(response, BusinessMessages.BRAND_LISTED_SUCCESSFULLY);
	}

	@Override
	public Result add(CreateBrandRequest createBrandRequest) {

		checkIfBrandExistsByName(createBrandRequest.getName());

		Brand brand = this.modelMapperService.forRequest().map(createBrandRequest, Brand.class);

		brand.setId(0);

		this.brandDao.save(brand);

		return new SuccessResult(BusinessMessages.BRAND_ADDED_SUCCESSFULLY);

	}

	@Override
	public DataResult<GetBrandDto> getById(int id) {

		checkIfBrandExistsById(id);

		Brand brand = this.brandDao.getById(id);

		GetBrandDto response = this.modelMapperService.forDto().map(brand, GetBrandDto.class);

		return new SuccessDataResult<>(response, BusinessMessages.BRAND_GET_SUCCESSFULLY);
	}

	@Override
	public Result delete(int id) {

		checkIfBrandExistsById(id);

		this.brandDao.deleteById(id);

		return new SuccessResult(BusinessMessages.BRAND_DELETED_SUCCESSFULLY);
	}

	@Override
	public Result update(UpdateBrandRequest updateBrandRequest) {

		checkIfBrandExistsById(updateBrandRequest.getId());

		Brand brand = this.modelMapperService.forRequest().map(updateBrandRequest, Brand.class);

		checkIfUpdatedBrandExistsByName(brand);

		this.brandDao.save(brand);

		return new SuccessResult(BusinessMessages.BRAND_UPDATED_SUCCESSFULLY);
	}

	@Override
	public void checkIfBrandExistsById(int id) {
		if (!this.brandDao.existsById(id)) {
			throw new BusinessException(BusinessMessages.BRAND_NOT_FOUND);
		}
	}

	private void checkIfBrandExistsByName(String name) {
		if (this.brandDao.existsByBrandName(name)) {
			throw new BusinessException(BusinessMessages.BRAND_NAME_ALREADY_EXISTS);
		}
	}

	private void checkIfUpdatedBrandExistsByName(Brand brand) {

		Brand exsistsBrand = this.brandDao.findByBrandName(brand.getBrandName());

		if (exsistsBrand != null && !exsistsBrand.getId().equals(brand.getId())) {
			throw new BusinessException(BusinessMessages.BRAND_NAME_ALREADY_EXISTS);
		}
	}

}
