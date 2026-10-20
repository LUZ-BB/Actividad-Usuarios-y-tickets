package utez.edu.mx.A8.controller.ticket.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RequestReasignarPrioridadDTO {

    @NotBlank(message = "La nueva prioridad es obligatoria (BAJA, MEDIA, ALTA)")
    private String prioridad;
}