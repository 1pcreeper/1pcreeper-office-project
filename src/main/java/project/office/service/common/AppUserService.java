package project.office.service.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import project.office.mapper.AppUserMapper;
import project.office.mapper.PaginationMapper;
import project.office.model.dto.base.response.PaginationBaseResponseDTO;
import project.office.model.dto.objects.AppUserDTO;
import project.office.model.dto.response.AppUserPrincipalResponseDTO;
import project.office.model.dto.response.AppUserResponseDTO;
import project.office.model.entity.AppUser;
import project.office.model.exception.ResourceNotFoundException;
import project.office.service.manager.AppUserManagerService;

@Service
public class AppUserService{
    private final AppUserManagerService appUserManagerService;
    private final AppUserMapper appUserMapper;
    private final PaginationMapper paginationMapper;
    @Autowired
    public AppUserService(
            AppUserManagerService appUserManagerService,
            AppUserMapper appUserMapper,
            PaginationMapper paginationMapper
    ){
        this.appUserManagerService = appUserManagerService;
        this.appUserMapper = appUserMapper;
        this.paginationMapper = paginationMapper;
    }

    public PaginationBaseResponseDTO<AppUserResponseDTO> findByNameContaining(String partialName, Pageable pageable) {
        Page<AppUser> entities =  appUserManagerService.findByNameContaining(partialName,pageable);
        return paginationMapper.toDTO(entities, appUserMapper::toAppUserResponseDTO);
    }

    public AppUserPrincipalResponseDTO findMyself(AppUser appUser){
        return AppUserPrincipalResponseDTO.builder()
                .user(appUserMapper.toAppUserResponseDTO(appUser))
                .roles(appUser.getRoles())
                .build();
    }

    public AppUserDTO findByName(String name){
        AppUser existingAppUser =  appUserManagerService.findByName(name).orElseThrow(()->new ResourceNotFoundException("User Name Not Found"));
        return appUserMapper.toAppUserDTO(existingAppUser);
    }
}
