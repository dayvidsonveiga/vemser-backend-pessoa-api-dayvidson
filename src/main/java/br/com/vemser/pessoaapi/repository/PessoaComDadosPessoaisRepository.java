package br.com.vemser.pessoaapi.repository;

import br.com.vemser.pessoaapi.client.DadosPessoaisClient;
import br.com.vemser.pessoaapi.dto.PessoaComDadosPessoaisDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PessoaComDadosPessoaisRepository {

    private static List<PessoaComDadosPessoaisDTO> listaPessoasComDadosPessoais = new ArrayList<>();

    @Autowired
    private PessoaRepository pessoaRepository;
    @Autowired
    private DadosPessoaisClient dadosPessoaisClient;


    public List<PessoaComDadosPessoaisDTO> list() {
        return listaPessoasComDadosPessoais;
    }

    public PessoaComDadosPessoaisDTO post(PessoaComDadosPessoaisDTO pessoaComDados) {
        listaPessoasComDadosPessoais.add(pessoaComDados);
        return pessoaComDados;
    }

    public void delete(PessoaComDadosPessoaisDTO pessoaComDadosPessoaisDTO) {
        listaPessoasComDadosPessoais.remove(pessoaComDadosPessoaisDTO);
    }
}