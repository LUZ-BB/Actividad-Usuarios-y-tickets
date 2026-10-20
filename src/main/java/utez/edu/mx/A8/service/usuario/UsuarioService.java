package utez.edu.mx.A8.service.usuario;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import utez.edu.mx.A8.controller.usuario.dto.RequestCrearUsuarioDTO;
import utez.edu.mx.A8.exception.BadRequestExeption;
import utez.edu.mx.A8.model.usuario.Usuario;
import utez.edu.mx.A8.repository.usuario.UsuarioRepository;

import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public List<Usuario> getAll() {
        return usuarioRepository.findAll();
    }

    @Transactional
    public Usuario crearUsuario(RequestCrearUsuarioDTO payload) {
        Usuario usuario = new Usuario();
        usuario.setNombre(payload.getNombre());
        usuario.setActivo(true);
        return usuarioRepository.save(usuario);
    }

    @Transactional
    public Usuario desactivarUsuario(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new BadRequestExeption("El usuario no existe"));

        usuario.setActivo(false);
        return usuarioRepository.save(usuario);
    }
}