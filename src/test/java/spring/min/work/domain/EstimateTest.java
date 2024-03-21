package spring.min.work.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import spring.min.work.repository.EstimateRepository;
import spring.min.work.service.EstimateServiceImpl;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class EstimateTest {
    @InjectMocks
    private EstimateServiceImpl estimateService;

    @Mock
    private EstimateRepository estimateRepository;

    @Test
    public void testEstimate() {
        Estimate estimate = new Estimate("room", "category", "description",
                "manufacturer", "product", "5", "25");
        estimate.setId(1);
        when(estimateRepository.findById(1L)).thenReturn(Optional.of(estimate));
        assertEquals(estimateService.getEstimateById(1).getRoom(), "room");
        assertEquals(estimateService.getEstimateById(1).getCategory(), "category");
        assertEquals(estimateService.getEstimateById(1).getDescription(), "description");
        assertEquals(estimateService.getEstimateById(1).getManufacturer(), "manufacturer");
        assertEquals(estimateService.getEstimateById(1).getProduct(), "product");
        assertEquals(estimateService.getEstimateById(1).getQuantity(), "5");
        assertEquals(estimateService.getEstimateById(1).getPrice(), "25");
        assertEquals(estimateService.getEstimateById(1).getId(), 1);
    }
}