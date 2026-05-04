package com.exemplo.rest.service;

import com.exemplo.rest.dto.UserDTO;
import com.exemplo.rest.exception.UserNotFoundException;
import com.exemplo.rest.model.UserModel;
import com.exemplo.rest.dto.UserResponse;
import com.exemplo.rest.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final ModelMapper mapper = new ModelMapper();
    private final BCryptPasswordEncoder bCrypt = new BCryptPasswordEncoder();
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
        userModel.setPassword(bCrypt.encode(user.getPassword()));
        return mapper.map(repo, UserResponse.class);
    }

    public UserResponse buscarID(Long id){
        UserModel user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        return mapper.map(user, UserResponse.class);
    }
}
