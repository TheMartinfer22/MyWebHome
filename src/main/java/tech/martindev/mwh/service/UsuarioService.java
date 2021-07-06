package tech.martindev.mwh.service;


import org.springframework.stereotype.Service;
import tech.martindev.mwh.entities.UsuarioEntity;
import tech.martindev.mwh.exception.AccountAlreadyExistsException;
import tech.martindev.mwh.exception.AccountNotFoundException;
import tech.martindev.mwh.exception.AccountPasswordWrongException;
import tech.martindev.mwh.repositories.UsuarioRepository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.stream.Stream;

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
    public void createAccount(UsuarioEntity usuarioEntity) throws IllegalAccessException {
        boolean userAleartyExist = usuarioRepository
                .findByUsername(usuarioEntity.getUsername())
                .stream().anyMatch(e -> e.getUsername()
                        .equals(usuarioEntity.getUsername()));

        if (userAleartyExist) {
            throw new AccountAlreadyExistsException();
        }

        usuarioEntity.setRegister_At(LocalDateTime.now());
        usuarioRepository.save(usuarioEntity);
    }

    /**
     * Está recebendo a ID que está sendo passada na classe do controlador.
     * E retornando os dados do usuário.
     * <p>
     * TODO Criar DTO para proteger senha de usuário (Privacity Security)
     *
     * @param id
     * @return
     */
    public UsuarioEntity getById(Long id) {
        return usuarioRepository.findById(id)
                .stream()
                .findFirst()
                .orElseThrow(AccountNotFoundException::new);
    }

    /**
     * Está pedindo ao controller que envie uma string que corresponde
     * ao nome de usuário e então irá procurar
     * no database se aquele usuário existe, caso não existir irá
     * retornar uma exceção pernsonalizada.
     * <p>
     * Irá retornar a conta completa do usuário e irá pegar o
     * primeiro resultado pois, está sendo validado.
     *
     * @param username
     * @return
     */
    public UsuarioEntity getByUsername(String username) {
        return usuarioRepository.findByUsername(username)
                .stream()
                .findFirst()
                .orElseThrow(AccountNotFoundException::new);
    }

    /**
     * Está retornando todos usuários registrados mas,
     * apenas o nome de usuário.
     *
     * @return
     */
    public Stream<String> listAccounts() {
        return usuarioRepository.findAll().stream().map(UsuarioEntity::getUsername);
    }

    /**
     * Irá receber a id que está sendo passado no controller,
     * então irá verificar se a ID existe, caso existir irá deletar o usuário com
     * a id que foi passada, caso contrário irá lançar uma exceção personalizada.
     *
     * @param id
     */
    @Transactional
    public void deleteById(Long id) {
        if (usuarioRepository.findById(id).isPresent()) {
            usuarioRepository.deleteById(id);
        } else {
            throw new AccountNotFoundException();
        }
    }

    /**
     * Irá receber o  parâmetro Path que foi passado no controlador
     * e irá verificar se a conta existe,
     * também irá ser responsável por ver se a senha que foi passada é a antiga do usuário
     * para assim garantir segurança.
     *
     * @param id
     * @param newPassword
     */
    @Transactional
    public void updatePasswordById(Long id, String oldPassword, String newPassword) {
        if (usuarioRepository.findById(id).isEmpty()){
            throw new AccountNotFoundException();
        }

        usuarioRepository.findById(id).ifPresent(usuarioEntity -> {
            if (usuarioEntity.getPassword().equals(oldPassword)) {
                usuarioRepository.findById(id).ifPresent(username -> {
                    username.setPassword(newPassword);
                });
            } else {
                throw new AccountPasswordWrongException();
            }
        });
    }

    /**
     * Está aguardando o parâmetro id e newUsername (O novo nick de usuário)
     * Depois está procurando se a id fornecida existe no banco de dados, caso exister
     * irá ver se o nick já foi utilizado e se caso não irá alterar com o novo nome de usuário.
     *
     * @param id
     * @param newUsername
     */
    @Transactional
    public void updateUsernameById(Long id, String newUsername) {
        if (usuarioRepository.findById(id).isEmpty()){
            throw new AccountNotFoundException();
        }

        usuarioRepository.findById(id).ifPresent(usuarioEntity -> {
            if (usuarioRepository.findByUsername(newUsername).isPresent()){
                throw new AccountAlreadyExistsException();
            }
            if (usuarioEntity.getUsername().equals(newUsername)){
                throw new AccountAlreadyExistsException();
            }
            usuarioEntity.setUsername(newUsername);
        });
    }
}
