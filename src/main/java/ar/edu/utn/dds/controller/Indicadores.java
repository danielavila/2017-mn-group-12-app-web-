package ar.edu.utn.dds.controller;

import static spark.Spark.get;
import static spark.Spark.post;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ar.edu.utn.dds.excepciones.NoSeEncuentraElIndicadorException;
import ar.edu.utn.dds.excepciones.NoSeEncuentraLaCuentaEnElPeriodoException;
import ar.edu.utn.dds.excepciones.NoSeEncuentraLaCuentaException;
import ar.edu.utn.dds.excepciones.NoSeEncuentraLaEmpresaException;
import ar.edu.utn.dds.modeloWeb.IndicadorCalculable;
import ar.edu.utn.dds.modeloWeb.IndicadorWeb;
import ar.edu.utn.dds.modeloWeb.Model;
import ar.edu.utn.dds.templateEngine.FreeMarkerEngine;
import spark.ModelAndView;

public class Indicadores {
	public void init(Model mod) {
	get("/calcularIndicador", (request, response) -> {
		response.status(200);
		Map<String, Object> viewObjects = new HashMap<String, Object>();
		viewObjects.put("indicadores", mod.sendIndicadores());
		viewObjects.put("empresas", mod.sendEmpresas());
		viewObjects.put("templateName", "calcularIndicador.ftl");
		return new ModelAndView(viewObjects, "main.ftl");
	}, new FreeMarkerEngine());

	post("/calcularIndicador", (request, response) -> {
		ObjectMapper mapper = new ObjectMapper();
		try {
			IndicadorCalculable i = mapper.readValue(request.body(), IndicadorCalculable.class);

			if (mod.checkIndicadorCalculable(i)) {

				String resultado = mod.calcularIndicador(i, mod.armarPeriodo(i.getFechaInicio(), i.getFechaFin()));
				response.status(200);
				response.type("application/json");
				return "Resultado: " + resultado;

			} else {
				response.status(400);
				response.type("application/json");
				return "La empresa no existe";
			}
		} catch (JsonParseException jpe) {
			response.status(404);
			return "Exception";
		} catch (NoSeEncuentraElIndicadorException e) {
			response.status(404);
			return "No se encuentra Indicador";
		} catch (NoSeEncuentraLaCuentaEnElPeriodoException e) {
			response.status(404);
			return "No se encuentra la cuenta en el periodo";
		} catch (NoSeEncuentraLaCuentaException e) {
			response.status(404);
			return "No se encuentra la cuenta";
		} catch (NoSeEncuentraLaEmpresaException e) {
			response.status(404);
			return "No se encuentra la empresa";
		}
	});

	get("/crearIndicador", (request, response) -> {
		response.status(200);
		Map<String, Object> viewObjects = new HashMap<String, Object>();
		viewObjects.put("templateName", "crearIndicador.ftl");
		return new ModelAndView(viewObjects, "main.ftl");
	}, new FreeMarkerEngine());

	post("/crearIndicador", (request, response) -> {
		ObjectMapper mapper = new ObjectMapper();
		try {
			IndicadorWeb i = mapper.readValue(request.body(), IndicadorWeb.class);
			if (!i.isValid()) {
				response.status(400);
				return "Corregir los campos";
			}
			mod.createIndicador(i.getNombre(), i.getExpresion());
			response.status(200);
			response.type("application/json");
			return "Indicador creado";

		} catch (JsonParseException jpe) {
			response.status(404);
			return "Exception";
		}
	});
}}
