package in.metalab.mapper;

import in.metalab.dto.UserRequest;
import in.metalab.dto.UserResponse;
import in.metalab.entity.User;
import in.metalab.utils.PasswordUtils;

public class UserMapper {

	public static User convertUserReqToUser(UserRequest request) {

		return User.builder().firstName(request.getFirstName()).lastName(request.getLastName())
				.email(request.getEmail()).mobileNo(request.getMobileNo())
				.password(PasswordUtils.encodePassword(request.getPassword())).build();
	}
	
	public static UserResponse convertUserToUserRes(User user) {
		return UserResponse.builder()
				.id(user.getId())
				.firstName(user.getFirstName())
				.lastName(user.getLastName())
				.email(user.getEmail())
				.mobileNo(user.getMobileNo())
				.build();
	}
}
