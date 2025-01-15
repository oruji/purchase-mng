package io.github.oruji.purchasemng.service.user;

import io.github.oruji.purchasemng.entity.user.User;
import io.github.oruji.purchasemng.service.user.model.UserModel;

public interface UserService {

	UserModel save(UserModel model);

	User findByUsername(String username);
}
