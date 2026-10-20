package utez.edu.mx.A8.controller.usuario.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RequestCrearUsuarioDTO {

    @NotBlank(message = "El nombre del usuario es obligatorio")
    private String nombre;
}