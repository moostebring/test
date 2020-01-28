package nl.quintor.app.repository;

import nl.quintor.app.model.RoleModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleModelRepository extends CrudRepository<RoleModel, Integer> {
    RoleModel findByName(String role);
}
