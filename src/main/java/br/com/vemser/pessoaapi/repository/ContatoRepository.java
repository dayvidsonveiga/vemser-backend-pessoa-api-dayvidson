package br.com.vemser.pessoaapi.repository;

import br.com.vemser.pessoaapi.entities.Contato;
import br.com.vemser.pessoaapi.entities.Pessoa;
import br.com.vemser.pessoaapi.enums.TipoContato;
import br.com.vemser.pessoaapi.exceptions.RegraDeNegocioException;
import br.com.vemser.pessoaapi.service.PessoaService;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class ContatoRepository {
    private static List<Contato> listaContatos = new ArrayList<>();
    private AtomicInteger COUNTER = new AtomicInteger();

    public static List<Contato> getListaContatos() {
        return listaContatos;
    }

    public static void setListaContatos(List<Contato> listaContatos) {
        ContatoRepository.listaContatos = listaContatos;
    }

    public ContatoRepository() {
        listaContatos.add(new Contato(COUNTER.incrementAndGet(), 1, TipoContato.COMERCIAL, "513510-4811", "Fixo"));
        listaContatos.add(new Contato(COUNTER.incrementAndGet() /*2*/, 2, TipoContato.RESIDENCIAL, "619484484584", "Pessoal"));
        listaContatos.add(new Contato(COUNTER.incrementAndGet() /*3*/, 3, TipoContato.RESIDENCIAL, "61999884584", "Whatsapp"));
        listaContatos.add(new Contato(COUNTER.incrementAndGet() /*4*/, 4, TipoContato.COMERCIAL, "517847284584", "Telegram"));
        listaContatos.add(new Contato(COUNTER.incrementAndGet() /*5*/, 5, TipoContato.RESIDENCIAL, "7195287284584", "Whatsapp"));
    }

    public Contato create(Contato contato) {
        contato.setIdContato(COUNTER.incrementAndGet());
        listaContatos.add(contato);
        return contato;
    }

    public void deleteContato(Contato contato) throws RegraDeNegocioException {

        listaContatos.remove(contato);
    }

    public List<Contato> list() {
        return listaContatos;
    }

}
