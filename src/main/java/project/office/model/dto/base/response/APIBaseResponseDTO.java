package project.office.model.dto.base.response;

import jakarta.annotation.Nullable;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class APIBaseResponseDTO<T> {
    private boolean success;
    private String message;
    private T data;

    public static <D> APIBaseResponseDTO<D> success(@Nullable D data){
        return APIBaseResponseDTO.<D>builder()
                .success(true)
                .message("")
                .data(data)
                .build();
    }

    public static APIBaseResponseDTO<Object> error(String message){
        return APIBaseResponseDTO.builder()
                .success(false)
                .message(message)
                .data(null)
                .build();
    }
}
