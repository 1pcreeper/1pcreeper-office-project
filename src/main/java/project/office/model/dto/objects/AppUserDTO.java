package project.office.model.dto.objects;

import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppUserDTO {
    @Null
    private Long id;
    private String name;
    @Null
    private LocalDateTime createdAt;
    @Null
    private LocalDateTime updatedAt;
}