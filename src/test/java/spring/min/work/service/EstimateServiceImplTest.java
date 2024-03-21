package spring.min.work.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import spring.min.work.domain.Estimate;
import spring.min.work.repository.EstimateRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class EstimateServiceImplTest {
    @InjectMocks
    private EstimateServiceImpl estimateService;

    @Mock
    private EstimateRepository estimateRepository;

    @Test
    public void testCreateEstimate() {
        Estimate estimate = new Estimate();
        estimateService.createEstimate(estimate);
        Mockito.verify(estimateRepository, Mockito.times(1)).save(estimate);
    }

    @Test
    public void testGetEstimateById() {
        Estimate estimate = new Estimate();
        estimate.setId(1);
        estimate.setRoom("room");
        when(estimateRepository.findById(1L)).thenReturn(Optional.of(estimate));
        Estimate currentEstimate = estimateService.getEstimateById(1);
        assertEquals(estimate, currentEstimate);
    }

    @Test
    public void testGetAll() {
        Estimate estimate = new Estimate();
        List<Estimate> expected = Collections.singletonList(estimate);
        when(estimateRepository.findAll()).thenReturn(expected);
        List<Estimate> actual = estimateService.getAll();
        assertEquals(expected, actual);
    }

    @Test
    public void testDeleteEstimateById(){
        estimateService.deleteEstimateById(1);
        Mockito.verify(estimateRepository, Mockito.times(1)).deleteById(1L);
    }

    @Test
    public void testGetTotal(){
        Estimate estimate = new Estimate();
        estimate.setSum(String.valueOf(250));
        Estimate estimate1 = new Estimate();
        estimate1.setSum(String.valueOf(250));
        List<Estimate> list = new ArrayList<>(List.of(estimate, estimate1));
        assert (Double.parseDouble(estimateService.getTotal(list).get(0).getSum()) == 500);
    }

    @Test
    public void testDeleteAll(){
        estimateService.deleteAll("name");
        Mockito.verify(estimateRepository, Mockito.times(1)).deleteAll();
    }
}
