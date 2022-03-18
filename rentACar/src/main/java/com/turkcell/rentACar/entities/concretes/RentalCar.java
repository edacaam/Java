package com.turkcell.rentACar.entities.concretes;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "rental_cars")
public class RentalCar {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "rental_car_id")
	private int rentalCarId;

	@Column(name = "starting_date")
	private LocalDate startingDate;

	@Column(name = "end_date")
	private LocalDate endDate;
	
	@Column(name = "starting_kilometer")
	private Double startingKilometer;

	@Column(name = "ending_kilometer")
	private Double endingKilometer;

	@ManyToOne
	@JoinColumn(name = "car_id")
	private Car car;
	
	@ManyToOne
	@JoinColumn(name = "city_of_pick_up_id")
	private City cityOfPickUp;
	
	@ManyToOne
	@JoinColumn(name = "city_of_delivery_id")
	private City cityOfDelivery;
	
	@OneToMany(mappedBy = "rentalCar", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<OrderedAdditionalService> orderedAdditionalServices;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private Customer customer;
	
	@OneToOne(mappedBy = "rentalCar")
	private Invoice invoice;
}
