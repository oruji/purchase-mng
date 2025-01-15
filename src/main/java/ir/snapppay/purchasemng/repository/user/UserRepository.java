package ir.snapppay.purchasemng.repository.user;

import java.util.Optional;

import ir.snapppay.purchasemng.entity.user.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByUsername(String username);

}
