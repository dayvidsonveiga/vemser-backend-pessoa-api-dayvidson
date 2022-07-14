package br.com.vemser.pessoaapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.*;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PessoaCreateDTO {

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
}
