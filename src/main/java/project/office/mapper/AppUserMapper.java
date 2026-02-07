package project.office.mapper;

import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import project.office.model.dto.objects.AppUserDTO;
import project.office.model.dto.request.AppUserRequestDTO;
import project.office.model.dto.response.AppUserResponseDTO;
import project.office.model.entity.AppUser;

@Component
@RequiredArgsConstructor
public class AppUserMapper {
    private final PasswordEncoder passwordEncoder;

    public AppUserResponseDTO toAppUserResponseDTO(AppUser entity) {
        return AppUserResponseDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .build();
    }

    public AppUser toAppUser(AppUserRequestDTO dto) {
        return toAppUserWithEncoded(new AppUser(), dto);
    }

    public AppUser toAppUserWithEncoded(AppUser appUser, AppUserRequestDTO dto) {
        if (!Strings.isBlank(dto.getPassword())) {
            appUser.setPassword(passwordEncoder.encode(dto.getPassword()));
        }
        appUser.setName(dto.getName());
//        appUser.setDisplayName(dto.getDisplayName());
//        appUser.setEmail(dto.getEmail());
//        appUser.setRoles(new HashSet<>(Set.of(dto.getRole())));
//        appUser.setStatus(dto.getStatus());
        return appUser;
    }

    public AppUserDTO toAppUserDTO(AppUser entity) {
        if (entity == null) {
            return null;
        }
        return AppUserDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}
