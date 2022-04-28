package com.turkcell.rentACar.api.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.turkcell.rentACar.api.models.rentalCar.payment.ExtraPaymentModel;
import com.turkcell.rentACar.api.models.rentalCar.payment.PaymentModel;
import com.turkcell.rentACar.business.abstracts.PaymentService;
import com.turkcell.rentACar.business.dtos.payment.GetPaymentDto;
import com.turkcell.rentACar.business.dtos.payment.PaymentListDto;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;

@RestController
@RequestMapping("/api/payments")
public class PaymentsController {

	private PaymentService paymentService;

	@Autowired
	public PaymentsController(PaymentService paymentService) {
		this.paymentService = paymentService;
	}

	@GetMapping("/getAll")
	public DataResult<List<PaymentListDto>> getAll() {
		return this.paymentService.getAll();
	}

	@PostMapping("/addForCorporateCustomer")
	public Result addPaymentForCorporateCustomer(@RequestBody @Valid PaymentModel paymentModel) {
		return this.paymentService.makePaymentForCorporateCustomer(paymentModel);
	}

	@PostMapping("/addForIndividualCustomer")
	public Result addPaymentForIndividualCustomer(@RequestBody @Valid PaymentModel paymentModel) {
		return this.paymentService.makePaymentForIndividualCustomer(paymentModel);
	}

	@PostMapping("/addForLateDelivery")
	public Result addPaymentForLateDelivery(@RequestBody @Valid ExtraPaymentModel extraPaymentModel) {
		return this.paymentService.makeExtraPayment(extraPaymentModel);
	}

	@GetMapping("/getById/{id}")
	public DataResult<GetPaymentDto> getById(@RequestParam int id) {
		return this.paymentService.getById(id);
	}

	@DeleteMapping("/delete/{id}")
	public Result delete(@RequestParam int id) {
		return this.paymentService.delete(id);
	}
}
