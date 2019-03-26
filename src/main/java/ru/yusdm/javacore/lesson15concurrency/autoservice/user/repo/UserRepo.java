package ru.yusdm.javacore.lesson15concurrency.autoservice.user.repo;

import ru.yusdm.javacore.lesson15concurrency.autoservice.common.solutions.repo.BaseRepo;
import ru.yusdm.javacore.lesson15concurrency.autoservice.user.domain.User;
import ru.yusdm.javacore.lesson15concurrency.autoservice.user.search.UserSearchCondition;

import java.util.List;

public interface UserRepo extends BaseRepo<User, Long> {
    List<? extends User> search(UserSearchCondition searchCondition);
}
