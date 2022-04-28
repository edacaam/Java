package com.turkcell.rentACar.business.abstracts;

import java.time.LocalDate;
import java.util.List;

import com.turkcell.rentACar.business.dtos.invoice.GetInvoiceDto;
import com.turkcell.rentACar.business.dtos.invoice.InvoiceListDto;
import com.turkcell.rentACar.business.requests.invoice.CreateInvoiceRequest;
import com.turkcell.rentACar.business.requests.invoice.UpdateInvoiceRequest;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;
import com.turkcell.rentACar.entities.concretes.Invoice;

public interface InvoiceService {

	DataResult<Invoice> add(CreateInvoiceRequest createInvoiceRequest);

	DataResult<Invoice> addLateDeliveredInvoice(CreateInvoiceRequest createInvoiceRequest, LocalDate lateDate);

	Result delete(int id);

	Result update(UpdateInvoiceRequest updateInvoiceRequest);

	DataResult<GetInvoiceDto> getById(int id);

	DataResult<List<InvoiceListDto>> getAll();

	DataResult<List<InvoiceListDto>> getByCustomerId(int customerId);

	DataResult<List<InvoiceListDto>> getByDateRange(LocalDate startDate, LocalDate endDate);

	void checkIfInvoiceExistsById(int id);
}
