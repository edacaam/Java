package com.turkcell.rentACar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turkcell.rentACar.business.abstracts.ColorService;
import com.turkcell.rentACar.business.constants.messages.BusinessMessages;
import com.turkcell.rentACar.business.dtos.ColorListDto;
import com.turkcell.rentACar.business.dtos.GetColorDto;
import com.turkcell.rentACar.business.requests.CreateColorRequest;
import com.turkcell.rentACar.business.requests.UpdateColorRequest;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;
import com.turkcell.rentACar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentACar.core.utilities.results.SuccessResult;
import com.turkcell.rentACar.dataAccess.abstracts.ColorDao;
import com.turkcell.rentACar.entities.concretes.Color;

@Service
public class ColorManager implements ColorService {

	private ColorDao colorDao;
	private ModelMapperService modelMapperService;

	@Autowired
	public ColorManager(ColorDao colorDao, ModelMapperService modelMapperService) {
		this.colorDao = colorDao;
		this.modelMapperService = modelMapperService;
	}

	@Override
	public Result add(CreateColorRequest createColorRequest) {

		checkIfColorExistsByName(createColorRequest.getName());

		Color color = this.modelMapperService.forRequest().map(createColorRequest, Color.class);

		this.colorDao.save(color);

		return new SuccessResult(BusinessMessages.COLOR_ADDED_SUCCESSFULLY);
	}

	@Override
	public Result delete(int id) {

		checkIfColorExistsById(id);

		this.colorDao.deleteById(id);

		return new SuccessResult(BusinessMessages.COLOR_DELETED_SUCCESSFULLY);

	}

	@Override
	public Result update(UpdateColorRequest updateColorRequest) {

		checkIfColorExistsById(updateColorRequest.getId());

		Color color = this.modelMapperService.forRequest().map(updateColorRequest, Color.class);

		checkIfUpdatedColorExistsByName(color);

		this.colorDao.save(color);

		return new SuccessResult(BusinessMessages.COLOR_UPDATED_SUCCESSFULLY);

	}

	@Override
	public DataResult<GetColorDto> getById(int id) {

		checkIfColorExistsById(id);

		Color color = colorDao.getById(id);

		GetColorDto response = this.modelMapperService.forDto().map(color, GetColorDto.class);

		return new SuccessDataResult<>(response, BusinessMessages.COLOR_GET_SUCCESSFULLY);
	}

	@Override
	public DataResult<List<ColorListDto>> getAll() {

		List<Color> result = this.colorDao.findAll();

		List<ColorListDto> response = result.stream()
				.map(color -> this.modelMapperService.forDto().map(color, ColorListDto.class))
				.collect(Collectors.toList());

		return new SuccessDataResult<>(response, BusinessMessages.COLOR_LISTED_SUCCESSFULLY);
	}

	@Override
	public void checkIfColorExistsById(int id) {
		if (!this.colorDao.existsById(id)) {
			throw new BusinessException(BusinessMessages.COLOR_NOT_FOUND);
		}
	}

	private void checkIfColorExistsByName(String name) {
		if (this.colorDao.existsByColorName(name)) {
			throw new BusinessException(BusinessMessages.COLOR_NAME_ALREADY_EXISTS);
		}
	}

	private void checkIfUpdatedColorExistsByName(Color color) {

		Color exsistsColor = this.colorDao.findByColorName(color.getColorName());

		if (exsistsColor != null && !exsistsColor.getId().equals(color.getId())) {
			throw new BusinessException(BusinessMessages.COLOR_NAME_ALREADY_EXISTS);
		}
	}
}
