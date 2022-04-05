package com.turkcell.rentACar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.turkcell.rentACar.business.abstracts.PaymentService;
import com.turkcell.rentACar.business.abstracts.PosService;
import com.turkcell.rentACar.business.constants.messages.BusinessMessages;
import com.turkcell.rentACar.business.dtos.GetPaymentDto;
import com.turkcell.rentACar.business.dtos.PaymentListDto;
import com.turkcell.rentACar.business.requests.CreatePaymentRequest;
import com.turkcell.rentACar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.ErrorResult;
import com.turkcell.rentACar.core.utilities.results.Result;
import com.turkcell.rentACar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentACar.core.utilities.results.SuccessResult;
import com.turkcell.rentACar.dataAccess.abstracts.PaymentDao;
import com.turkcell.rentACar.entities.concretes.Payment;

@Service
public class PaymentManager implements PaymentService {

	private PaymentDao paymentDao;

	private ModelMapperService modelMapperService;

	private PosService posService;

	@Autowired
	public PaymentManager(PaymentDao paymentDao, ModelMapperService modelMapperService, PosService posService) {
		this.paymentDao = paymentDao;
		this.modelMapperService = modelMapperService;
		this.posService = posService;
	}

	@Override
	public Result delete(int id) {
		this.paymentDao.deleteById(id);
		return new SuccessResult(BusinessMessages.PAYMENT_DELETED_SUCCESSFULLY);
	}

	@Override
	public Result add(CreatePaymentRequest createPaymentRequest) {

		Payment payment = modelMapperService.forRequest().map(createPaymentRequest, Payment.class);

		Result result = posService.makePayment(createPaymentRequest);

		if (result.isSuccess()) {
			runPaymentSuccessor();
			return new SuccessResult(BusinessMessages.PAYMENT_ADDED_SUCCESSFULLY);
		}
		return new ErrorResult();
	}

	@Transactional
	private void runPaymentSuccessor() {
		// rental
		// invoice
		// add service
		// paymentDao.save(payment);
	}

	@Override
	public DataResult<List<PaymentListDto>> getAll() {
		List<Payment> result = this.paymentDao.findAll();
		List<PaymentListDto> response = result.stream()
				.map(payment -> this.modelMapperService.forDto().map(payment, PaymentListDto.class))
				.collect(Collectors.toList());
		return new SuccessDataResult<List<PaymentListDto>>(response, BusinessMessages.PAYMENT_LISTED_SUCCESSFULLY);
	}

	@Override
	public DataResult<GetPaymentDto> getById(int id) {
		Payment payment = this.paymentDao.getById(id);
		GetPaymentDto response = this.modelMapperService.forDto().map(payment, GetPaymentDto.class);
		return new SuccessDataResult<GetPaymentDto>(response, BusinessMessages.PAYMENT_GET_SUCCESSFULLY);
	}

}
