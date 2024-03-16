package spring.min.work.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.min.work.domain.Estimate;
import spring.min.work.repository.EstimateRepository;

import java.util.List;

@Service
public class EstimateServiceImpl implements EstimateService {
    private final EstimateRepository estimateRepository;

    @Autowired
    public EstimateServiceImpl(EstimateRepository estimateRepository) {
        this.estimateRepository = estimateRepository;
    }

    @Override
    public Estimate createEstimate(Estimate estimate) {
        return estimateRepository.save(estimate);
    }

    @Override
    public Estimate getEstimateById(Integer id) {
        return estimateRepository.findById(Long.valueOf(id))
                .orElseThrow(() -> new RuntimeException("Estimate not found"));
    }

    @Override
    public List<Estimate> getAll() {
        return estimateRepository.findAll();
    }

    @Override
    public void deleteEstimateById(Integer id) {
        estimateRepository.deleteById(Long.valueOf(id));
    }
}
