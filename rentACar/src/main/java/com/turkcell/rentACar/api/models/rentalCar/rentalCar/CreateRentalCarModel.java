package com.turkcell.rentACar.api.models.rentalCar.rentalCar;

import java.util.List;

import com.turkcell.rentACar.business.requests.orderedAdditionalService.CreateOrderedAdditionalServiceRequest;
import com.turkcell.rentACar.business.requests.rentalCar.CreateRentalCarRequest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateRentalCarModel {

	private CreateRentalCarRequest createRentalCarRequest;

	private List<CreateOrderedAdditionalServiceRequest> createOrderedAdditionalServiceRequest;
}
