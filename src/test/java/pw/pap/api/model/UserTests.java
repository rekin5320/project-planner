package pw.pap.api.model;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pw.pap.repository.UserRepository;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserTests {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testEmptyConstructor() {
        User user = new User();
        assertNotNull(user.getAccountCreationDate());
    }

    @Test
    public void testNotEmptyConstructor() {
        User user = new User("Rob", "abcd", "dcba");
        assertNotNull(user.getAccountCreationDate());
        assertEquals("Rob", user.getName());
        assertEquals("abcd", user.getPasswordHash());
        assertEquals("dcba", user.getSalt());
    }

    @Test
    public void testAccountCreationDateEmptyConstructor() {
        Date dateBeforeCreation = new Date();
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        User user = new User();
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Date dateAfterCreation = new Date();
        assertNotNull(user.getAccountCreationDate());
        assertTrue(user.getAccountCreationDate().after(dateBeforeCreation));
        assertTrue(user.getAccountCreationDate().before(dateAfterCreation));
    }

    @Test
    public void testAccountCreationDateNotEmptyConstructor() {
        Date dateBeforeCreation = new Date();
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        User user = new User("Rob", "abcd", "dcba");
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Date dateAfterCreation = new Date();
        assertNotNull(user.getAccountCreationDate());
        assertTrue(user.getAccountCreationDate().after(dateBeforeCreation));
        assertTrue(user.getAccountCreationDate().before(dateAfterCreation));
    }

    @Test
    public void testSaveRetrieveDeleteUser() {
        User user = new User("Bob", "abcd", "dcba");
        User savedUser = userRepository.save(user);

        User retrievedUser = userRepository.findById(savedUser.getId()).orElse(null);
        assertNotNull(retrievedUser);
        assertEquals("Bob", retrievedUser.getName());
        assertEquals("abcd", retrievedUser.getPasswordHash());
        assertEquals("dcba", retrievedUser.getSalt());
        assertNotNull(retrievedUser.getAccountCreationDate());

        userRepository.deleteById(retrievedUser.getId());
        retrievedUser = userRepository.findById(savedUser.getId()).orElse(null);
        assertNull(retrievedUser);
    }
}