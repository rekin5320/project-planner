package pw.pap.api.model;

import java.util.Date;
import java.util.concurrent.TimeUnit;

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

    @Test
    public void testEmptyConstructor() {
        User user = new User();
        assertThrows(DataIntegrityViolationException.class, () -> userRepository.save(user));
    }

    @Test
    public void testNameConstructor() {
        Date currentDate = new Date();
        User user = new User("Tom", currentDate);
        assertEquals("Tom", user.getName());
        assertEquals(currentDate, user.getAccountCreationDate());
    }

    @Test
    public void testFullConstructor() {
        Date currentDate = new Date();
        User user = new User("Rob", "abcd", "dcba", currentDate);
        assertEquals("Rob", user.getName());
        assertEquals("abcd", user.getPasswordHash());
        assertEquals("dcba", user.getSalt());
        assertEquals(currentDate, user.getAccountCreationDate());
    }

    @Test
    @Transactional
    @Rollback
    public void testSaveRetrieveDeleteUser() {
        Date currentDate = new Date();
        User user = new User("Bob", "abcd", "dcba", currentDate);
        User savedUser = userRepository.save(user);
        User retrievedUser = userRepository
            .findById(savedUser.getId())
            .orElse(null);

        assertNotNull(retrievedUser);
        assertEquals("Bob", retrievedUser.getName());
        assertEquals("abcd", retrievedUser.getPasswordHash());
        assertEquals("dcba", retrievedUser.getSalt());
        assertEquals(currentDate, user.getAccountCreationDate());

        userRepository.deleteById(retrievedUser.getId());
        retrievedUser = userRepository
            .findById(savedUser.getId())
            .orElse(null);
        assertNull(retrievedUser);
    }

    // TODO: probably should fail, updatable=false
    /* @Test
    @Transactional
    @Rollback
    public void testAccountCreationDateNotUpdatable() {
        User user = new User();
        user.setName("Dave");
        Date oldDate = new Date();
        user.setAccountCreationDate(oldDate);
        userRepository.save(user);

        try {
            TimeUnit.SECONDS.sleep(2);
        } 
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        Date newDate = new Date();
        user.setAccountCreationDate(newDate);
        userRepository.save(user);

        // assertThrows(DataIntegrityViolationException.class, () -> userRepository.save(user));

        User retrievedUser = userRepository.findById(user.getId()).orElse(null);
        assertEquals(oldDate, retrievedUser.getAccountCreationDate());
    } */
}
