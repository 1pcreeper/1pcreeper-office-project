package project.office.service.base;


import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import project.office.model.exception.ResourceNotFoundException;
import project.office.repository.base.AbstractBaseRepository;

import java.util.List;

/**
 * AbstractBaseService provides common data access methods for entities.
 * <p>
 * This class is designed to be extended by specific service implementations
 * (e.g., AppUserDetailsService, PastActivityService) to eliminate repetitive boilerplate code.
 * It does not use @Transactional at the class level for the following reasons:
 * <ul>
 *   <li>Transaction boundaries are expected to be managed by the subclass methods.</li>
 *   <li>Subclasses can define custom transactional behavior, such as specific
 *       propagation or isolation levels, tailored to their business requirements.</li>
 * </ul>
 * This approach ensures flexibility and allows transactional behavior to align
 * with the context of the calling service methods.
 * </p>
 */
public abstract class AbstractBaseService<T, ID> {
    protected AbstractBaseRepository<T, ID> repository;

    private final String entityName;

    protected AbstractBaseService(AbstractBaseRepository<T, ID> repository,
                                  String entityName) {
        this.repository = repository;
        this.entityName = entityName;
    }

    public List<T> findAll() {
        return repository.findAll();
    }

    public Page<T> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public T findById(ID id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("%s not found by id : %s", entityName, id)));
    }

    public List<T> findAllById(List<ID> ids) {
        return repository.findAllById(ids);
    }

    public T getReferenceById(ID id) {
        try {
            return repository.getReferenceById(id);
        } catch (EntityNotFoundException ex) {
            throw new ResourceNotFoundException(String.format(
                    "%s not found by id : %s | %s", entityName, id, ex.getMessage()));
        }
    }

    public T save(T entity) {
        return repository.save(entity);
    }

    public List<T> saveAll(Iterable<T> entities) {
        return repository.saveAll(entities);
    }

    public void deleteById(ID id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException(
                    String.format("%s not found by id : %s", entityName, id));
        }
        repository.deleteById(id);
    }

    public void delete(T entity) {
        repository.delete(entity);
    }

    public void deleteAll(List<T> entities) {
        repository.deleteAll(entities);
    }

    public long count() {
        return repository.count();
    }

    public void deleteAllByIds(List<ID> ids) {
        List<T> entities = repository.findAllById(ids);
        if (entities.size() != ids.size()) {
            throw new ResourceNotFoundException(
                    String.format("Some %s not found for the provided IDs.", entityName)
            );
        }
        repository.deleteAll(entities);
    }
}

