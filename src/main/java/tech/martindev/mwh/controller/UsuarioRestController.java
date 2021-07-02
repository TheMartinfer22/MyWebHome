package tech.martindev.mwh.controller;

import org.springframework.web.bind.annotation.*;
import tech.martindev.mwh.entities.UsuarioEntity;
import tech.martindev.mwh.service.UsuarioService;

@RestController
public class UsuarioRestController {

    final UsuarioService usuarioService;

    public UsuarioRestController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping
    public void createAccount(@RequestBody UsuarioEntity usuarioEntity){
        usuarioService.createUser(usuarioEntity);
    }

    @GetMapping(path = "{id}")
    public UsuarioEntity listAccounts(@PathVariable  Long id){
        return usuarioService.listAccounts(id);
    }
}
