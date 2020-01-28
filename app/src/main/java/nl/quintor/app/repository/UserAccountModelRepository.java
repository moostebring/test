package nl.quintor.app.repository;

import nl.quintor.app.model.UserAccountModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAccountModelRepository extends CrudRepository<UserAccountModel, Integer> {
    UserAccountModel findByUsername(String username);
    UserAccountModel findById(Long id);
    UserAccountModel findByReferencedId(Long referencedId);
}
