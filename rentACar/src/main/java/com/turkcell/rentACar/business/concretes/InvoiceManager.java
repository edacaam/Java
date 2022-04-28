package com.turkcell.rentACar.business.concretes;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.turkcell.rentACar.business.abstracts.CarService;
import com.turkcell.rentACar.business.abstracts.CustomerService;
import com.turkcell.rentACar.business.abstracts.InvoiceService;
import com.turkcell.rentACar.business.abstracts.RentalCarService;
import com.turkcell.rentACar.business.constants.messages.BusinessMessages;
import com.turkcell.rentACar.business.dtos.invoice.GetInvoiceDto;
import com.turkcell.rentACar.business.dtos.invoice.InvoiceListDto;
import com.turkcell.rentACar.business.dtos.orderedAdditionalService.OrderedAdditionalServiceListDto;
import com.turkcell.rentACar.business.dtos.rentalCar.GetRentalCarDto;
import com.turkcell.rentACar.business.requests.invoice.CreateInvoiceRequest;
import com.turkcell.rentACar.business.requests.invoice.UpdateInvoiceRequest;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;
import com.turkcell.rentACar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentACar.core.utilities.results.SuccessResult;
import com.turkcell.rentACar.dataAccess.abstracts.InvoiceDao;
import com.turkcell.rentACar.entities.concretes.Invoice;

@Service
public class InvoiceManager implements InvoiceService {

	private InvoiceDao invoiceDao;
	private ModelMapperService modelMapperService;
	private RentalCarService rentalCarService;
	private CarService carService;
	private CustomerService customerService;

	public InvoiceManager(InvoiceDao invoiceDao, ModelMapperService modelMapperService,
			RentalCarService rentalCarService, CarService carService, CustomerService customerService) {
		this.invoiceDao = invoiceDao;
		this.modelMapperService = modelMapperService;
		this.rentalCarService = rentalCarService;
		this.carService = carService;
		this.customerService = customerService;
	}

	@Override
	public DataResult<Invoice> add(CreateInvoiceRequest createInvoiceRequest) {

		rentalCarService.checkIfRentalCarIsExistsById(createInvoiceRequest.getRentalCarId());

		Invoice invoice = this.modelMapperService.forRequest().map(createInvoiceRequest, Invoice.class);

		GetRentalCarDto rentalCarDto = rentalCarService.getById(createInvoiceRequest.getRentalCarId()).getData();

		invoice.setId(0);

		setInvoiceFields(invoice, rentalCarDto);

		Invoice savedInvoice = this.invoiceDao.save(invoice);

		return new SuccessDataResult<>(savedInvoice, BusinessMessages.INVOICE_ADDED_SUCCESSFULLY);
	}

	@Override
	public DataResult<Invoice> addLateDeliveredInvoice(CreateInvoiceRequest createInvoiceRequest, LocalDate lateDate) {

		rentalCarService.checkIfRentalCarIsExistsById(createInvoiceRequest.getRentalCarId());

		Invoice invoice = this.modelMapperService.forRequest().map(createInvoiceRequest, Invoice.class);

		GetRentalCarDto rentalCarDto = rentalCarService.getById(createInvoiceRequest.getRentalCarId()).getData();

		invoice.setId(0);

		setInvoiceFieldsForLateDelivery(invoice, rentalCarDto, lateDate);

		Invoice savedInvoice = this.invoiceDao.save(invoice);

		return new SuccessDataResult<>(savedInvoice, BusinessMessages.INVOICE_ADDED_SUCCESSFULLY);
	}

	@Override
	public Result delete(int id) {

		checkIfInvoiceExistsById(id);

		this.invoiceDao.deleteById(id);

		return new SuccessResult(BusinessMessages.INVOICE_DELETED_SUCCESSFULLY);
	}

	@Override
	public Result update(UpdateInvoiceRequest updateInvoiceRequest) {

		checkIfInvoiceExistsById(updateInvoiceRequest.getId());
		rentalCarService.checkIfRentalCarIsExistsById(updateInvoiceRequest.getRentalCarId());

		Invoice invoice = this.modelMapperService.forRequest().map(updateInvoiceRequest, Invoice.class);

		GetRentalCarDto rentalCarDto = rentalCarService.getById(updateInvoiceRequest.getRentalCarId()).getData();

		setInvoiceFields(invoice, rentalCarDto);

		this.invoiceDao.save(invoice);

		return new SuccessResult(BusinessMessages.INVOICE_UPDATED_SUCCESSFULLY);
	}

	@Override
	public DataResult<GetInvoiceDto> getById(int id) {

		checkIfInvoiceExistsById(id);

		Invoice invoice = invoiceDao.getById(id);

		GetInvoiceDto response = this.modelMapperService.forDto().map(invoice, GetInvoiceDto.class);

		return new SuccessDataResult<>(response, BusinessMessages.INVOICE_GET_SUCCESSFULLY);
	}

	@Override
	public DataResult<List<InvoiceListDto>> getAll() {

		List<Invoice> result = this.invoiceDao.findAll();

		List<InvoiceListDto> response = result.stream()
				.map(invoice -> this.modelMapperService.forDto().map(invoice, InvoiceListDto.class))
				.collect(Collectors.toList());

		return new SuccessDataResult<>(response, BusinessMessages.INVOICE_LISTED_SUCCESSFULLY);
	}

	@Override
	public DataResult<List<InvoiceListDto>> getByCustomerId(int customerId) {

		customerService.checkIfCustomerExistsById(customerId);

		List<Invoice> result = this.invoiceDao.findByCustomerUserId(customerId);

		List<InvoiceListDto> response = result.stream()
				.map(invoice -> this.modelMapperService.forDto().map(invoice, InvoiceListDto.class))
				.collect(Collectors.toList());

		return new SuccessDataResult<>(response, BusinessMessages.INVOICE_LISTED_SUCCESSFULLY);
	}

	@Override
	public DataResult<List<InvoiceListDto>> getByDateRange(LocalDate startDate, LocalDate endDate) {

		List<Invoice> result = this.invoiceDao.findByCreationDateBetween(startDate, endDate);

		List<InvoiceListDto> response = result.stream()
				.map(invoice -> this.modelMapperService.forDto().map(invoice, InvoiceListDto.class))
				.collect(Collectors.toList());

		return new SuccessDataResult<>(response, BusinessMessages.INVOICE_LISTED_SUCCESSFULLY);
	}

	private void setInvoiceFields(Invoice invoice, GetRentalCarDto rentalCarDto) {
		invoice.setTotalRentDay(calculateTotalDay(rentalCarDto));
		invoice.setTotalPrice(calculateTotalPrice(rentalCarDto));
		invoice.setRentStartDate(rentalCarDto.getStartingDate());
		invoice.setRentEndDate(rentalCarDto.getEndDate());
		invoice.setCreationDate(LocalDate.now());
		invoice.setCustomer(customerService.getById(rentalCarDto.getCustomerUserId()).getData());
		invoice.setInvoiceNumber(System.currentTimeMillis() + rentalCarDto.getId().toString());
	}

	private void setInvoiceFieldsForLateDelivery(Invoice invoice, GetRentalCarDto rentalCarDto, LocalDate lateDate) {
		invoice.setTotalRentDay(calculateTotalDayForLateDelivery(rentalCarDto, lateDate));
		invoice.setTotalPrice(calculateTotalPriceForLateDelivery(rentalCarDto, lateDate));
		invoice.setRentStartDate(rentalCarDto.getEndDate());
		invoice.setRentEndDate(lateDate);
		invoice.setCreationDate(LocalDate.now());
		invoice.setCustomer(customerService.getById(rentalCarDto.getCustomerUserId()).getData());
		invoice.setInvoiceNumber(System.currentTimeMillis() + rentalCarDto.getId().toString());
	}

	private double calculateTotalPrice(GetRentalCarDto rentalCar) {
		int totalDate = calculateTotalDay(rentalCar);
		return calculateCarRentPrice(rentalCar, totalDate) + calculateAdditionalServicesPrice(rentalCar, totalDate)
				+ calculateDifferentCityPrice(rentalCar);
	}

	private double calculateTotalPriceForLateDelivery(GetRentalCarDto rentalCar, LocalDate lateDate) {
		int totalDate = calculateTotalDayForLateDelivery(rentalCar, lateDate);
		return calculateCarRentPrice(rentalCar, totalDate) + calculateAdditionalServicesPrice(rentalCar, totalDate);
	}

	private int calculateTotalDay(GetRentalCarDto rentalCarDto) {
		return (int) (ChronoUnit.DAYS.between(rentalCarDto.getStartingDate(), rentalCarDto.getEndDate()) + 1);
	}

	private int calculateTotalDayForLateDelivery(GetRentalCarDto rentalCarDto, LocalDate lateDate) {
		return (int) ChronoUnit.DAYS.between(rentalCarDto.getEndDate(), lateDate);
	}

	private double calculateAdditionalServicesPrice(GetRentalCarDto rentalCarDto, int totalDate) {
		double additionalServicePrice = 0;
		for (OrderedAdditionalServiceListDto orderedAdditionalService : rentalCarDto
				.getOrderedAdditionalServiceList()) {
			additionalServicePrice += totalDate * orderedAdditionalService.getAdditionalService().getDailyPrice();
		}
		return additionalServicePrice;
	}

	private double calculateDifferentCityPrice(GetRentalCarDto rentalCarDto) {
		if (!rentalCarDto.getCityOfDeliveryId().equals(rentalCarDto.getCityOfPickUpId())) {
			return 750;
		}
		return 0;
	}

	private double calculateCarRentPrice(GetRentalCarDto rentalCarDto, int totalDate) {

		return totalDate * (carService.getById(rentalCarDto.getCarId()).getData().getDailyPrice());
	}

	@Override
	public void checkIfInvoiceExistsById(int id) {
		if (!this.invoiceDao.existsById(id)) {
			throw new BusinessException(BusinessMessages.INVOICE_NOT_FOUND);
		}
	}
}
