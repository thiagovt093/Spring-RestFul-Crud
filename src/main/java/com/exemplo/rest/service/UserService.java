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

    {
        mapper.getConfiguration().setSkipNullEnabled(true);  // --> Skip Objetos nulos
    }
    private final BCryptPasswordEncoder bCrypt = new BCryptPasswordEncoder();
    @Autowired
    private UserRepository userRepository;

    public List<UserResponse> listarUsers() {
        return userRepository.findAll()
                .stream()
                .map(u -> mapper.map(u, UserResponse.class))
                .toList();
    }

    public UserResponse salvar(UserDTO data) {
        UserModel user = mapper.map(data, UserModel.class);
        user.setPassword(bCrypt.encode(data.getPassword())); // ✅ antes do save
        return mapper.map(userRepository.save(user), UserResponse.class);
    }

    public UserResponse atualizar(Long id, UserDTO data){
        UserModel userModel = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        mapper.map(data, userModel);

        if(data.getPassword() != null)  userModel.setPassword(new BCryptPasswordEncoder().encode(data.getPassword()));

        return mapper.map(userRepository.save(userModel), UserResponse.class);
    }

    public void deletar(Long id) {
        userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        userRepository.deleteById(id);
    }
    public UserResponse buscarID(Long id){
        UserModel user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        return mapper.map(user, UserResponse.class);
    }
}
