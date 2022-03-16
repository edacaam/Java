package com.turkcell.rentACar.dataAccess.abstracts;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.turkcell.rentACar.entities.concretes.Color;
import com.turkcell.rentACar.entities.concretes.Invoice;

@Repository
public interface InvoiceDao extends JpaRepository<Invoice, Integer> {
	List<Invoice> findByCustomerUserId(int customerId);
	List<Invoice> findByCreationDateBetween(LocalDate startDate,LocalDate endDate);
}
