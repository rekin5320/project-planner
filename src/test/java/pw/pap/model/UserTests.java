package pw.pap.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import pw.pap.repository.UserRepository;


@SpringBootTest
class UserTests {
    @Autowired
    private UserRepository userRepository;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private final LocalDateTime date = LocalDateTime.parse("2023-10-11 13:37:42", formatter);
    private final LocalDateTime date2 = LocalDateTime.parse("2023-10-11 13:51:53", formatter);

    @Test
    public void testEmptyConstructor() {
        User user = new User();
        assertThrows(DataIntegrityViolationException.class, () -> userRepository.save(user));
    }

    @Test
    public void testNameConstructor() {
        User user = new User("Tom", date);
        assertEquals("Tom", user.getName());
        assertEquals(date, user.getAccountCreationDate());
    }

    @Test
    public void testFullConstructor() {
        User user = new User("Rob", "abcd", "dcba", date);
        assertEquals("Rob", user.getName());
        assertEquals("abcd", user.getPasswordHash());
        assertEquals("dcba", user.getSalt());
        assertEquals(date, user.getAccountCreationDate());
    }

    @Test
    @Transactional
    @Rollback
    public void testSaveRetrieveDeleteUser() {
        User user = new User("Bob", "abcd", "dcba", date);
        User savedUser = userRepository.save(user);
        User retrievedUser = userRepository
            .findById(savedUser.getId())
            .orElse(null);

        assertNotNull(retrievedUser);
        assertEquals("Bob", retrievedUser.getName());
        assertEquals("abcd", retrievedUser.getPasswordHash());
        assertEquals("dcba", retrievedUser.getSalt());
        assertEquals(date, user.getAccountCreationDate());

        userRepository.deleteById(retrievedUser.getId());
        retrievedUser = userRepository
            .findById(savedUser.getId())
            .orElse(null);
        assertNull(retrievedUser);
    }

    // TODO: probably it should not be possible to change accountCreationDate, updatable=false
    /* @Test
    @Transactional
    @Rollback
    public void testAccountCreationDateNotUpdatable() {
        User user = new User();
        user.setName("Dave");
        user.setAccountCreationDate(date);
        userRepository.save(user);

        user.setAccountCreationDate(date2);
        userRepository.save(user);

        // maybe it should throw an error
        // assertThrows(DataIntegrityViolationException.class, () -> userRepository.save(user));

        // if above doesn't throw error, maybe it should not really be saved to database
        User retrievedUser = userRepository.findById(user.getId()).orElse(null);
        assertEquals(date, retrievedUser.getAccountCreationDate());
    } */
}
