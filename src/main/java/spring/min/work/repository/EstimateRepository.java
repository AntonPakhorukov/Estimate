package spring.min.work.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring.min.work.domain.Estimate;

@Repository
public interface EstimateRepository extends JpaRepository<Estimate, Long> {
}
