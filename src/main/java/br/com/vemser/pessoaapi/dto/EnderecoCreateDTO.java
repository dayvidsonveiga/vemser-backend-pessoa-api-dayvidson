package br.com.vemser.pessoaapi.dto;

import br.com.vemser.pessoaapi.enums.TipoEndereco;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class EnderecoCreateDTO {

    @Schema(description = "ID da pessoa à quem o endereço pertence")
    private Integer idPessoa;

    @Schema(description = "Tipo de endereço (COMERCIAL/RESIDENCIAL)")
    @NotNull
    private TipoEndereco tipo;

    @Schema(description = "Logradouro do endereço")
    @Length(min = 1, max = 250)
    private String logradouro;

    @Schema(description = "Número do endereço")
    @NotNull
    private Integer numero;

    @Schema(description = "Complemento (opcional) do endereço")
    private String complemento;

    @Schema(description = "CEP do endereço", maxLength = 8, minLength = 8)
    @NotBlank
    @Size(min = 8, max = 8, message = "CEP deve possuir 8 números")
    private String cep;

    @Schema(description = "Cidade do endereço")
    @NotBlank
    @Length(max = 250, message = "Campo cidade pode ter no máximo 250 caracteres")
    private String cidade;

    @Schema(description = "Estado do endereço")
    @NotNull
    private String estado;

    @Schema(description = "País do endereço")
    @NotNull
    private String pais;


}
