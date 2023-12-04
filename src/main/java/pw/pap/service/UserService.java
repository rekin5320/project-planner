package pw.pap.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pw.pap.repository.UserRepository;
import pw.pap.api.model.User;
import java.util.List;
import java.util.Optional;
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers() {
        return (List<User>) userRepository.findAll();
    }

    public User getUserById(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }

    public User addUser(User user) {
        return userRepository.save(user);
    }

    public User updateUser(Long userId, User updatedUser) {
        User existingUser = userRepository.findById(userId).orElse(null);
        if (existingUser != null) {
            existingUser.setName(updatedUser.getName());


            return userRepository.save(existingUser);
        } else {
            return null;
        }
    }

    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }
}