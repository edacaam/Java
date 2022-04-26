package com.turkcell.rentACar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.turkcell.rentACar.api.models.rentalCar.CreateRentalCarModel;
import com.turkcell.rentACar.api.models.rentalCar.UpdateRentalCarModel;
import com.turkcell.rentACar.business.abstracts.AdditionalServiceService;
import com.turkcell.rentACar.business.abstracts.CarMaintenanceService;
import com.turkcell.rentACar.business.abstracts.CarService;
import com.turkcell.rentACar.business.abstracts.CityService;
import com.turkcell.rentACar.business.abstracts.CorporateCustomerService;
import com.turkcell.rentACar.business.abstracts.IndividualCustomerService;
import com.turkcell.rentACar.business.abstracts.OrderedAdditionalServiceService;
import com.turkcell.rentACar.business.abstracts.RentalCarService;
import com.turkcell.rentACar.business.constants.messages.BusinessMessages;
import com.turkcell.rentACar.business.dtos.CarMaintenanceListDto;
import com.turkcell.rentACar.business.dtos.GetRentalCarDto;
import com.turkcell.rentACar.business.dtos.RentalCarListDto;
import com.turkcell.rentACar.business.requests.CreateOrderedAdditionalServiceRequest;
import com.turkcell.rentACar.business.requests.CreateRentalCarRequest;
import com.turkcell.rentACar.business.requests.EndOfRentRequest;
import com.turkcell.rentACar.business.requests.UpdateRentalCarRequest;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.ErrorDataResult;
import com.turkcell.rentACar.core.utilities.results.Result;
import com.turkcell.rentACar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentACar.core.utilities.results.SuccessResult;
import com.turkcell.rentACar.dataAccess.abstracts.RentalCarDao;
import com.turkcell.rentACar.entities.concretes.OrderedAdditionalService;
import com.turkcell.rentACar.entities.concretes.RentalCar;

@Service
public class RentalCarManager implements RentalCarService {

	private RentalCarDao rentalCarDao;
	private ModelMapperService modelMapperService;
	private CarMaintenanceService carMaintenanceService;
	private CarService carService;
	private OrderedAdditionalServiceService orderedAdditionalServiceService;
	private CityService cityService;
	private AdditionalServiceService additionalServiceService;
	private IndividualCustomerService individualCustomerService;
	private CorporateCustomerService corporateCustomerService;

	@Autowired
	public RentalCarManager(RentalCarDao rentalCarDao, ModelMapperService modelMapperService,
			CarMaintenanceService carMaintenanceService, CarService carService,
			OrderedAdditionalServiceService orderedAdditionalServiceService, CityService cityService,
			IndividualCustomerService individualCustomerService, CorporateCustomerService corporateCustomerService,
			AdditionalServiceService additionalServiceService) {
		this.rentalCarDao = rentalCarDao;
		this.modelMapperService = modelMapperService;
		this.carMaintenanceService = carMaintenanceService;
		this.carService = carService;
		this.orderedAdditionalServiceService = orderedAdditionalServiceService;
		this.cityService = cityService;
		this.individualCustomerService = individualCustomerService;
		this.corporateCustomerService = corporateCustomerService;
		this.additionalServiceService = additionalServiceService;
	}

	@Override
	public Result addForCorporateCustomer(CreateRentalCarModel createRentalCarModel) {
		carService.checkIfCarExistsById(createRentalCarModel.getCreateRentalCarRequest().getCarId());
		cityService.checkIfCityExistsById(createRentalCarModel.getCreateRentalCarRequest().getCityOfDeliveryId());
		cityService.checkIfCityExistsById(createRentalCarModel.getCreateRentalCarRequest().getCityOfPickUpId());
		corporateCustomerService.checkIfCorporateCustomerExistsById(
				createRentalCarModel.getCreateRentalCarRequest().getCustomerUserId());

		RentalCar rentalCar = this.modelMapperService.forRequest().map(createRentalCarModel.getCreateRentalCarRequest(),
				RentalCar.class);

		checkIfRentalDatesIsValid(rentalCar);
		checkIfCarAlreadyRented(createRentalCarModel.getCreateRentalCarRequest());
		checkIfCarUnderMaintenance(rentalCar);

		rentalCar.setOrderedAdditionalServices(orderedAdditionalServiceMapper(
				createRentalCarModel.getCreateOrderedAdditionalServiceRequest(), rentalCar));
		rentalCar.setStartingKilometer(carService.getById(rentalCar.getCar().getId()).getData().getKilometer());
		rentalCar.setId(0);

		this.rentalCarDao.save(rentalCar);
		return new SuccessResult(BusinessMessages.RENTAL_CAR_ADDED_SUCCESSFULLY);
	}

	@Override
	public Result addForIndividualCustomer(CreateRentalCarModel createRentalCarModel) {
		carService.checkIfCarExistsById(createRentalCarModel.getCreateRentalCarRequest().getCarId());
		cityService.checkIfCityExistsById(createRentalCarModel.getCreateRentalCarRequest().getCityOfDeliveryId());
		cityService.checkIfCityExistsById(createRentalCarModel.getCreateRentalCarRequest().getCityOfPickUpId());
		individualCustomerService.checkIfIndividualCustomerExistsById(
				createRentalCarModel.getCreateRentalCarRequest().getCustomerUserId());
		checkIfCarAlreadyRented(createRentalCarModel.getCreateRentalCarRequest());

		RentalCar rentalCar = this.modelMapperService.forRequest().map(createRentalCarModel.getCreateRentalCarRequest(),
				RentalCar.class);

		checkIfRentalDatesIsValid(rentalCar);
		checkIfCarUnderMaintenance(rentalCar);

		rentalCar.setOrderedAdditionalServices(orderedAdditionalServiceMapper(
				createRentalCarModel.getCreateOrderedAdditionalServiceRequest(), rentalCar));
		rentalCar.setStartingKilometer(carService.getById(rentalCar.getCar().getId()).getData().getKilometer());
		rentalCar.setId(0);

		this.rentalCarDao.save(rentalCar);
		return new SuccessResult(BusinessMessages.RENTAL_CAR_ADDED_SUCCESSFULLY);
	}

	@Override
	public Result update(UpdateRentalCarModel updateRentalCarModel) {

		checkIfRentalCarIsExistsById(updateRentalCarModel.getUpdateRentalCarRequest().getId());
		carService.checkIfCarExistsById(updateRentalCarModel.getUpdateRentalCarRequest().getCarId());
		cityService.checkIfCityExistsById(updateRentalCarModel.getUpdateRentalCarRequest().getCityOfDeliveryId());
		cityService.checkIfCityExistsById(updateRentalCarModel.getUpdateRentalCarRequest().getCityOfPickUpId());
		checkIfUpdatedCarAlreadyRented(updateRentalCarModel.getUpdateRentalCarRequest());

		RentalCar rentalCarUpdate = this.modelMapperService.forRequest()
				.map(updateRentalCarModel.getUpdateRentalCarRequest(), RentalCar.class);

		checkIfRentalDatesIsValid(rentalCarUpdate);
		checkIfCarUnderMaintenance(rentalCarUpdate);

		orderedAdditionalServiceService.deleteAll(rentalCarUpdate.getId());
		rentalCarUpdate.setOrderedAdditionalServices(orderedAdditionalServiceMapper(
				updateRentalCarModel.getCreateOrderedAdditionalServiceRequest(), rentalCarUpdate));
		rentalCarUpdate
				.setStartingKilometer(carService.getById(rentalCarUpdate.getCar().getId()).getData().getKilometer());

		this.rentalCarDao.save(rentalCarUpdate);
		return new SuccessResult(BusinessMessages.RENTAL_CAR_UPDATED_SUCCESSFULLY);
	}

	@Override
	public DataResult<GetRentalCarDto> getById(int rentalCarId) {
		checkIfRentalCarIsExistsById(rentalCarId);

		RentalCar result = this.rentalCarDao.getById(rentalCarId);

		GetRentalCarDto response = this.modelMapperService.forDto().map(result, GetRentalCarDto.class);

		return new SuccessDataResult<>(response, BusinessMessages.RENTAL_CAR_GET_SUCCESSFULLY);
	}

	@Override
	public DataResult<List<RentalCarListDto>> getAll() {

		List<RentalCar> result = this.rentalCarDao.findAll();

		List<RentalCarListDto> response = result.stream()
				.map(rentalCar -> this.modelMapperService.forDto().map(rentalCar, RentalCarListDto.class))
				.collect(Collectors.toList());

		return new SuccessDataResult<>(response, BusinessMessages.RENTAL_CAR_LISTED_SUCCESSFULLY);
	}

	@Override
	public DataResult<List<RentalCarListDto>> getAllPaged(int pageNo, int pageSize) {

		Pageable pageable = PageRequest.of(pageNo - 1, pageSize);

		List<RentalCar> result = this.rentalCarDao.findAll(pageable).getContent();

		List<RentalCarListDto> response = result.stream()
				.map(rentalCar -> this.modelMapperService.forDto().map(rentalCar, RentalCarListDto.class))
				.collect(Collectors.toList());

		return new SuccessDataResult<>(response, BusinessMessages.RENTAL_CAR_LISTED_SUCCESSFULLY);
	}

	@Override
	public DataResult<List<RentalCarListDto>> getAllSorted(Direction direction) {

		Sort sort = Sort.by(direction, "endDate");

		List<RentalCar> result = this.rentalCarDao.findAll(sort);

		List<RentalCarListDto> response = result.stream()
				.map(rentalCar -> this.modelMapperService.forDto().map(rentalCar, RentalCarListDto.class))
				.collect(Collectors.toList());

		return new SuccessDataResult<>(response, BusinessMessages.RENTAL_CAR_LISTED_SUCCESSFULLY);
	}

	@Override
	public DataResult<List<RentalCarListDto>> getByCarId(int carId) {

		carService.checkIfCarExistsById(carId);

		List<RentalCar> result = this.rentalCarDao.findByCarId(carId);

		if (result.isEmpty()) {
			return new ErrorDataResult<>("Rental cars could not be listed.");
		}

		List<RentalCarListDto> response = result.stream()
				.map(rentalCar -> this.modelMapperService.forDto().map(rentalCar, RentalCarListDto.class))
				.collect(Collectors.toList());
		return new SuccessDataResult<>(response, BusinessMessages.RENTAL_CAR_LISTED_SUCCESSFULLY);
	}

	@Override
	public Result delete(int id) {
		checkIfRentalCarIsExistsById(id);

		this.rentalCarDao.deleteById(id);

		return new SuccessResult(BusinessMessages.RENTAL_CAR_DELETED_SUCCESSFULLY);
	}

	@Override
	public Result endOfRent(EndOfRentRequest endOfRentRequest) {

		RentalCar rentalCar = rentalCarDao.getById(endOfRentRequest.getId());

		rentalCar.setEndingKilometer(endOfRentRequest.getEndingKilometer());

		carService.updateKilometer(rentalCar.getCar().getId(), rentalCar.getEndingKilometer());

		rentalCarDao.save(rentalCar);

		return new SuccessResult("Ending kilometer recorded, car rental is over.");
	}

	@Override
	public void checkIfRentalCarIsExistsById(int rentalCarId) {
		if (!this.rentalCarDao.existsById(rentalCarId)) {
			throw new BusinessException(BusinessMessages.RENTAL_CAR_NOT_FOUND);
		}
	}

	private void checkIfRentalDatesIsValid(RentalCar rentalCar) {
		if (rentalCar.getStartingDate().isAfter(rentalCar.getEndDate())) {
			throw new BusinessException(BusinessMessages.RENTAL_CAR_DATE_INVALID);
		}
	}

	private void checkIfCarUnderMaintenance(RentalCar rentalCar) {
		List<CarMaintenanceListDto> result = this.carMaintenanceService.getByCarId(rentalCar.getCar().getId())
				.getData();
		if (result != null) {
			for (CarMaintenanceListDto carMaintenanceDto : result) {
				if (rentalCar.getStartingDate().isBefore(carMaintenanceDto.getReturnDate())
						|| rentalCar.getStartingDate().isEqual(carMaintenanceDto.getReturnDate())
						|| rentalCar.getEndDate().isBefore(carMaintenanceDto.getReturnDate())
						|| rentalCar.getEndDate().isEqual(carMaintenanceDto.getReturnDate())) {
					throw new BusinessException(BusinessMessages.CAR_UNDER_MAINTENANCE_IN_RENT_DATE);
				}
			}
		}
	}

	private void checkIfCarAlreadyRented(CreateRentalCarRequest createRentalCarRequest) {
		List<RentalCar> rentalCars = rentalCarDao.findByCarId(createRentalCarRequest.getCarId());

		for (RentalCar rentalCar : rentalCars) {

			if ((createRentalCarRequest.getStartingDate().equals(rentalCar.getStartingDate()))
					|| (createRentalCarRequest.getEndDate().equals(rentalCar.getEndDate()))
					|| (createRentalCarRequest.getEndDate().equals(rentalCar.getStartingDate()))
					|| (createRentalCarRequest.getStartingDate().equals(rentalCar.getEndDate()))
					|| (createRentalCarRequest.getStartingDate().isBefore(rentalCar.getEndDate())
							&& createRentalCarRequest.getStartingDate().isAfter(rentalCar.getStartingDate()))
					|| (createRentalCarRequest.getEndDate().isAfter(rentalCar.getStartingDate())
							&& createRentalCarRequest.getEndDate().isBefore(rentalCar.getEndDate()))) {
				throw new BusinessException(BusinessMessages.CAR_ALREADY_RENTED);
			}
		}
	}

	private void checkIfUpdatedCarAlreadyRented(UpdateRentalCarRequest updateRentalCarRequest) {
		List<RentalCar> rentalCars = rentalCarDao.findByCarId(updateRentalCarRequest.getCarId());

		for (RentalCar rentalCar : rentalCars) {

			if (updateRentalCarRequest.getId() != rentalCar.getId()
					&& ((updateRentalCarRequest.getStartingDate().equals(rentalCar.getStartingDate()))
							|| (updateRentalCarRequest.getEndDate().equals(rentalCar.getEndDate()))
							|| (updateRentalCarRequest.getEndDate().equals(rentalCar.getStartingDate()))
							|| (updateRentalCarRequest.getStartingDate().equals(rentalCar.getEndDate()))
							|| (updateRentalCarRequest.getStartingDate().isBefore(rentalCar.getEndDate())
									&& updateRentalCarRequest.getStartingDate().isAfter(rentalCar.getStartingDate()))
							|| (updateRentalCarRequest.getEndDate().isAfter(rentalCar.getStartingDate())
									&& updateRentalCarRequest.getEndDate().isBefore(rentalCar.getEndDate())))) {
				throw new BusinessException(BusinessMessages.CAR_ALREADY_RENTED);
			}
		}
	}

	private List<OrderedAdditionalService> orderedAdditionalServiceMapper(
			List<CreateOrderedAdditionalServiceRequest> orderedAdditionalServiceRequests, RentalCar rentalCar) {
		List<OrderedAdditionalService> orderedAdditionalServices = orderedAdditionalServiceRequests.stream()
				.map(additionalService -> this.modelMapperService.forRequest().map(additionalService,
						OrderedAdditionalService.class))
				.collect(Collectors.toList());

		for (OrderedAdditionalService orderedAdditionalService : orderedAdditionalServices) {
			additionalServiceService
					.checkIfAdditionalServiceExistsById(orderedAdditionalService.getAdditionalService().getId());
			orderedAdditionalService.setRentalCar(rentalCar);
			orderedAdditionalService.setId(0);
		}
		return orderedAdditionalServices;
	}
}
