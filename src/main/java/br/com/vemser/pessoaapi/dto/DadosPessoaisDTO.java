package br.com.vemser.pessoaapi.dto;

import br.com.vemser.pessoaapi.enums.TipoSexo;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DadosPessoaisDTO {

    private String cnh;

    private String cpf;

    private String nome;

    private String nomeMae;

    private String nomePai;

    private String rg;

    private TipoSexo sexo;

    private String tituloEleitor;
}
