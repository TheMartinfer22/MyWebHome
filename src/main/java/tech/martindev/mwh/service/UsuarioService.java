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
     * Recebe o parâmetro da classe UsuarioRestController e verifica se o usuário informado já existe,
     * caso existir irá fornecer uma exceção
     *
     * @param usuarioEntity
     */
    public void createAccount(UsuarioEntity usuarioEntity) throws IllegalAccessException{
        boolean userAleartyExist = usuarioRepository
                .findByUsername(usuarioEntity.getUsername())
                .stream().anyMatch(e -> e.getUsername()
                        .equals(usuarioEntity.getUsername()));

        if (userAleartyExist){
            throw new IllegalAccessException("O usuário já existe.");
        }

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
