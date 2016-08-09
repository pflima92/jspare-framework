package org.jspare.sample.web.forvertx.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.br.CPF;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Person {

	@NotNull
	@Size(min = 5, max = 40)
	private String name;
	@CPF
	private String cpf;
}
