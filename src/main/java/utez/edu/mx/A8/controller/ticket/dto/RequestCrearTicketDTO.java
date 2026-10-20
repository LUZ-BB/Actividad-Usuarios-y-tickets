package utez.edu.mx.A8.controller.ticket.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RequestCrearTicketDTO {

    @NotBlank(message = "La descripcion no puede estar vacia")
    private String descripcion;

    @NotBlank(message = "La prioridad es obligatoria (BAJA, MEDIA, ALTA)")
    private String prioridad;

    @NotNull(message = "El id del usuario es obligatorio")
    private Long usuarioId;
}