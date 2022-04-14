package com.turkcell.rentACar.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.turkcell.rentACar.entities.concretes.IndividualCustomer;

@Repository
public interface IndividualCustomerDao extends JpaRepository<IndividualCustomer, Integer> {

	boolean existsByNationalIdentity(String nationalIdentity);

	IndividualCustomer findByNationalIdentity(String nationalIdentity);
}
