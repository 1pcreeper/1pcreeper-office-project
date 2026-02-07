package project.office.model.dto.response;

import lombok.Builder;
import lombok.Data;
import project.office.model.entity.enums.AppUserRole;

import java.util.Set;

@Data
@Builder
public class AppUserPrincipalResponseDTO {
    private AppUserResponseDTO user;
    private Set<AppUserRole> roles;
}
