package dev.asteri.pasteva.service;

import dev.asteri.pasteva.model.User;
import dev.asteri.pasteva.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Primary
@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository repository;

    public User saveUser(User user) {
        return repository.save(user);
    }

    public Optional<User> findByUsername(String username) {
        return repository.findByUsername(username);
    }

    public boolean existsByUsername(String username) {
        return repository.existsByUsername(username);
    }

    public boolean existsById(UUID id) {
        return repository.existsById(id);
    }

    public Optional<User> findById(UUID id) {
        return repository.findById(id);
    }

    public void deleteUser(UUID id) {
        repository.deleteById(id);
    }

    public UserDetails loadUserByUsername(String username) {
        var user = repository.findByUsername(username);
        return user.orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));
    }
}
