package ar.edu.utn.dds.controller;

import static spark.Spark.get;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import ar.edu.utn.dds.modeloWeb.Model;
import ar.edu.utn.dds.templateEngine.FreeMarkerEngine;
import spark.ModelAndView;

public class Cuentas {
	
	public void init(Model mod) {

	get("/getCuentas", (request, response) -> {

		response.status(200);
		Map<String, Object> viewObjects = new HashMap<String, Object>();
		viewObjects.put("templateName", "mostrarCuentas.ftl");

		return new ModelAndView(viewObjects, "main.ftl");
	}, new FreeMarkerEngine());

	get("/getCuentas/:id", (request, response) -> {
		response.status(200);
		String id = request.params(":id");
		Map<String, Object> viewObjects = new HashMap<String, Object>();
		viewObjects.put("templateName", "mostrarCuentas.ftl");
		mod.getCuentas(id);
		response.redirect("/getCuentas");
		return new ModelAndView(viewObjects, "main.ftl");
	}, new FreeMarkerEngine());

	get("/getcuentas", (request, response) -> {
		response.status(200);
		return toJSON(mod.sendCuentas());
	});

	
}
	private static String toJSON(Object obj) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			mapper.enable(SerializationFeature.INDENT_OUTPUT);
			StringWriter sw = new StringWriter();
			mapper.writeValue(sw, obj);
			return sw.toString();
		} catch (IOException e) {
			System.err.println(e);
		}
		return null;
	}
}