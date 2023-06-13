package kr.ac.jejunu.studyplanner.User;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public User create(String username, String email, String password){
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        this.userRepository.save(user);
        return user;
    }
    public User findByUsername(String username){
        return userRepository.findByUsername(username);
    }
    public User getByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
