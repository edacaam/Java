package com.turkcell.rentACar.business.concretes;

import org.springframework.stereotype.Service;

import com.turkcell.rentACar.business.abstracts.UserService;
import com.turkcell.rentACar.business.constants.messages.BusinessMessages;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.dataAccess.abstracts.UserDao;
import com.turkcell.rentACar.entities.concretes.User;

@Service
public class UserManager implements UserService {

	private UserDao userDao;

	public UserManager(UserDao userDao) {
		this.userDao = userDao;
	}

	@Override
	public void checkIfUserAlreadyExistsByEmail(String email) {
		if (this.userDao.existsByEmail(email)) {
			throw new BusinessException(BusinessMessages.USER_EMAIL_ALREADY_EXISTS);
		}
	}

	@Override
	public User getUserByEmail(String email) {
		return this.userDao.findByEmail(email);
	}
}
