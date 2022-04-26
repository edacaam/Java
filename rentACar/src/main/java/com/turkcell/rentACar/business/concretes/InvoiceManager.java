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
import com.turkcell.rentACar.business.dtos.GetInvoiceDto;
import com.turkcell.rentACar.business.dtos.GetRentalCarDto;
import com.turkcell.rentACar.business.dtos.InvoiceListDto;
import com.turkcell.rentACar.business.dtos.OrderedAdditionalServiceListDto;
import com.turkcell.rentACar.business.requests.CreateInvoiceRequest;
import com.turkcell.rentACar.business.requests.UpdateInvoiceRequest;
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
	public Result add(CreateInvoiceRequest createInvoiceRequest) {

		rentalCarService.checkIfRentalCarIsExistsById(createInvoiceRequest.getRentalCarId());

		Invoice invoice = this.modelMapperService.forRequest().map(createInvoiceRequest, Invoice.class);

		GetRentalCarDto rentalCarDto = rentalCarService.getById(createInvoiceRequest.getRentalCarId()).getData();

		invoice.setId(0);

		setInvoiceFields(invoice, rentalCarDto);

		this.invoiceDao.save(invoice);

		return new SuccessResult(BusinessMessages.INVOICE_ADDED_SUCCESSFULLY);
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
	}

	private double calculateTotalPrice(GetRentalCarDto rentalCar) {
		double totalPrice = calculateTotalDay(rentalCar)
				* (carService.getById(rentalCar.getCarId()).getData().getDailyPrice());

		if (!rentalCar.getCityOfDeliveryId().equals(rentalCar.getCityOfPickUpId())) {
			totalPrice += 750;
		}

		for (OrderedAdditionalServiceListDto orderedAdditionalService : rentalCar.getOrderedAdditionalServiceList()) {
			totalPrice += orderedAdditionalService.getAdditionalService().getDailyPrice();
		}

		return totalPrice;
	}

	private int calculateTotalDay(GetRentalCarDto rentalCarDto) {
		return (int) (ChronoUnit.DAYS.between(rentalCarDto.getStartingDate(), rentalCarDto.getEndDate()) + 1);
	}

	@Override
	public void checkIfInvoiceExistsById(int id) {
		if (!this.invoiceDao.existsById(id)) {
			throw new BusinessException(BusinessMessages.INVOICE_NOT_FOUND);
		}
	}
}
