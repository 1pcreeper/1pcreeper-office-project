package project.office.model.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AppUserResponseDTO {
    private Long id;
    private String name;
//    private AppUserRole role;
//    private ActivationStatus status;
}