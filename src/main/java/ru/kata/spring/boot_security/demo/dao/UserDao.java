
package ru.kata.spring.boot_security.demo.dao;



import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserDao {

    void saveUser(User user);

    User getUserById(long id) ;

    void updateUser(User userUpdated);

    void removeUserById(long id);

    List<User> getAllUsers();

    User findByUsername(String username);
}

