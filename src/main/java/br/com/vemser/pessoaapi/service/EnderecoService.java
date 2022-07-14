package br.com.vemser.pessoaapi.service;

import br.com.vemser.pessoaapi.dto.EnderecoCreateDTO;
import br.com.vemser.pessoaapi.dto.EnderecoDTO;
import br.com.vemser.pessoaapi.entities.Endereco;
import br.com.vemser.pessoaapi.entities.Pessoa;
import br.com.vemser.pessoaapi.exceptions.RegraDeNegocioException;
import br.com.vemser.pessoaapi.repository.EnderecoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class EnderecoService {

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private PessoaService pessoaService;

    @Autowired
    private EmailService emailService;

    @Autowired
    ObjectMapper objectMapper;

    public EnderecoDTO create(Integer idPessoa, EnderecoCreateDTO enderecoCreateDTO) throws RegraDeNegocioException {
        log.info("Criando endereço...");
        Pessoa pessoa = pessoaService.findByIdPessoa(idPessoa);
        log.info("Criando o endereço para: " + pessoa.getNome());

        Endereco enderecoEntity = objectMapper.convertValue(enderecoCreateDTO, Endereco.class);
        enderecoEntity.setIdPessoa(idPessoa);
        enderecoEntity = enderecoRepository.create(enderecoEntity);

        EnderecoDTO enderecoDTO = objectMapper.convertValue(enderecoEntity, EnderecoDTO.class);
        log.info("Endereço criado!");
        emailService.sendEmailAdicionarEndereco(pessoa);
        return enderecoDTO;
    }

    public EnderecoDTO update(Integer idEndereco, EnderecoCreateDTO enderecoCreateDTOAtualizar) throws RegraDeNegocioException {
        log.info("Atualizando endereço...");
        Endereco enderecoEntityAtualizado = findByIdEndereco(idEndereco);
        Pessoa pessoaRecuperada = pessoaService.findByIdPessoa(enderecoEntityAtualizado.getIdPessoa());
        log.info("Atualizando o endereço de: " + pessoaRecuperada.getNome());

        enderecoEntityAtualizado.setTipo(enderecoCreateDTOAtualizar.getTipo());
        enderecoEntityAtualizado.setLogradouro(enderecoCreateDTOAtualizar.getLogradouro());
        enderecoEntityAtualizado.setNumero(enderecoCreateDTOAtualizar.getNumero());
        enderecoEntityAtualizado.setComplemento(enderecoCreateDTOAtualizar.getComplemento());
        enderecoEntityAtualizado.setCep(enderecoCreateDTOAtualizar.getCep());
        enderecoEntityAtualizado.setCidade(enderecoCreateDTOAtualizar.getCidade());
        enderecoEntityAtualizado.setEstado(enderecoCreateDTOAtualizar.getEstado());
        enderecoEntityAtualizado.setPais(enderecoCreateDTOAtualizar.getPais());

        EnderecoDTO enderecoDTO = objectMapper.convertValue(enderecoEntityAtualizado, EnderecoDTO.class);
        log.info("Endereço atualizado!");
        emailService.sendEmailAtualizarEndereco(pessoaRecuperada);
        return enderecoDTO;
    }

    public void delete(Integer idEndereco) throws RegraDeNegocioException {
        log.info("Deletando endereço...");
        Endereco enderecoRecuperado = findByIdEndereco(idEndereco);
        Pessoa pessoaRecuperada = pessoaService.findByIdPessoa(enderecoRecuperado.getIdPessoa());
        enderecoRepository.list().remove(enderecoRecuperado);
        emailService.sendEmailRemoverEndereco(pessoaRecuperada);
        log.info("Endereço deletado!");
    }

    public List<EnderecoDTO> list() {
        log.info("Listar todos os endereços");
        return enderecoRepository.list().stream()
                .map(endereco -> objectMapper.convertValue(endereco, EnderecoDTO.class))
                .collect(Collectors.toList());
    }

    public EnderecoDTO listByIdEndereco(Integer idEndereco) throws RegraDeNegocioException {
        log.info("Listar endereco por id");
        return objectMapper.convertValue(findByIdEndereco(idEndereco), EnderecoDTO.class);
    }

    public List<EnderecoDTO> listByIdPessoa(Integer idPessoa) throws RegraDeNegocioException {
        log.info("Listar endereco por id de Pessoa");
        pessoaService.findByIdPessoa(idPessoa);
        return enderecoRepository.list().stream()
                .filter(endereco -> endereco.getIdPessoa().equals(idPessoa))
                .map(endereco -> objectMapper.convertValue(endereco, EnderecoDTO.class))
                .collect(Collectors.toList());
    }

    //Utilização interna
    public Endereco findByIdEndereco(Integer idEndereco) throws RegraDeNegocioException {
        Endereco enderecoById = enderecoRepository.list().stream()
                .filter(endereco -> endereco.getIdEndereco().equals(idEndereco))
                .findFirst()
                .orElseThrow(() -> new RegraDeNegocioException("Endereço não encontrado"));
        return enderecoById;
    }
}