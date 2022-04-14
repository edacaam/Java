package com.turkcell.rentACar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turkcell.rentACar.business.abstracts.CityService;
import com.turkcell.rentACar.business.constants.messages.BusinessMessages;
import com.turkcell.rentACar.business.dtos.CityListDto;
import com.turkcell.rentACar.business.dtos.GetCityDto;
import com.turkcell.rentACar.business.requests.CreateCityRequest;
import com.turkcell.rentACar.business.requests.UpdateCityRequest;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;
import com.turkcell.rentACar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentACar.core.utilities.results.SuccessResult;
import com.turkcell.rentACar.dataAccess.abstracts.CityDao;
import com.turkcell.rentACar.entities.concretes.City;

@Service
public class CityManager implements CityService {

	private CityDao cityDao;
	private ModelMapperService modelMapperService;

	@Autowired
	public CityManager(CityDao cityDao, ModelMapperService modelMapperService) {
		this.cityDao = cityDao;
		this.modelMapperService = modelMapperService;
	}

	@Override
	public Result add(CreateCityRequest createCityRequest) {

		checkIfCityExistsByName(createCityRequest.getName());

		City city = this.modelMapperService.forRequest().map(createCityRequest, City.class);
		city.setId(0);

		this.cityDao.save(city);

		return new SuccessResult(BusinessMessages.CITY_ADDED_SUCCESSFULLY);
	}

	@Override
	public Result delete(int id) {

		checkIfCityExistsById(id);

		this.cityDao.deleteById(id);

		return new SuccessResult(BusinessMessages.CITY_DELETED_SUCCESSFULLY);
	}

	@Override
	public Result update(UpdateCityRequest updateCityRequest) {

		checkIfCityExistsById(updateCityRequest.getId());

		City city = this.modelMapperService.forRequest().map(updateCityRequest, City.class);

		checkIfUpdatedCityExistsByName(city);

		this.cityDao.save(city);

		return new SuccessResult(BusinessMessages.CITY_UPDATED_SUCCESSFULLY);
	}

	@Override
	public DataResult<GetCityDto> getById(int id) {

		checkIfCityExistsById(id);

		City city = cityDao.getById(id);

		GetCityDto response = this.modelMapperService.forDto().map(city, GetCityDto.class);

		return new SuccessDataResult<>(response, BusinessMessages.CITY_GET_SUCCESSFULLY);
	}

	@Override
	public DataResult<List<CityListDto>> getAll() {

		List<City> result = this.cityDao.findAll();

		List<CityListDto> response = result.stream()
				.map(city -> this.modelMapperService.forDto().map(city, CityListDto.class))
				.collect(Collectors.toList());

		return new SuccessDataResult<>(response, BusinessMessages.CITY_LISTED_SUCCESSFULLY);
	}

	@Override
	public void checkIfCityExistsById(int id) {
		if (!this.cityDao.existsById(id)) {
			throw new BusinessException(BusinessMessages.CITY_NOT_FOUND);
		}
	}

	private void checkIfCityExistsByName(String name) {
		if (this.cityDao.existsByName(name)) {
			throw new BusinessException(BusinessMessages.CITY_NAME_ALREADY_EXISTS);
		}
	}

	private void checkIfUpdatedCityExistsByName(City city) {

		City exsistsCity = this.cityDao.findByName(city.getName());

		if (exsistsCity != null && !exsistsCity.getId().equals(city.getId())) {
			throw new BusinessException(BusinessMessages.CITY_NAME_ALREADY_EXISTS);
		}
	}

}
