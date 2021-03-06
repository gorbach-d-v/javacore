package ru.yusdm.javacore.lesson9genericsbegin.autoservice.user.repo.impl.memory;

import ru.yusdm.javacore.lesson9genericsbegin.autoservice.storage.SequenceGenerator;
import ru.yusdm.javacore.lesson9genericsbegin.autoservice.user.domain.User;
import ru.yusdm.javacore.lesson9genericsbegin.autoservice.user.repo.UserRepo;
import ru.yusdm.javacore.lesson9genericsbegin.autoservice.user.search.UserSearchCondition;

import java.util.Collections;
import java.util.List;

import static ru.yusdm.javacore.lesson9genericsbegin.autoservice.storage.Storage.usersList;


public class UserCollectionRepo implements UserRepo {

    @Override
    public void insert(User user) {
        user.setId(SequenceGenerator.getNextValue());
        usersList.add(user);
    }

    @Override
    public void update(User user) {
        //we already in memory, no need to update object
    }

    @Override
    public User findById(Long id) {
        return findUserById(id);
    }

    @Override
    public List<User> search(UserSearchCondition searchCondition) {
        return Collections.emptyList();
    }

    @Override
    public void deleteById(Long id) {
        User found = findUserById(id);

        if (found != null) {
            usersList.remove(found);
        }
    }

    @Override
    public void printAll() {
        for (User user : usersList) {
            System.out.println(user);
        }
    }

    private User findUserById(long userId) {
        for (User user : usersList) {
            if (Long.valueOf(userId).equals(user.getId())) {
                return user;
            }
        }
        return null;
    }
}