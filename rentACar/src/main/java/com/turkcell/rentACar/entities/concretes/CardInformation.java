package com.turkcell.rentACar.entities.concretes;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "card_informations")
public class CardInformation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "card_information_id")
	private Integer id;

	@Column(name = "card_no")
	private String cardNo;

	@Column(name = "card_holder")
	private String cardHolder;

	@Column(name = "expiration_month")
	private String expirationMonth;

	@Column(name = "expiration_year")
	private String expirationYear;

	@Column(name = "cvv")
	private String cvv;

	@ManyToOne
	@JoinColumn(name = "customer_id")
	private Customer customer;
}
