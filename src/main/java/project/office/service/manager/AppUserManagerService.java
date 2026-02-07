package project.office.service.manager;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import project.office.model.entity.AppUser;
import project.office.repository.AppUserRepository;
import project.office.service.base.AbstractBaseService;

import java.util.List;
import java.util.Optional;

@Service
public class AppUserManagerService extends AbstractBaseService<AppUser, Long> {
    private final AppUserRepository appUserRepository;
    public AppUserManagerService(AppUserRepository appUserRepository){
        super(appUserRepository,"AppUser");
        this.appUserRepository = appUserRepository;
    }
    public Optional<AppUser> findByName(String name){
        return appUserRepository.findByName(name);
    }
    public boolean existsByName(String name){
        return appUserRepository.existsByName(name);
    }
    public Page<AppUser> findAllByPage(Pageable pageable){
        return appUserRepository.findAllByPage(pageable);
    }
    public Page<AppUser> findByNameContaining(@Param("partialName") String partialName, Pageable pageable){
        return appUserRepository.findByNameContaining(partialName,pageable);
    }
    public List<AppUser> findByNameContaining(@Param("partialName") String partialName){
        return appUserRepository.findByNameContaining(partialName);
    }
}
