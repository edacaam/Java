package com.turkcell.rentACar.dataAccess.abstracts;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.turkcell.rentACar.entities.concretes.OrderedAdditionalService;

@Repository
public interface OrderedAdditonalServiceDao extends JpaRepository<OrderedAdditionalService, Integer> {
 List<OrderedAdditionalService> findByRentalCar_RentalCarId(int id);
}
