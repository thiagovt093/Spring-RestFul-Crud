package com.exemplo.rest.service;

import com.exemplo.rest.dto.UserDTO;
import com.exemplo.rest.exception.UserNotFoundException;
import com.exemplo.rest.model.UserModel;
import com.exemplo.rest.model.UserResponse;
import com.exemplo.rest.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class UserService {

    private final ModelMapper mapper = new ModelMapper();

    @Autowired
    private UserRepository userRepository;

    public List<UserResponse> listarUsers() {
        return userRepository.findAll()
                .stream()
                .map(u -> mapper.map(u, UserResponse.class))
                .toList();
    }

    public UserResponse salvar(UserDTO user) {
        UserModel userModel = mapper.map(user, UserModel.class);
        UserModel repo = userRepository.save(userModel);
        return mapper.map(repo, UserResponse.class);
    }

    public UserResponse buscarID(Long id){
        UserModel user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        return mapper.map(user, UserResponse.class);
    }
}
