package com.turkcell.rentACar.business.abstracts;

import java.time.LocalDate;
import java.util.List;

import com.turkcell.rentACar.business.dtos.GetInvoiceDto;
import com.turkcell.rentACar.business.dtos.InvoiceListDto;
import com.turkcell.rentACar.business.requests.CreateInvoiceRequest;
import com.turkcell.rentACar.business.requests.UpdateInvoiceRequest;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;

public interface InvoiceService {
	Result add(CreateInvoiceRequest createInvoiceRequest);

	Result delete(int id);

	Result update(UpdateInvoiceRequest updateInvoiceRequest);

	DataResult<GetInvoiceDto> getById(int id);

	DataResult<List<InvoiceListDto>> getAll();
	
	DataResult<List<InvoiceListDto>> getByCustomerId(int customerId);
	
	DataResult<List<InvoiceListDto>> getByDateRange(LocalDate startDate,LocalDate endDate);
}
