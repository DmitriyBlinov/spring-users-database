package com.dblinov.demospringapp;

import com.dblinov.demospringapp.user.User;
import com.dblinov.demospringapp.user.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class UserRepositoryTest {
    @Autowired private UserRepository repo;

    @Test
    public void testAddNewUser() {
        User user = new User();
        user.setEmail("myname.mylastname1@gmail.com");
        user.setPassword("testPassword123");
        user.setFirstName("Myname");
        user.setLastName("Mylastname");

        User savedUser = repo.save(user);

        Assertions.assertThat(savedUser).isNotNull();
        Assertions.assertThat(savedUser.getId()).isGreaterThan(0);
    }

    @Test
    public void testListAll() {
        Iterable<User> users = repo.findAll();
        Assertions.assertThat(users).hasSizeGreaterThan(0);

        for (User user : users) {
            System.out.println(user);
        }
    }

    @Test
    public void testUpdate() {
        Integer tempId = 1;
        Optional<User> optionalUser = repo.findById(tempId);
        User user = optionalUser.get();
        user.setPassword("newpass123");
        repo.save(user);

        User updateUser = repo.findById(tempId).get();
        Assertions.assertThat(updateUser.getPassword()).isEqualTo("newpass123");
    }

    @Test
    public void testGet() {
        Integer tempId = 1;
        Optional<User> optionalUser = repo.findById(tempId);
        Assertions.assertThat(optionalUser).isPresent();
        System.out.println(optionalUser.get());
    }

    @Test
    public void testDelete() {
        Integer tempId = 1;
        repo.deleteById(tempId);
        Optional<User> optionalUser = repo.findById(tempId);

        Assertions.assertThat(optionalUser).isNotPresent();
    }
}