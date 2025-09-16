package com.gamestudio.user;

import com.gamestudio.user.entity.User;
import com.gamestudio.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
public class UserServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void testCreateUser() {
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("testpassword");

        User savedUser = userRepository.save(user);

        assertThat(savedUser.getId()).isNotNull();
        assertThat(savedUser.getUsername()).isEqualTo("testuser");
    }
}