package pw.pap.api.model;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pw.pap.repository.UserRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
class UserTests {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testSaveRetrieveDeleteUser() {
        User user = new User("Bob");

        User savedUser = userRepository.save(user);

        User retrievedUser = userRepository.findById(savedUser.getId()).orElse(null);
        assertNotNull(retrievedUser);
        assertEquals("Bob", retrievedUser.getName());

        userRepository.deleteById(retrievedUser.getId());
        retrievedUser = userRepository.findById(savedUser.getId()).orElse(null);
        assertNull(retrievedUser);
    }
}