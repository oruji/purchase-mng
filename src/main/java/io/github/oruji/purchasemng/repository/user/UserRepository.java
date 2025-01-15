package io.github.oruji.purchasemng.repository.user;

import java.util.Optional;

import io.github.oruji.purchasemng.entity.user.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByUsername(String username);

}
