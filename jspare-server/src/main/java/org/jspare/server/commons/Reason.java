package org.jspare.server.commons;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Reason {

	private String name;
	private String message;
}
