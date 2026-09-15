package io.jamdyer.trading.portfolio.user;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.UserDetailsService;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }

    public String create(String username, String password) {
        User user = User.builder()
        .username(username)
        .password(new BCryptPasswordEncoder().encode(password))
        .authorities("basic")
        .build();

        userRepository.save(user);
        return "Created user with username: " + username;
    }


}
