package in.hrm.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserTokenResponse
{
    private String userId;
    private String sessionId;
    private String firstName;
    private String lastName;
    private String email;
    private String accessToken;
}
