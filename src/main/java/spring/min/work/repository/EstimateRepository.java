package spring.min.work.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.min.work.domain.Estimate;

public interface EstimateRepository extends JpaRepository<Estimate, Long> {
}
