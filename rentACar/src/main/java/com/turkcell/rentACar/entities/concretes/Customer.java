package com.turkcell.rentACar.entities.concretes;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "customers")
@AllArgsConstructor
@NoArgsConstructor
@PrimaryKeyJoinColumn(name = "customer_id", referencedColumnName = "user_id")
public class Customer extends User {

	@Column(name = "registration_date")
	private LocalDate registrationDate;

	@OneToMany(mappedBy = "customer")
	private List<RentalCar> rentalCars;

	@OneToMany(mappedBy = "customer")
	private List<Invoice> invoices;

	@OneToMany(mappedBy = "customer")
	private List<Payment> payments;

	@OneToMany(mappedBy = "customer")
	private List<CardInformation> cardInformations;
}
