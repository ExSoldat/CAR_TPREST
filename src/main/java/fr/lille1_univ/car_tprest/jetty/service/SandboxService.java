package fr.lille1_univ.car_tprest.jetty.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import fr.lille1_univ.car_tprest.jetty.model.SandboxObject;

@Component
public class SandboxService {
	public SandboxObject object;
	public String testVar(String name) {
		object = new SandboxObject(name, "Tango", "Charlie");
		System.out.println("returning : " + object.renderComplexJSON());
		return object.renderComplexJSON();
	}
}
