package ru.yusdm.javacore.lesson8setandcomparator.autoservice.user.repo;

import ru.yusdm.javacore.lesson8setandcomparator.autoservice.common.business.repo.BaseRepo;
import ru.yusdm.javacore.lesson8setandcomparator.autoservice.user.domain.User;
import ru.yusdm.javacore.lesson8setandcomparator.autoservice.user.search.UserSearchCondition;

import java.util.List;

public interface UserRepo extends BaseRepo {
    void add(User user);

    User findById(long id);

    List<User> search(UserSearchCondition searchCondition);

    void update(User user);
}
