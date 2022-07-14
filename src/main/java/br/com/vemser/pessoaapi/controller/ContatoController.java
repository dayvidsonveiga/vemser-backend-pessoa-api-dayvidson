package br.com.vemser.pessoaapi.controller;

import br.com.vemser.pessoaapi.dto.ContatoCreateDTO;
import br.com.vemser.pessoaapi.dto.ContatoDTO;
import br.com.vemser.pessoaapi.entities.Contato;
import br.com.vemser.pessoaapi.exceptions.RegraDeNegocioException;
import br.com.vemser.pessoaapi.service.ContatoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/contato") // localhost:8080/contato
@Validated
public class ContatoController {

    @Autowired
    private ContatoService contatoService;

    @Operation(summary = "Adicionar contato", description = "Adiciona um endereço, vinculando-o à uma pessoa existente")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna o corpo do contato adicionado, com seu novo ID"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PostMapping ("/{idPessoa}")//localhost:8080/contato
    public ResponseEntity<ContatoDTO> create(@PathVariable ("idPessoa") Integer idPessoa, @RequestBody @Valid ContatoCreateDTO contatoCreateDTO) throws RegraDeNegocioException {
        return new ResponseEntity<>(contatoService.create(idPessoa, contatoCreateDTO), HttpStatus.CREATED);
    }

    @Operation(summary = "Atualizar contato", description = "Atualiza um contato, localizando-o por seu ID")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna o corpo do contato atualizado"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PutMapping("/{idContato}") //localhost:8080/contato/{idContato}
    public ResponseEntity<ContatoDTO> update(@PathVariable("idContato") Integer id, @RequestBody @Valid ContatoCreateDTO contatoAtualizarDTO) throws RegraDeNegocioException{
        return new ResponseEntity<>(contatoService.update(id, contatoAtualizarDTO), HttpStatus.OK);
    }

    @Operation(summary = "Remover contato", description = "Remove um contato, localizando-o por seu ID")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna somente o Status Code da requisição HTTP"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @DeleteMapping("/{idContato}") //localhost:8080/contato/{idContato}
    public void delete(@PathVariable("idContato") Integer id) throws RegraDeNegocioException {
        contatoService.delete(id);
    }

    @Operation(summary = "Listar contatos", description = "Lista todos os contatos do banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna a lista de contatos"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping //localhost:8080/contato
    public ResponseEntity<List<ContatoDTO>> list (){
        return new ResponseEntity<>(contatoService.list(), HttpStatus.OK);
    }

    @Operation(summary = "Listar contatos de uma pessoa", description = "Lista todos os contatos vinculados ao ID de uma pessoa")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna a lista de contatos vinculados ao ID de uma pessoa"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/{idPessoa}") //localhost:8080/contato/2
    public ResponseEntity<List<ContatoDTO>> listByIdPessoa(@PathVariable ("idPessoa") Integer idPessoa) throws RegraDeNegocioException {
        return new ResponseEntity<>(contatoService.listByIdPessoa(idPessoa), HttpStatus.OK);
    }
}

