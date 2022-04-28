package com.turkcell.rentACar.business.abstracts;

import java.util.List;

import com.turkcell.rentACar.business.dtos.carMaintenance.CarMaintenanceListDto;
import com.turkcell.rentACar.business.dtos.carMaintenance.GetCarMaintenanceDto;
import com.turkcell.rentACar.business.requests.carMaintenance.CreateCarMaintenanceRequest;
import com.turkcell.rentACar.business.requests.carMaintenance.UpdateCarMaintenanceRequest;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;

public interface CarMaintenanceService {

	Result add(CreateCarMaintenanceRequest createCarMaintenanceRequest);

	Result update(UpdateCarMaintenanceRequest updateCarMaintenanceRequest);

	Result delete(int id);

	DataResult<GetCarMaintenanceDto> getById(int id);

	DataResult<List<CarMaintenanceListDto>> getAll();

	DataResult<List<CarMaintenanceListDto>> getByCarId(int carId);
}
