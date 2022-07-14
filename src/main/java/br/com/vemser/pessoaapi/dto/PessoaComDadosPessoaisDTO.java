package br.com.vemser.pessoaapi.dto;

import br.com.vemser.pessoaapi.enums.TipoSexo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PessoaComDadosPessoaisDTO {

    private Integer idPessoa;

    @Schema(description = "Nome da Pessoa")
    @NotBlank(message = "Insira um nome!" )
    private String nome;

    @Schema(description = "Data de nascimento")
    @NotNull
    @Past
    private LocalDate dataNascimento;

    @Schema(description = "CPF")
    @NotEmpty
    @Size(min = 11, max = 11, message = "CPF deve conter 11 números")
    private String cpf;

    @Schema(description = "Email")
    @NotBlank
    private String email;

    private String cnh;

    private String nomeMae;

    private String nomePai;

    private String rg;

    private TipoSexo sexo;

    private String tituloEleitor;
}
