package com.exemplo.rest.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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
    @NotNull
    private int idade;
}
