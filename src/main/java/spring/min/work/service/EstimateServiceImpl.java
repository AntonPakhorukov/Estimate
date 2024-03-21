package spring.min.work.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.min.work.domain.Estimate;
import spring.min.work.repository.EstimateRepository;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    @Override
    public List<Estimate> getTotal(List<Estimate> allEstimate) {
        Map<String, Estimate> getEstimate = allEstimate.stream().collect(Collectors
                .toMap(estimate -> estimate.getRoom() + " " + estimate.getCategory(),
                        estimate -> estimate, (existing, toAdd) ->
                                new Estimate(existing.getRoom(), existing.getCategory(),
                                        String.valueOf(Double.valueOf(existing.getSum())
                                                + Double.valueOf(toAdd.getSum())))));
        List<Estimate> result = getEstimate.values().stream()
                .sorted(Comparator.comparing(Estimate::getRoom)).collect(Collectors.toList());
        return result;
    }
    public void deleteAll(String name){
        estimateRepository.deleteAll();
    }
}
