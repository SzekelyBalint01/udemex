package udemx.service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import udemx.model.User;
import udemx.repository.UserRepository;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserServiceTest {

    @MockitoBean
    private UserRepository userRepository;

    @Autowired
    private UserService userService;
/*
    @Test
    public void testSaveUser() {

        User user = User.builder()
                .name("John Doe")
                .email("john.doe@example.com")
                .address("123 Main St")
                .phone("123-456-7890")
                .build();


        when(userRepository.save(user)).thenReturn(user);


        User savedUser = userService.saveUser(user);

        assertNotNull(savedUser);
        assertEquals("John Doe", savedUser.getName());
        assertEquals("john.doe@example.com", savedUser.getEmail());
        assertEquals("123 Main St", savedUser.getAddress());
        assertEquals("123-456-7890", savedUser.getPhone());

        verify(userRepository, times(1)).save(user);
    }
    */
}
