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

public class Empresas {
	public void init(Model mod) {
	get("/getEmpresas", (request, response) -> {
		response.status(200);
		mod.getEmpresas();
		Map<String, Object> viewObjects = new HashMap<String, Object>();
		viewObjects.put("empresas", mod.sendEmpresas());
		viewObjects.put("templateName", "mostrarEmpresa.ftl");
		return new ModelAndView(viewObjects, "main.ftl");
	}, new FreeMarkerEngine());

	



	get("/getempresas", (request, response) -> {
		response.status(200);
		return toJSON(mod.sendEmpresas());
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
