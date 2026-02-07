package project.office.controller.common.admin;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.office.mapper.AppUserMapper;
import project.office.mapper.PaginationMapper;
import project.office.model.dto.base.response.PaginationBaseResponseDTO;
import project.office.model.dto.objects.AppUserDTO;
import project.office.model.dto.request.AppUserRequestDTO;
import project.office.model.dto.response.AppUserResponseDTO;
import project.office.service.auth.AppUserDetailsService;

import java.util.List;

@RestController
@RequestMapping("/admin/users")
@RequiredArgsConstructor
public class AdminAppUserController {
    private final AppUserMapper appUserMapper;
    private final AppUserDetailsService appUserDetailsService;
    private final PaginationMapper paginationMapper;

    @PostMapping
    public AppUserResponseDTO createAppUser(@RequestBody @Valid AppUserRequestDTO requestDto) {
        return appUserDetailsService.createUser(requestDto);
    }

    @PutMapping("/{id}")
    public AppUserResponseDTO updateUser(@PathVariable Long id,
                                         @RequestBody @Valid AppUserRequestDTO requestDto) {
        return appUserDetailsService.updateUser(id, requestDto);
    }

    @GetMapping
    public ResponseEntity<PaginationBaseResponseDTO<AppUserDTO>> findAll(@PageableDefault Pageable pageable) {
        // Assuming service has paginated findAll; add if needed
        PaginationBaseResponseDTO<AppUserDTO> response = PaginationBaseResponseDTO.<AppUserDTO>builder()
                .content(List.of())
                .pageNumber(pageable.getPageNumber())
                .pageSize(pageable.getPageSize())
                .totalPages(0)
                .totalElements(0L)
                .build();
        return ResponseEntity.ok(response);
    }
}