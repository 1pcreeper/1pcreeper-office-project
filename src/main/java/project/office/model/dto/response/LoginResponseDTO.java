package project.office.model.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponseDTO {
    private String accessToken;
}
