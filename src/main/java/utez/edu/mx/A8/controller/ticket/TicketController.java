package utez.edu.mx.A8.controller.ticket;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utez.edu.mx.A8.controller.ticket.dto.RequestCrearTicketDTO;
import utez.edu.mx.A8.controller.ticket.dto.RequestReasignarPrioridadDTO;
import utez.edu.mx.A8.model.ticket.Ticket;
import utez.edu.mx.A8.service.ticket.TicketService;

import java.util.List;

@RestController
@RequestMapping("/ticket")
@CrossOrigin({"*"})
public class TicketController {

    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @PostMapping
    public ResponseEntity<Ticket> crearTicket(@RequestBody @Valid RequestCrearTicketDTO payload) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ticketService.crearTicket(payload));
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<Ticket> actualizarEstadoTicket(@PathVariable Long id) {
        return ResponseEntity.ok(ticketService.actualizarEstado(id));
    }

    @PatchMapping("/{id}/prioridad")
    public ResponseEntity<Ticket> reasignarPrioridad(
            @PathVariable Long id,
            @RequestBody @Valid RequestReasignarPrioridadDTO payload) {
        return ResponseEntity.ok(ticketService.reasignarPrioridad(id, payload));
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Ticket>> obtenerTicketsUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(ticketService.obtenerTicketsUsuario(usuarioId));
    }
}