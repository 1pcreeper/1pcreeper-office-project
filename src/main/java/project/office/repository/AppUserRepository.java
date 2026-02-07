package project.office.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import project.office.model.entity.AppUser;
import project.office.repository.base.AbstractBaseRepository;

import java.util.List;
import java.util.Optional;

public interface AppUserRepository extends AbstractBaseRepository<AppUser, Long> {
    Optional<AppUser> findByName(String name);
    boolean existsByName(String name);
    @Query("SELECT u FROM AppUser u")
    Page<AppUser> findAllByPage(Pageable pageable);
    @Query("SELECT u FROM AppUser u WHERE u.name LIKE %:partialName%")
    Page<AppUser> findByNameContaining(@Param("partialName") String partialName,Pageable pageable);
    @Query("SELECT u FROM AppUser u WHERE u.name LIKE %:partialName%")
    List<AppUser> findByNameContaining(@Param("partialName") String partialName);
}