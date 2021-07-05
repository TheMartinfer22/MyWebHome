package tech.martindev.mwh.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tech.martindev.mwh.entities.UsuarioEntity;
import tech.martindev.mwh.service.UsuarioService;

import java.util.stream.Stream;

@RestController
public class UsuarioRestController {

    final UsuarioService usuarioService;

    public UsuarioRestController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void createAccount(@RequestBody UsuarioEntity usuarioEntity) throws IllegalAccessException{
        usuarioService.createAccount(usuarioEntity);
    }

    @GetMapping(path = "/get/{id}")
    public UsuarioEntity listById(@PathVariable  Long id){
        return usuarioService.getById(id);
    }

    @ResponseStatus(HttpStatus.FOUND)
    @GetMapping(path = "{username}")
    public UsuarioEntity listByUsername(@PathVariable  String username){
        return usuarioService.getByUsername(username);
    }

    @ResponseStatus(HttpStatus.FOUND)
    @GetMapping
    public Stream<String> listAccounts(){
        return usuarioService.listAccounts();
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @DeleteMapping(path = "/delete/{id}")
    public void deleteById(@PathVariable Long id){
        usuarioService.deleteById(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping(path = "/update_pass/{id}/{oldPassword}/{newPassword}")
    public void updatePasswordById(@PathVariable Long id, @PathVariable String oldPassword, @PathVariable String newPassword){
        usuarioService.updatePasswordById(id, oldPassword, newPassword);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping(path = "/update_user/{id}/{newUsername}")
    public void updateUsernameById(@PathVariable Long id, @PathVariable String newUsername){
        usuarioService.updateUsernameById(id, newUsername);
    }
}
