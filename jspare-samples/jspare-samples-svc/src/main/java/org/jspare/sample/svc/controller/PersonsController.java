package org.jspare.sample.svc.controller;

import org.jspare.sample.svc.model.Person;
import org.jspare.server.commons.SimpleResponse;
import org.jspare.server.controller.Controller;
import org.jspare.server.mapping.Mapping;
import org.jspare.server.mapping.Method;
import org.jspare.server.mapping.Type;

public class PersonsController extends Controller {
	
	@Mapping("persons")
	@Method(Type.POST)
	public void save(Person person){
		
		success(new SimpleResponse());
	}
}