package com.rajeshkawali.exception;

import java.util.function.Function;
import java.util.function.Supplier;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

/**
 * @author Rajesh_Kawali
 *
 */
@Component
public class ResponseStatus {

	public static Function<Long, ResponseStatusException> idNotFound = (id) -> {
		return new ResponseStatusException(HttpStatus.NOT_FOUND, "No customer available with the given Id: " + id);
	};

	public static Supplier<ResponseStatusException> serverError = () -> {
		throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "RunTimeException from customer service");
	};
}
