package com.exemplo.rest.controller;

import com.exemplo.rest.dto.UserDTO;
import com.exemplo.rest.dto.UserResponse;
import com.exemplo.rest.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/list")
    public List<UserResponse> listar() {
        return userService.listarUsers();
    }

    @GetMapping("/list/{id}")
    public UserResponse buscar(@PathVariable Long id){
        return userService.buscarID(id);
    }

    @PutMapping("{id}")
    public UserResponse userCreate(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        return userService.atualizar(id, userDTO);
    }

    @DeleteMapping("{id}")
    public ResponseEntity userDelete(@PathVariable Long id){
        userService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}