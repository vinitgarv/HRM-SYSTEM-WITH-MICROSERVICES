package in.metalab.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequest {
	
	private String id;
	
	private String firstName;
	
	private String lastName;
	
	private String email;
	 
	private String mobileNo;
	
	private String password;

}
