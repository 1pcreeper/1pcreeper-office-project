package project.office.service.auth;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.office.mapper.AppUserMapper;
import project.office.mapper.PaginationMapper;
import project.office.model.dto.request.AppUserRequestDTO;
import project.office.model.dto.response.AppUserResponseDTO;
import project.office.model.entity.AppUser;
import project.office.model.entity.enums.AppUserRole;
import project.office.model.exception.EntityAlreadyExistsException;
import project.office.model.exception.PropertyInvalidException;
import project.office.model.exception.ResourceNotFoundException;
import project.office.repository.AppUserRepository;
import project.office.service.base.AbstractBaseService;

import java.util.Set;

@Service
public class AppUserDetailsService extends AbstractBaseService<AppUser, Long> implements UserDetailsService {
    private final AppUserMapper appUserMapper;
    private final AppUserRepository appUserRepository;
    private final PaginationMapper paginationMapper;
    protected AppUserDetailsService(AppUserMapper appUserMapper,
                                    AppUserRepository repository,
                                    PaginationMapper paginationMapper) {
        super(repository, "App User");
        this.appUserMapper = appUserMapper;
        this.appUserRepository = repository;
        this.paginationMapper = paginationMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return appUserRepository.findById(Long.decode(username))
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("User not found by Id : %s", username)));
    }

    @Transactional
    public AppUserResponseDTO createUser(AppUserRequestDTO requestDTO) {
        String name = requestDTO.getName();
        validateUser(requestDTO);
        AppUser newAppUser = appUserMapper.toAppUser(requestDTO);
        newAppUser.setRoles(Set.of(AppUserRole.USER));
        AppUser createdAppUser = save(newAppUser);
        return appUserMapper.toAppUserResponseDTO(createdAppUser);
    }

    @Transactional
    public AppUserResponseDTO updateUser(Long id, AppUserRequestDTO requestDto) {
        AppUser appUser = findById(id);
        if(!appUser.getName().equals(requestDto.getName())){
            if (appUserRepository.existsByName(requestDto.getName())) {
                throw new EntityAlreadyExistsException("App User already exists by name: " + requestDto.getName());
            }
        }
        if (requestDto.getPassword().isEmpty() || requestDto.getPassword().isBlank()) {
            throw new PropertyInvalidException("User password must not be null");
        }
        AppUser updatedUser = appUserMapper.toAppUserWithEncoded(appUser, requestDto);
        return appUserMapper.toAppUserResponseDTO(appUserRepository.save(updatedUser));
    }

    private void validateUser(AppUserRequestDTO requestDto) {
        if (appUserRepository.existsByName(requestDto.getName())) {
            throw new EntityAlreadyExistsException("App User already exists by name: " + requestDto.getName());
        }
        if (requestDto.getPassword().isEmpty() || requestDto.getPassword().isBlank()) {
            throw new PropertyInvalidException("User password must not be null");
        }
    }

//    public List<AppUser> findAllAuthors() {
//        return appUserRepository.findAllByRoles(List.of(AppUserRole.ADMIN, AppUserRole.CONTENT_CREATOR));
//    }
//
//    public Page<AppUser> findAllUsersSortedByRole(int page, int size) {
//        Pageable pageable = PageRequest.of(page, size);
//        return appUserRepository.findAllByOrderByRole(pageable);
//    }
//
//    @Transactional
//    public void updateLastLogin(String email) {
//        appUserRepository.updateLastLoginByEmail(LocalDateTime.now(), email);
//    }
}
