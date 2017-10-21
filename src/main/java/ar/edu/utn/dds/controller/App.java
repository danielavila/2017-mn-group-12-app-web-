package ar.edu.utn.dds.controller;

import static spark.Spark.get;
import static spark.Spark.staticFileLocation;

import java.util.HashMap;
import java.util.Map;

import ar.edu.utn.dds.modeloWeb.Model;
import ar.edu.utn.dds.templateEngine.FreeMarkerEngine;
import spark.ModelAndView;

public class App {

	public static void main(String[] args) {
		staticFileLocation("/public");
		App s = new App();
		s.init();
	}

	/**
	 * Function for Routes
	 */

	private void init() {

		Model mod = new Model();
		Condiciones cond= new Condiciones();
		cond.init(mod);
		Empresas e=new Empresas();
		e.init(mod);
		Cuentas c=new Cuentas();
		c.init(mod);
		Indicadores i=new Indicadores();
		i.init(mod);
		Metodologia m=new Metodologia();
		m.init(mod);

		get("/", (request, response) -> {
			Map<String, Object> viewObjects = new HashMap<String, Object>();
			viewObjects.put("title", "TP ANUAL DDS");
			viewObjects.put("templateName", "home.ftl");
			return new ModelAndView(viewObjects, "main.ftl");
		}, new FreeMarkerEngine());

		
		

		


	}

	

}
