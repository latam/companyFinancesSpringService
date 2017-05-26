package pl.mlata.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.mlata.configuration.security.authentication.JwtTokenAuthentication;
import pl.mlata.persistance.model.User;
import pl.mlata.persistance.repository.UserRepository;
import pl.mlata.dto.RegistrationDTO;

import java.util.Optional;

/**
 * Created by Mateusz on 04.04.2017.
 */

@Service
public class UserService {
    private final Logger log = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;


    public User getByUsername(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        return user.orElseThrow(() -> new UsernameNotFoundException("User '" + username + "' not found."));
    }

    @Transactional
    public void registerNewAccount(RegistrationDTO registrationData) {
        User user = new User(registrationData);

        String encodedPassword = passwordEncoder.encode(registrationData.getPassword());
        user.setPassword(encodedPassword);

        user = userRepository.save(user);
    }

    public User getCurrentUser() {
        JwtTokenAuthentication userAuth = (JwtTokenAuthentication) SecurityContextHolder.getContext().getAuthentication();
        User user = (User) userAuth.getPrincipal();

        return user;
    }
}
