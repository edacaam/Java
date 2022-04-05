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
import com.turkcell.rentACar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;
import com.turkcell.rentACar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentACar.core.utilities.results.SuccessResult;
import com.turkcell.rentACar.dataAccess.abstracts.CityDao;
import com.turkcell.rentACar.entities.concretes.City;
import com.turkcell.rentACar.exceptions.concretes.BusinessException;

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
		City city = this.modelMapperService.forRequest().map(createCityRequest, City.class);
		checkIfCityIsExists(createCityRequest.getName());
		this.cityDao.save(city);
		return new SuccessResult(BusinessMessages.CITY_ADDED_SUCCESSFULLY);
	}

	@Override
	public Result delete(int id) {
		this.cityDao.deleteById(id);
		return new SuccessResult(BusinessMessages.CITY_DELETED_SUCCESSFULLY);
	}

	@Override
	public Result update(UpdateCityRequest updateCityRequest) {
		City city = this.modelMapperService.forRequest().map(updateCityRequest, City.class);
		checkIfCityNameIsExists(city);
		this.cityDao.save(city);
		return new SuccessResult(BusinessMessages.CITY_UPDATED_SUCCESSFULLY);
	}

	@Override
	public DataResult<GetCityDto> getById(int id) {
		City city = cityDao.findById(id);
		GetCityDto response = this.modelMapperService.forDto().map(city, GetCityDto.class);
		return new SuccessDataResult<GetCityDto>(response, BusinessMessages.CITY_GET_SUCCESSFULLY);
	}

	@Override
	public DataResult<List<CityListDto>> getAll() {
		List<City> result = this.cityDao.findAll();
		List<CityListDto> response = result.stream()
				.map(city -> this.modelMapperService.forDto().map(city, CityListDto.class))
				.collect(Collectors.toList());
		return new SuccessDataResult<List<CityListDto>>(response, BusinessMessages.CITY_LISTED_SUCCESSFULLY);
	}

	private void checkIfCityIsExists(String name) {
		if (this.cityDao.existsByName(name)) {
			throw new BusinessException("Aynı isimde şehir eklenemez");
		}
	}

	private void checkIfCityNameIsExists(City city) {

		City ifExsistsCity = this.cityDao.findByName(city.getName());

		if (ifExsistsCity != null && ifExsistsCity.getCityId() != city.getCityId()) {
			throw new BusinessException("Aynı isimde şehir zaten mevcut");
		}
	}

}
