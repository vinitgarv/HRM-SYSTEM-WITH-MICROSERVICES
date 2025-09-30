package in.metalab.helper;

import in.metalab.constants.UserCommonMessages;
import in.metalab.dto.UserRequest;

public class UserFieldValidation {

	public static void validateFields(UserRequest request) {
		if (request.getFirstName()==null || request.getFirstName().isEmpty()) {
			throw new RuntimeException(UserCommonMessages.FIRST_NAME_REQUIRED);
		}
		
		if (request.getLastName()==null || request.getLastName().isEmpty()) {
			throw new RuntimeException(UserCommonMessages.LAST_NAME_REQUIRED);
		}
		
		if (request.getEmail()==null || request.getEmail().isEmpty()) {
			throw new RuntimeException(UserCommonMessages.EMAIL_REQUIRED);
		}
		
		if (request.getMobileNo()==null || request.getMobileNo().isEmpty()) {
			throw new RuntimeException(UserCommonMessages.MOBILE_NO_REQUIRED);
		}
		
		if (request.getPassword()==null || request.getPassword().isEmpty()) {
			throw new RuntimeException(UserCommonMessages.PASSWORD_REQUIRED);
		}
	}
}
