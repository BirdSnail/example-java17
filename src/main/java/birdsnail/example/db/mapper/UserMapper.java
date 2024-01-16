package birdsnail.example.db.mapper;

import birdsnail.example.base.User;

public interface UserMapper {

    User selectByName(String name);

    int insert(User user);
}
