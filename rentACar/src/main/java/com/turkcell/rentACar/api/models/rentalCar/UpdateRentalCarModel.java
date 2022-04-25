package com.turkcell.rentACar.api.models.rentalCar;

import java.util.List;

import com.turkcell.rentACar.business.requests.CreateOrderedAdditionalServiceRequest;
import com.turkcell.rentACar.business.requests.UpdateRentalCarRequest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateRentalCarModel {

	private UpdateRentalCarRequest updateRentalCarRequest;

	private List<CreateOrderedAdditionalServiceRequest> createOrderedAdditionalServiceRequest;
}
