package ir.snapppay.purchasemng.exception;

public class UserIdAlreadyExistException extends RuntimeException {

	public UserIdAlreadyExistException(String userIdIsAlreadyTaken) {
		super(userIdIsAlreadyTaken);
	}

}
