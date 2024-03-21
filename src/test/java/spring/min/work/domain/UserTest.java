package spring.min.work.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import spring.min.work.repository.UserRepository;
import spring.min.work.service.UserService;
import spring.min.work.service.UserServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class UserTest {
    @InjectMocks
    private UserServiceImpl userService;
    @Mock
    private UserRepository userRepository;

    @Test
    public void testUser() {
        User user = new User("username", "email", "password",
                true, "phone","address");
        user.setId(1L);
        user.setActive(true);
        when(userRepository.findByUsername("username")).thenReturn(user);
        assertEquals(userRepository.findByUsername("username").getId(), 1);
        assertEquals(userRepository.findByUsername("username").getUsername(), "username");
        assertEquals(userRepository.findByUsername("username").getEmail(), "email");
        assertEquals(userRepository.findByUsername("username").getPassword(), "password");
        assertEquals(userRepository.findByUsername("username").getPhone(), "phone");
        assertEquals(userRepository.findByUsername("username").getAddress(), "address");
        assertEquals(userRepository.findByUsername("username").isActive(), true);
    }
    @Test
    public void testUserConstructor() {
        User user = new User("username", "password");
        when(userRepository.findByUsername("username")).thenReturn(user);
        assertEquals(userRepository.findByUsername("username").getUsername(), "username");
        assertEquals(userRepository.findByUsername("username").getPassword(), "password");
    }

    @Test
    public void testListEstimateToUser(){
        List<Estimate> estimateList = new ArrayList<>(List.of(new Estimate(), new Estimate()));
        User user = new User();
        user.setEstimates(estimateList);
        assertEquals(user.getEstimates().size(), 2);

    }

}