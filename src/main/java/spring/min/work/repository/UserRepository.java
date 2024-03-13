package spring.min.work.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.min.work.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
