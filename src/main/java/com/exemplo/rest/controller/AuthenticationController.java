package com.exemplo.rest.controller;

import com.exemplo.rest.dto.AuthenticationDTO;
import com.exemplo.rest.dto.LoginResponseDTO;
import com.exemplo.rest.dto.RegisterDTO;
import com.exemplo.rest.infra.security.TokenService;
import com.exemplo.rest.model.UserModel;
import com.exemplo.rest.repository.UserRepository;
import com.exemplo.rest.util.HashUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("auth")
public class AuthenticationController {

    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    UserRepository repository;
    @Autowired
    TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthenticationDTO data) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        var user = (UserModel) auth.getPrincipal();

        assert user != null;
        var acessToken = tokenService.generateToken(user);
        var refreshToken = tokenService.generateRefreshToken(user);

        user.setRefreshToken(HashUtil.hashToken(refreshToken));
        repository.save(user);
        return ResponseEntity.ok(new LoginResponseDTO(acessToken, refreshToken));
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid RegisterDTO data) {
        if(this.repository.findByEmail(data.email()) != null)
            return ResponseEntity.badRequest().build();

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        UserModel newUser = new UserModel(data.email(), encryptedPassword, data.role(), data.name(), data.idade());

        this.repository.save(newUser);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/refresh")
    public ResponseEntity refresh(HttpServletRequest request){
        var authReader = request.getHeader("Authorization");
        if(authReader == null || !authReader.startsWith("Bearer ")) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        var refreshToken = authReader.replace("Bearer ", "");

        var subject = tokenService.validateToken(refreshToken);
        if(subject.isEmpty()) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        UserModel user = (UserModel) repository.findByEmail(subject);
        if(user == null || user.getRefreshToken() == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        if(!HashUtil.hashToken(refreshToken).equals(user.getRefreshToken())) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        var newAcessToken = tokenService.generateToken(user);
        var newRefreshToken = tokenService.generateRefreshToken(user);

        user.setRefreshToken(newRefreshToken);
        repository.save(user);
        return ResponseEntity.ok(new LoginResponseDTO(newAcessToken, newRefreshToken));
    }
}
