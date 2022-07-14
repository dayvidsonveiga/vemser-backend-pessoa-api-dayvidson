package br.com.vemser.pessoaapi.service;

import br.com.vemser.pessoaapi.client.DadosPessoaisClient;
import br.com.vemser.pessoaapi.dto.DadosPessoaisDTO;
import br.com.vemser.pessoaapi.dto.PessoaComDadosPessoaisDTO;
import br.com.vemser.pessoaapi.dto.PessoaCreateDTO;
import br.com.vemser.pessoaapi.dto.PessoaDTO;
import br.com.vemser.pessoaapi.entities.Pessoa;
import br.com.vemser.pessoaapi.exceptions.RegraDeNegocioException;
import br.com.vemser.pessoaapi.repository.PessoaComDadosPessoaisRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PessoaComDadosPessoaisService {

    @Autowired
    private DadosPessoaisService dadosPessoaisService;

    @Autowired
    private PessoaService pessoaService;

    @Autowired
    private PessoaComDadosPessoaisRepository pessoaComDadosPessoaisRepository;

    @Autowired
    private ObjectMapper objectMapper;

    public List<PessoaComDadosPessoaisDTO> getAll() {
        return pessoaComDadosPessoaisRepository.list();
    }

    public PessoaComDadosPessoaisDTO post(PessoaComDadosPessoaisDTO pessoaComDadosPessoaisDTO) throws RegraDeNegocioException {
        PessoaDTO pessoaDTO = pessoaService.create(convertPessoaComDadosToPessoaDTO(pessoaComDadosPessoaisDTO));
        dadosPessoaisService.post(convertPessoaComDadosToDadosPessoaisDTO(pessoaComDadosPessoaisDTO));

        pessoaComDadosPessoaisDTO.setIdPessoa(pessoaDTO.getIdPessoa());

        pessoaComDadosPessoaisRepository.post(pessoaComDadosPessoaisDTO);

        return pessoaComDadosPessoaisDTO;
    }

    public PessoaComDadosPessoaisDTO put(String cpf, PessoaComDadosPessoaisDTO pessoaComDadosPessoaisDTOAtualizar) throws RegraDeNegocioException {
        PessoaComDadosPessoaisDTO pessoaComDadosRecuperada = findByCpf(cpf);

        updatePessoa(pessoaComDadosPessoaisDTOAtualizar);
        updateDadosPessoais(pessoaComDadosPessoaisDTOAtualizar);
        PessoaComDadosPessoaisDTO pessoaComDadosAtualizada = updatePessoasComDadosPessoais(pessoaComDadosRecuperada, pessoaComDadosPessoaisDTOAtualizar);

        return pessoaComDadosAtualizada;
    }

    public void delete(String cpf) throws RegraDeNegocioException {
        PessoaComDadosPessoaisDTO pessoaComDadosRecuperada = findByCpf(cpf);
        pessoaService.delete(pessoaComDadosRecuperada.getIdPessoa());
        dadosPessoaisService.delete(cpf);
        pessoaComDadosPessoaisRepository.delete(pessoaComDadosRecuperada);
    }

    private PessoaComDadosPessoaisDTO updatePessoasComDadosPessoais(PessoaComDadosPessoaisDTO pessoaRecuperada, PessoaComDadosPessoaisDTO pessoaAtualizar) {
        pessoaRecuperada.setNome(pessoaAtualizar.getNome());
        pessoaRecuperada.setCpf(pessoaAtualizar.getCpf());
        pessoaRecuperada.setDataNascimento(pessoaAtualizar.getDataNascimento());
        pessoaRecuperada.setEmail(pessoaAtualizar.getEmail());
        return pessoaRecuperada;
    }

    private  void updatePessoa(PessoaComDadosPessoaisDTO pessoaComDadosPessoaisDTO) throws RegraDeNegocioException {
        PessoaCreateDTO pessoaCreateDTO = new PessoaCreateDTO();

        pessoaCreateDTO.setNome(pessoaComDadosPessoaisDTO.getNome());
        pessoaCreateDTO.setDataNascimento(pessoaComDadosPessoaisDTO.getDataNascimento());
        pessoaCreateDTO.setEmail(pessoaComDadosPessoaisDTO.getEmail());

        pessoaService.update(pessoaComDadosPessoaisDTO.getIdPessoa(), pessoaCreateDTO);
    }

    private void updateDadosPessoais(PessoaComDadosPessoaisDTO pessoaComDadosPessoaisDTO) {
        DadosPessoaisDTO dadosPessoaisDTO = new DadosPessoaisDTO();

        dadosPessoaisDTO.setCnh(pessoaComDadosPessoaisDTO.getCnh());
        dadosPessoaisDTO.setNome(pessoaComDadosPessoaisDTO.getNome());
        dadosPessoaisDTO.setNomeMae(pessoaComDadosPessoaisDTO.getNomeMae());
        dadosPessoaisDTO.setNomePai(pessoaComDadosPessoaisDTO.getNomePai());
        dadosPessoaisDTO.setRg(pessoaComDadosPessoaisDTO.getRg());
        dadosPessoaisDTO.setSexo(pessoaComDadosPessoaisDTO.getSexo());
        dadosPessoaisDTO.setTituloEleitor(pessoaComDadosPessoaisDTO.getTituloEleitor());

        dadosPessoaisService.put(pessoaComDadosPessoaisDTO.getCpf(), dadosPessoaisDTO);
    }

    private PessoaDTO convertPessoaComDadosToPessoaDTO(PessoaComDadosPessoaisDTO pessoaComDadosPessoaisDTO) {
        PessoaDTO pessoaDTO = new PessoaDTO();
        pessoaDTO.setNome(pessoaComDadosPessoaisDTO.getNome());
        pessoaDTO.setCpf(pessoaComDadosPessoaisDTO.getCpf());
        pessoaDTO.setDataNascimento(pessoaComDadosPessoaisDTO.getDataNascimento());
        pessoaDTO.setEmail(pessoaComDadosPessoaisDTO.getEmail());
        return pessoaDTO;
    }

    private DadosPessoaisDTO convertPessoaComDadosToDadosPessoaisDTO(PessoaComDadosPessoaisDTO pessoaComDadosPessoaisDTO) {
        DadosPessoaisDTO dadosPessoaisDTO = new DadosPessoaisDTO();
        dadosPessoaisDTO.setCnh(pessoaComDadosPessoaisDTO.getCnh());
        dadosPessoaisDTO.setCpf(pessoaComDadosPessoaisDTO.getCpf());
        dadosPessoaisDTO.setNome(pessoaComDadosPessoaisDTO.getNome());
        dadosPessoaisDTO.setNomeMae(pessoaComDadosPessoaisDTO.getNomeMae());
        dadosPessoaisDTO.setNomePai(pessoaComDadosPessoaisDTO.getNomePai());
        dadosPessoaisDTO.setRg(pessoaComDadosPessoaisDTO.getRg());
        dadosPessoaisDTO.setSexo(pessoaComDadosPessoaisDTO.getSexo());
        dadosPessoaisDTO.setTituloEleitor(pessoaComDadosPessoaisDTO.getTituloEleitor());
        return dadosPessoaisDTO;
    }

    public PessoaComDadosPessoaisDTO findByCpf(String cpf) throws RegraDeNegocioException {
        return pessoaComDadosPessoaisRepository.list().stream()
                .filter(pessoaComDados -> pessoaComDados.getCpf().equals(cpf))
                .findFirst()
                .orElseThrow(() -> new RegraDeNegocioException("CPF nao encontrado"));
    }
}