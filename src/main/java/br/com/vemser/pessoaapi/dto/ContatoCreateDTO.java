package br.com.vemser.pessoaapi.dto;

import br.com.vemser.pessoaapi.enums.TipoContato;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ContatoCreateDTO {

    @Schema(description = "ID da pessoa que o contato pertence")
    private Integer idPessoa;

    @Schema(description = "Tipo do contato(COMERCIAL/RESIDENCIAL)")
    @NotNull
    private TipoContato tipoContato;

    @Schema(description = "Número de telefone do contato", maxLength = 13)
    @NotBlank
    @Size(min = 1, max = 13, message = "Número pode ter até 13 números")
    private String numero;

    @Schema(description = "Forma de utilizar o contato")
    @NotBlank
    private String descricao;
}
