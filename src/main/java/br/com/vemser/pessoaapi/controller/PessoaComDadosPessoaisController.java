package br.com.vemser.pessoaapi.controller;

import br.com.vemser.pessoaapi.dto.DadosPessoaisDTO;
import br.com.vemser.pessoaapi.dto.PessoaComDadosPessoaisDTO;
import br.com.vemser.pessoaapi.exceptions.RegraDeNegocioException;
import br.com.vemser.pessoaapi.service.PessoaComDadosPessoaisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pessoas-com-dados")
public class PessoaComDadosPessoaisController {

    @Autowired
    private PessoaComDadosPessoaisService pessoaComDadosPessoaisService;

    @GetMapping
    public ResponseEntity<List<PessoaComDadosPessoaisDTO>> getAll(){
        return new ResponseEntity<>(pessoaComDadosPessoaisService.getAll(), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<PessoaComDadosPessoaisDTO> post(@RequestBody PessoaComDadosPessoaisDTO pessoaComDadosPessoaisDTO) throws RegraDeNegocioException {
        return new ResponseEntity<>(pessoaComDadosPessoaisService.post(pessoaComDadosPessoaisDTO), HttpStatus.OK);
    }

    @PutMapping("/{cpf}")
    public ResponseEntity<PessoaComDadosPessoaisDTO> put(@PathVariable("cpf") String cpf, @RequestBody PessoaComDadosPessoaisDTO pessoaComDadosPessoaisDTO) throws RegraDeNegocioException {
        return new ResponseEntity<>(pessoaComDadosPessoaisService.put(cpf, pessoaComDadosPessoaisDTO), HttpStatus.OK);
    }

    @DeleteMapping("/{cpf}")
    public void Delete(@PathVariable("cpf") String cpf) throws RegraDeNegocioException {
        pessoaComDadosPessoaisService.delete(cpf);
    }
}