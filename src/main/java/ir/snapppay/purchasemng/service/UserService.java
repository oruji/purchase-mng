package ir.snapppay.purchasemng.service;

import ir.snapppay.purchasemng.dto.UserAddRequest;
import ir.snapppay.purchasemng.service.model.UserModel;

import org.springframework.http.ResponseEntity;

public interface UserService {

	UserModel save(UserModel model);

	boolean findByUsername(String username);

}
