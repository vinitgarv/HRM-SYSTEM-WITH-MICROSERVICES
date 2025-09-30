package in.metalab.respository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import in.metalab.entity.User;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

	Optional<User> findByIdAndIsDeletedFalseAndIsActiveTrue(String id);

	Optional<User> findByIdAndIsDeletedFalse(String id);

	boolean existsByEmailAndIsDeletedFalseAndIsActiveTrue(String email);

	boolean existsByMobileNoAndIsDeletedFalseAndIsActiveTrue(String mobileNo);

	Optional<User> findByEmailAndIsDeletedFalseAndIsActiveTrue(String email);
	
	Optional<User> findByMobileNoAndIsDeletedFalseAndIsActiveTrue(String mobileNo);
}
