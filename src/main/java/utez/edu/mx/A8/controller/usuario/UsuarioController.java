package utez.edu.mx.A8.controller.usuario;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utez.edu.mx.A8.controller.usuario.dto.RequestCrearUsuarioDTO;
import utez.edu.mx.A8.model.usuario.Usuario;
import utez.edu.mx.A8.service.usuario.UsuarioService;

import java.util.List;

@RestController
@RequestMapping("/usuario")
@CrossOrigin({"*"})
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> getAll() {
        return ResponseEntity.ok(usuarioService.getAll());
    }

    @PostMapping
    public ResponseEntity<Usuario> crearUsuario(@RequestBody @Valid RequestCrearUsuarioDTO payload) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(usuarioService.crearUsuario(payload));
    }

    @PatchMapping("/{id}/desactivar")
    public ResponseEntity<Usuario> desactivarUsuario(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.desactivarUsuario(id));
    }
}