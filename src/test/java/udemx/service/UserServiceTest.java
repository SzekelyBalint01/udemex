package udemx.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import udemx.model.User;
import udemx.repository.UserRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class UserServiceTest {

    private final UserRepository userRepository = mock(UserRepository.class);
    private final UserService userService = new UserService(userRepository);

    @Test
    void testSaveUser() {

        User user = User.builder()
                .id(1L)
                .name("John Doe")
                .email("john.doe@example.com")
                .build();
        when(userRepository.save(Mockito.any(User.class))).thenReturn(user);

        User savedUser = userService.saveUser(user);

        assertEquals(user, savedUser, "Saved user should match the returned user from repository");
        verify(userRepository, times(1)).save(user);
    }
}
