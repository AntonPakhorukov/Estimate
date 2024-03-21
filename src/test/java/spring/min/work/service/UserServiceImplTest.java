package spring.min.work.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import spring.min.work.domain.User;
import spring.min.work.repository.EstimateRepository;
import spring.min.work.repository.UserRepository;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class UserServiceImplTest {
    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;
    @Test
    void testGetAll() {
        User user = new User();
        List<User> list = Collections.singletonList(user);
        when(userRepository.findAll()).thenReturn(list);
        List<User> expected = userService.getAll();
        assertEquals(list, expected);
    }
    @Test
    void testGetUserByName() {
        User user = new User();
        user.setUsername("user1");
        when(userRepository.findByUsername(user.getUsername())).thenReturn(user);
        assertEquals(userService.getUserByName("user1"), userRepository.findByUsername("user1"));
    }
}