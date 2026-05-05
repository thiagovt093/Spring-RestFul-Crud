package com.exemplo.rest.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    @NotEmpty(message = "Nome Obrigatório")
    private String name;
    @Email(message = "Email inválido")
    @NotEmpty(message = "Email Obrigatório")
    private String email;
    @NotEmpty(message = "Senha obrigatória")
    @Size(min = 6, message = "A senha deve conter pelomenos 6 caracteres")
    private String password;
    @NotNull
    private Integer idade;
}
