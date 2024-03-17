package spring.min.work.service;

import spring.min.work.domain.Estimate;

import java.util.List;

public interface EstimateService {
    Estimate createEstimate(Estimate estimate);

    Estimate getEstimateById(Integer id);

    List<Estimate> getAll();

    void deleteEstimateById(Integer id);

    List<Estimate> getTotal(List<Estimate> allEstimate);

    void deleteAll();
}
