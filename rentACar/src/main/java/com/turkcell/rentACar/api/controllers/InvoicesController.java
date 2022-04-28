package com.turkcell.rentACar.api.controllers;

import java.time.LocalDate;
import java.util.List;

import javax.validation.Valid;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.turkcell.rentACar.business.abstracts.InvoiceService;
import com.turkcell.rentACar.business.dtos.invoice.GetInvoiceDto;
import com.turkcell.rentACar.business.dtos.invoice.InvoiceListDto;
import com.turkcell.rentACar.business.requests.invoice.CreateInvoiceRequest;
import com.turkcell.rentACar.business.requests.invoice.UpdateInvoiceRequest;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;

@RestController
@RequestMapping("/api/invoices")
public class InvoicesController {
	
	private InvoiceService invoiceService;
	
	public InvoicesController(InvoiceService invoiceService) {
		this.invoiceService = invoiceService;
	}

	@GetMapping("/getAll")
	public DataResult<List<InvoiceListDto>> getAll() {
		return this.invoiceService.getAll();
	}
	
	@PostMapping("/add")
	public Result add(@RequestBody @Valid CreateInvoiceRequest createInvoiceRequest){
		return this.invoiceService.add(createInvoiceRequest);
	}

	@GetMapping("/getById/{id}")
	public DataResult<GetInvoiceDto> getById(@RequestParam int id) {
		return this.invoiceService.getById(id);
	}
	
	@GetMapping("/getByCustomerId/{id}")
	public DataResult<List<InvoiceListDto>> getByCustomerId(@RequestParam int customerId) {
		return this.invoiceService.getByCustomerId(customerId);
	}
	
	@GetMapping("/getByDateRange/{id}")
	public DataResult<List<InvoiceListDto>> getByDateRange( @DateTimeFormat(pattern = "yyyy-MM-dd")LocalDate startDate, @DateTimeFormat(pattern = "yyyy-MM-dd")LocalDate endDate) {
		return this.invoiceService.getByDateRange(startDate, endDate);
	}

	@DeleteMapping("/delete/{id}")
	public Result delete(@RequestParam int id) {
		return this.invoiceService.delete(id);
	}

	@PutMapping("/update")
	public Result update(@RequestBody @Valid UpdateInvoiceRequest updateInvoiceRequest){
		return this.invoiceService.update(updateInvoiceRequest);
	}
}
