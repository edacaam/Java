package com.turkcell.rentACar.business.abstracts;

import com.turkcell.rentACar.entities.concretes.User;

public interface UserService {

	void checkIfUserAlreadyExistsByEmail(String email);

	User getUserByEmail(String email);
}
