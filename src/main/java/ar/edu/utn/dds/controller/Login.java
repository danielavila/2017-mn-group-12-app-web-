package ar.edu.utn.dds.controller;

import static spark.Spark.post;

import java.util.HashMap;
import java.util.Map;

import static spark.Spark.get;
import com.fasterxml.jackson.databind.ObjectMapper;

import ar.edu.utn.dds.modeloWeb.LoginWeb;
import ar.edu.utn.dds.modeloWeb.Model;
import ar.edu.utn.dds.templateEngine.FreeMarkerEngine;
import spark.ModelAndView;


public class Login {
	public void init(Model mod) {
	get("/login", (request, response) -> {	
		response.status(200);
		Map<String, Object> viewObjects = new HashMap<String, Object>();
			viewObjects.put("templateName", "login1.ftl");
		return new ModelAndView(viewObjects, "main.ftl");
		
	}, new FreeMarkerEngine());
	
	
	post("/login", (request, response) -> {
		response.status(200);
		ObjectMapper mapper = new ObjectMapper();
		LoginWeb usuario = mapper.readValue(request.body(), LoginWeb.class);
		mod.setUsuario(usuario);
		return "ingreso Usuario";
	});
}}
