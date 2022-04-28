package com.turkcell.rentACar.business.concretes;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.turkcell.rentACar.api.models.rentalCar.payment.ExtraPaymentModel;
import com.turkcell.rentACar.api.models.rentalCar.payment.PaymentModel;
import com.turkcell.rentACar.business.abstracts.InvoiceService;
import com.turkcell.rentACar.business.abstracts.PaymentService;
import com.turkcell.rentACar.business.abstracts.PosService;
import com.turkcell.rentACar.business.abstracts.RentalCarService;
import com.turkcell.rentACar.business.constants.messages.BusinessMessages;
import com.turkcell.rentACar.business.dtos.payment.GetPaymentDto;
import com.turkcell.rentACar.business.dtos.payment.PaymentListDto;
import com.turkcell.rentACar.business.dtos.rentalCar.GetRentalCarDto;
import com.turkcell.rentACar.business.requests.invoice.CreateInvoiceRequest;
import com.turkcell.rentACar.business.requests.payment.CreatePaymentRequest;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;
import com.turkcell.rentACar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentACar.core.utilities.results.SuccessResult;
import com.turkcell.rentACar.dataAccess.abstracts.PaymentDao;
import com.turkcell.rentACar.entities.concretes.Invoice;
import com.turkcell.rentACar.entities.concretes.Payment;
import com.turkcell.rentACar.entities.concretes.RentalCar;

@Service
public class PaymentManager implements PaymentService {

	private PaymentDao paymentDao;
	private ModelMapperService modelMapperService;
	private RentalCarService rentalCarService;
	private InvoiceService invoiceService;
	private PosService posService;

	@Autowired
	public PaymentManager(PaymentDao paymentDao, ModelMapperService modelMapperService,
			RentalCarService rentalCarService, InvoiceService invoiceService, PosService posService) {
		this.paymentDao = paymentDao;
		this.modelMapperService = modelMapperService;
		this.rentalCarService = rentalCarService;
		this.invoiceService = invoiceService;
		this.posService = posService;
	}

	@Override
	public void addPayment(CreatePaymentRequest createPaymentRequest, Invoice invoice, RentalCar rentalCar) {

		Payment payment = modelMapperService.forRequest().map(createPaymentRequest, Payment.class);
		payment.setRentalCar(rentalCar);
		payment.setInvoice(invoice);
		payment.setCustomer(rentalCar.getCustomer());
		payment.setAmount(invoice.getTotalPrice());

		paymentDao.save(payment);
	}

	@Override
	@Transactional
	public Result makePaymentForCorporateCustomer(PaymentModel paymentModel) {

		posService.makePayment(paymentModel.getPaymentRequest());

		runPaymentSuccessorForCorporateCustomer(paymentModel);

		return new SuccessResult(BusinessMessages.PAYMENT_ADDED_SUCCESSFULLY);
	}

	@Override
	@Transactional
	public Result makePaymentForIndividualCustomer(PaymentModel paymentModel) {

		posService.makePayment(paymentModel.getPaymentRequest());

		runPaymentSuccessorForIndividualCustomer(paymentModel);

		return new SuccessResult(BusinessMessages.PAYMENT_ADDED_SUCCESSFULLY);
	}

	@Transactional
	public void runPaymentSuccessorForCorporateCustomer(PaymentModel paymentModel) {

		RentalCar rentalCar = rentalCarService.addForCorporateCustomer(paymentModel.getRentalCarModel()).getData();

		Invoice invoice = invoiceService.add(new CreateInvoiceRequest(rentalCar.getId())).getData();

		addPayment(paymentModel.getPaymentRequest(), invoice, rentalCar);
	}

	@Transactional
	public void runPaymentSuccessorForIndividualCustomer(PaymentModel paymentModel) {

		RentalCar rentalCar = rentalCarService.addForIndividualCustomer(paymentModel.getRentalCarModel()).getData();

		Invoice invoice = invoiceService.add(new CreateInvoiceRequest(rentalCar.getId())).getData();

		addPayment(paymentModel.getPaymentRequest(), invoice, rentalCar);
	}

	@Override
	@Transactional
	public Result makeExtraPayment(ExtraPaymentModel extraPaymentModel) {
		posService.makePayment(extraPaymentModel.getPaymentRequest());

		runExtraPaymentSuccessor(extraPaymentModel);

		return new SuccessResult(BusinessMessages.PAYMENT_ADDED_SUCCESSFULLY);
	}

	@Transactional
	public void runExtraPaymentSuccessor(ExtraPaymentModel extraPaymentModel) {

		GetRentalCarDto rentalCarDto = rentalCarService
				.getById(extraPaymentModel.getEndOfRentRequest().getRentalCarId()).getData();

		RentalCar rentalCar = this.modelMapperService.forRequest().map(rentalCarDto, RentalCar.class);
		checkIfExtraPaymentDateNotLate(rentalCar.getEndDate(), extraPaymentModel.getEndOfRentRequest().getEndingDate());

		Invoice invoice = invoiceService.addLateDeliveredInvoice(new CreateInvoiceRequest(rentalCar.getId()),
				extraPaymentModel.getEndOfRentRequest().getEndingDate()).getData();

		rentalCarService.endOfRent(extraPaymentModel.getEndOfRentRequest());

		addPayment(extraPaymentModel.getPaymentRequest(), invoice, rentalCar);
	}

	@Override
	public DataResult<List<PaymentListDto>> getAll() {

		List<Payment> result = this.paymentDao.findAll();

		List<PaymentListDto> response = result.stream()
				.map(payment -> this.modelMapperService.forDto().map(payment, PaymentListDto.class))
				.collect(Collectors.toList());

		return new SuccessDataResult<>(response, BusinessMessages.PAYMENT_LISTED_SUCCESSFULLY);
	}

	@Override
	public DataResult<GetPaymentDto> getById(int id) {

		checkIfPaymentIsExistsById(id);

		Payment payment = this.paymentDao.getById(id);

		GetPaymentDto response = this.modelMapperService.forDto().map(payment, GetPaymentDto.class);

		return new SuccessDataResult<>(response, BusinessMessages.PAYMENT_GET_SUCCESSFULLY);
	}

	@Override
	public Result delete(int id) {

		checkIfPaymentIsExistsById(id);

		this.paymentDao.deleteById(id);

		return new SuccessResult(BusinessMessages.PAYMENT_DELETED_SUCCESSFULLY);
	}

	private void checkIfPaymentIsExistsById(int paymentId) {
		if (!this.paymentDao.existsById(paymentId)) {
			throw new BusinessException(BusinessMessages.PAYMENT_NOT_FOUND);
		}
	}

	private void checkIfExtraPaymentDateNotLate(LocalDate rentalCarEndDate, LocalDate lateDate) {
		if (lateDate.isBefore(rentalCarEndDate)) {
			throw new BusinessException(BusinessMessages.NOT_LATE_DELIVERY);
		}
	}
}
