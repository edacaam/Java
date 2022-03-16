package com.turkcell.rentACar.business.abstracts;

import com.turkcell.rentACar.core.utilities.results.Result;

public interface OrderedAdditionalServiceService {
	Result deleteAll(int rentalCarId);
}
