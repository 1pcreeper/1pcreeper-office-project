package project.office.model.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AppUserRequestDTO {
    @NotEmpty(message = "Name must not be empty")
    private String name;

    @NotEmpty(message = "Password must not be empty")
    private String password;
}