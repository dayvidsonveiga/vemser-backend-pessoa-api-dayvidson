package br.com.vemser.pessoaapi.service;

import br.com.vemser.pessoaapi.dto.ContatoCreateDTO;
import br.com.vemser.pessoaapi.dto.ContatoDTO;
import br.com.vemser.pessoaapi.entities.Contato;
import br.com.vemser.pessoaapi.entities.Pessoa;
import br.com.vemser.pessoaapi.exceptions.RegraDeNegocioException;
import br.com.vemser.pessoaapi.repository.ContatoRepository;
import br.com.vemser.pessoaapi.repository.PessoaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ContatoService {

    @Autowired
    private ContatoRepository contatoRepository;

    @Autowired
    private PessoaService pessoaService;

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private ObjectMapper objectMapper;

//    public ContatoService(){
//        contatoRepository = new ContatoRepository();
//    }

    public ContatoDTO create(Integer idPessoa, ContatoCreateDTO contatoCreateDTO) throws RegraDeNegocioException {
        log.info("Criando contato...");
        Pessoa pessoa = pessoaService.findByIdPessoa(idPessoa);
        log.info("Adicionando contato para " + pessoa.getNome());

        contatoCreateDTO.setIdPessoa(idPessoa);
        Contato contatoEntity = objectMapper.convertValue(contatoCreateDTO, Contato.class);
        contatoEntity = contatoRepository.create(contatoEntity);

        ContatoDTO contatoDTO = objectMapper.convertValue(contatoEntity, ContatoDTO.class);
        emailService.sendEmailAdicionarContato(pessoa);
        log.info("Contato adicionado!");
        return contatoDTO;
    }

    public ContatoDTO update(Integer idContato, ContatoCreateDTO contatoCreateDTOAtualizar) throws RegraDeNegocioException{
        log.info("Atualizando contato...");
        Contato contatoEntityAtualizado = findByIdContato(idContato);
        Pessoa pessoa = pessoaService.findByIdPessoa(contatoEntityAtualizado.getIdPessoa());

        contatoEntityAtualizado.setTipoContato(contatoCreateDTOAtualizar.getTipoContato());
        contatoEntityAtualizado.setNumero(contatoCreateDTOAtualizar.getNumero());
        contatoEntityAtualizado.setDescricao(contatoCreateDTOAtualizar.getDescricao());

        ContatoDTO contatoDTO = objectMapper.convertValue(contatoEntityAtualizado, ContatoDTO.class);
        emailService.sendEmailAtualizarContato(pessoa);
        log.info("Contato atualizado!");
        return contatoDTO;
    }

    public void delete(Integer idContato) throws RegraDeNegocioException {
        log.info("Deletando contato...");
        Contato contatoRecuperado = findByIdContato(idContato);
        Pessoa pessoa = pessoaService.findByIdPessoa(contatoRecuperado.getIdPessoa());
        contatoRepository.deleteContato(contatoRecuperado);
        emailService.sendEmailRemoverContato(pessoa);
        log.info("Contato deletado!");
    }

    public List<ContatoDTO> list (){
        log.info("Listar todos os contatos");
        return contatoRepository.list().stream()
                .map(contato -> objectMapper.convertValue(contato, ContatoDTO.class))
                .collect(Collectors.toList());
    }

    public List<ContatoDTO> listByIdPessoa(Integer idPessoa) throws RegraDeNegocioException {
        log.info("Listar contato por idPessoa");
        pessoaService.findByIdPessoa(idPessoa);
        return contatoRepository.list().stream()
                .filter(contato -> contato.getIdPessoa().equals(idPessoa))
                .map(contato -> objectMapper.convertValue(contato, ContatoDTO.class))
                .collect(Collectors.toList());
    }

    //Utilização Interna
    public Contato findByIdContato(Integer idContato) throws RegraDeNegocioException {
        Contato contatoById = contatoRepository.list().stream()
                .filter(endereco -> endereco.getIdContato().equals(idContato))
                .findFirst()
                .orElseThrow(() -> new RegraDeNegocioException("Contato não encontrado"));
        return contatoById;
    }
}

