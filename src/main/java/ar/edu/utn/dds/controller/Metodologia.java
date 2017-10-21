package ar.edu.utn.dds.controller;

import static spark.Spark.get;
import static spark.Spark.post;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.PersistenceException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ar.edu.utn.dds.entidades.Empresas;
import ar.edu.utn.dds.excepciones.MetodologiaYaExisteException;
import ar.edu.utn.dds.excepciones.NoHayCondicionesException;
import ar.edu.utn.dds.excepciones.NoHayEmpresasQueCumplanLaCondicionException;
import ar.edu.utn.dds.modelo.Empresa;
import ar.edu.utn.dds.modeloWeb.MetodologiaAplicable;
import ar.edu.utn.dds.modeloWeb.MetodologiaWeb;
import ar.edu.utn.dds.modeloWeb.Model;
import ar.edu.utn.dds.templateEngine.FreeMarkerEngine;
import spark.ModelAndView;

public class Metodologia {
	public void init(Model mod) {
	get("/aplicarMetodologia", (request, response) -> {
		response.status(200);
		Map<String, Object> viewObjects = new HashMap<String, Object>();
		viewObjects.put("empresas", mod.sendEmpresas());
		viewObjects.put("metodologias", mod.sendMetodologias());
		viewObjects.put("templateName", "aplicarMetodologia.ftl");
		return new ModelAndView(viewObjects, "main.ftl");
	}, new FreeMarkerEngine());

	post("/aplicarMetodologia", (request, response) -> {
		ObjectMapper mapper = new ObjectMapper();
		try {

			MetodologiaAplicable metodologia = mapper.readValue(request.body(), MetodologiaAplicable.class);
			List<String> nombreEmpresas=new ArrayList<>();
			nombreEmpresas.add(metodologia.getEmpresa1());
			nombreEmpresas.add(metodologia.getEmpresa2());
			nombreEmpresas.add(metodologia.getEmpresa3());
			nombreEmpresas.add(metodologia.getEmpresa4());
			
			List<Empresa> empresas=new ArrayList<>();
			nombreEmpresas.stream().filter(unN->unN!=null).collect(Collectors.toList()).stream().forEach(unN->{
				empresas.add(Empresas.getEmpresas().stream().filter(unaE->unaE.getNombre().equals(unN)).findFirst().get());
			});;
			
			
			List <String> lista = new ArrayList<String>();
			(mod.aplicarMetodologia(metodologia.getNombre(),empresas)).forEach(unP -> {
				lista.add(unP.getNombreEmpresa());
			});;
			String empresu = String.join("  ||  ", lista);
			response.status(200);
			response.type("application/json");

			return empresu;

		} catch (JsonParseException jpe) {
			response.status(404);
			return "Exception";
		} catch (NoHayEmpresasQueCumplanLaCondicionException e) {
			response.status(200);
			return "No hay empresas que cumplan con la metodologia";
		} catch (NoHayCondicionesException e) {
			response.status(200);
			return "La metodologia no presenta condiciones";
		}
	});
	
	
	
	get("/crearMetodologia", (request, response) -> {
		response.status(200);
		mod.inicializarMetodologia();
		Map<String, Object> viewObjects = new HashMap<String, Object>();
		viewObjects.put("templateName", "crearMetodologia.ftl");
		return new ModelAndView(viewObjects, "main.ftl");
	}, new FreeMarkerEngine());

	post("/crearMetodologia", (request, response) -> {
		ObjectMapper mapper = new ObjectMapper();
		try {
			MetodologiaWeb metodologia = mapper.readValue(request.body(), MetodologiaWeb.class);
			mod.createMetodologia(metodologia.getNombre());

			response.status(200);
			response.type("application/json");

			return "Metodologia creada exitosamente, ahora agreguele condiciones";

		} catch (JsonParseException jpe) {
			response.status(404);
			return "Exception";
		} catch (MetodologiaYaExisteException e) {
			response.status(403);
			return "Ya existe una metodologia con ese nombre";
		} catch (PersistenceException e) {
			response.status(403);
			return "Ya existe una metodologia con ese nombre";
		}
	});
}}
