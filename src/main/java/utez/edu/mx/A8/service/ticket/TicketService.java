package utez.edu.mx.A8.service.ticket;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import utez.edu.mx.A8.controller.ticket.dto.RequestCrearTicketDTO;
import utez.edu.mx.A8.controller.ticket.dto.RequestReasignarPrioridadDTO;
import utez.edu.mx.A8.exception.BadRequestExeption;
import utez.edu.mx.A8.model.ticket.EstadoTicket;
import utez.edu.mx.A8.model.ticket.PrioridadTicket;
import utez.edu.mx.A8.model.ticket.Ticket;
import utez.edu.mx.A8.model.usuario.Usuario;
import utez.edu.mx.A8.repository.ticket.TicketRepository;
import utez.edu.mx.A8.repository.usuario.UsuarioRepository;

import java.util.List;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;
    private final UsuarioRepository usuarioRepository;

    public TicketService(TicketRepository ticketRepository, UsuarioRepository usuarioRepository) {
        this.ticketRepository = ticketRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional
    public Ticket crearTicket(RequestCrearTicketDTO payload) {
        Usuario usuario = usuarioRepository.findById(payload.getUsuarioId())
                .orElseThrow(() -> new BadRequestExeption("El usuario no existe"));

        if (!usuario.isActivo()) {
            throw new BadRequestExeption("El usuario no esta activo, no puede crear tickets");
        }

        if (payload.getDescripcion() == null || payload.getDescripcion().trim().isEmpty()) {
            throw new BadRequestExeption("La descripcian del ticket no puede estar vacia");
        }

        PrioridadTicket prioridad;
        try {
            prioridad = PrioridadTicket.valueOf(payload.getPrioridad().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new BadRequestExeption("La prioridad ingresada no es valida (BAJA, MEDIA, ALTA)");
        }

        Ticket ticket = new Ticket();
        ticket.setDescripcion(payload.getDescripcion());
        ticket.setEstado(EstadoTicket.ABIERTO);
        ticket.setUsuario(usuario);
        ticket.setPrioridad(prioridad);

        return ticketRepository.save(ticket);
    }

    @Transactional
    public Ticket actualizarEstado(Long ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new BadRequestExeption("El ticket no existe"));

        if (ticket.getEstado() == EstadoTicket.ABIERTO) {
            ticket.setEstado(EstadoTicket.EN_PROCESO);
        } else if (ticket.getEstado() == EstadoTicket.EN_PROCESO) {
            ticket.setEstado(EstadoTicket.CERRADO);
        } else if (ticket.getEstado() == EstadoTicket.CERRADO) {
            throw new BadRequestExeption("El ticket ya esta CERRADO y no se puede modificar");
        }

        return ticketRepository.save(ticket);
    }

    @Transactional
    public Ticket reasignarPrioridad(Long ticketId, RequestReasignarPrioridadDTO payload) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new BadRequestExeption("El ticket no existe"));

        if (ticket.getEstado() == EstadoTicket.CERRADO) {
            throw new BadRequestExeption("No se puede cambiar la prioridad de un ticket CERRADO");
        }

        PrioridadTicket nuevaPrioridad;
        try {
            nuevaPrioridad = PrioridadTicket.valueOf(payload.getPrioridad().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new BadRequestExeption("La prioridad ingresada no es valida (BAJA, MEDIA, ALTA)");
        }

        ticket.setPrioridad(nuevaPrioridad);
        return ticketRepository.save(ticket);
    }

    public List<Ticket> obtenerTicketsUsuario(Long usuarioId) {
        if (!usuarioRepository.existsById(usuarioId)) {
            throw new BadRequestExeption("El usuario no existe");
        }
        return ticketRepository.findByUsuarioId(usuarioId);
    }
}