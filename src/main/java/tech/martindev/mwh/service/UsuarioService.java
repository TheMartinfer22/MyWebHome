package tech.martindev.mwh.service;


import org.springframework.stereotype.Service;
import tech.martindev.mwh.entities.UsuarioEntity;
import tech.martindev.mwh.repositories.UsuarioRepository;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

@Service
public class UsuarioService {

    final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    /**
     * Recebe o parâmetro da classe UsuarioRestController
     *
     * @param usuarioEntity
     */
    public void createUser(UsuarioEntity usuarioEntity){
        usuarioEntity.setRegister_At(LocalDateTime.now());
        usuarioRepository.save(usuarioEntity);
    }

    /**
     * Está recebendo a ID que está sendo passada na classe do controlador.
     *
     * @param id
     * @return
     */
    public UsuarioEntity listAccounts(Long id){
        return usuarioRepository.findById(id)
                .stream()
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Não foi possível encontrar o usuário"));
    }
}
