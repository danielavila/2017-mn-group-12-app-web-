package ar.edu.utn.dds.controller;

import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.staticFileLocation;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

import ar.edu.utn.dds.modeloWeb.Login;
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
			viewObjects.put("templateName", "login1.ftl");
			
			return new ModelAndView(viewObjects, "main.ftl");
			
		}, new FreeMarkerEngine());
		
		post("/app/log", (request, response) -> {
			response.status(200);
			ObjectMapper mapper = new ObjectMapper();
			Login usuario = request.attribute("usuario");
			return "ingreso Usuario";
		});
		
		
		
		get("/home", (request, response) -> {
			response.status(200);
			Map<String, Object> viewObjects = new HashMap<String, Object>();
			viewObjects.put("title", "TP ANUAL DDS");
			viewObjects.put("templateName", "home.ftl");
			return new ModelAndView(viewObjects, "main.ftl");
		}, new FreeMarkerEngine());

	}

	

}
