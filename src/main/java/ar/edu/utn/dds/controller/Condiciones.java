package ar.edu.utn.dds.controller;

import static spark.Spark.get;
import static spark.Spark.post;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ar.edu.utn.dds.excepciones.NoSeEncuentraElIndicadorException;
import ar.edu.utn.dds.modeloWeb.CrecienteWeb;
import ar.edu.utn.dds.modeloWeb.DecrecienteWeb;
import ar.edu.utn.dds.modeloWeb.LongevidadWeb;
import ar.edu.utn.dds.modeloWeb.Model;
import ar.edu.utn.dds.modeloWeb.SumaPromMedianaWeb;
import ar.edu.utn.dds.templateEngine.FreeMarkerEngine;
import spark.ModelAndView;

public class Condiciones {
	public void init(Model mod) {
		
	get("/condicionMediana", (request, response) -> {
		response.status(200);
		Map<String, Object> viewObjects = new HashMap<String, Object>();
		viewObjects.put("indicadores", mod.sendIndicadores());
		viewObjects.put("templateName", "condicionSumaPromeMediana.ftl");
		return new ModelAndView(viewObjects, "main.ftl");
	}, new FreeMarkerEngine());

	post("/condicionMediana", (request, response) -> {
		ObjectMapper mapper = new ObjectMapper();
		try {
			SumaPromMedianaWeb mediana = mapper.readValue(request.body(), SumaPromMedianaWeb.class);

			mod.createCondicionSumaPromeMediana("mediana", mediana.getNombreIndicador(), mediana.getComparador(),
					mediana.getValorAcomparar(), mediana.getOrdenamiento(),
					mod.armarPeriodo(mediana.getFechaInicio(), mediana.getFechaFin()));
			response.status(200);
			response.type("application/json");

			return "Condicion creada exitosamente, regrese al menu metodologia para finalizar la carga de condiciones";

		} catch (JsonParseException jpe) {
			response.status(404);
			return "Exception";
		} catch (NoSeEncuentraElIndicadorException e) {
			e.printStackTrace();
			return "Exception";
		}
	});

	get("/condicionPromedio", (request, response) -> {
		response.status(200);
		Map<String, Object> viewObjects = new HashMap<String, Object>();
		viewObjects.put("indicadores", mod.sendIndicadores());
		viewObjects.put("templateName", "condicionSumaPromeMediana.ftl");
		return new ModelAndView(viewObjects, "main.ftl");
	}, new FreeMarkerEngine());

	post("/condicionPromedio", (request, response) -> {
		ObjectMapper mapper = new ObjectMapper();
		try {
			SumaPromMedianaWeb promedio = mapper.readValue(request.body(), SumaPromMedianaWeb.class);

			mod.createCondicionSumaPromeMediana("promedio", promedio.getNombreIndicador(), promedio.getComparador(),
					promedio.getValorAcomparar(), promedio.getOrdenamiento(),
					mod.armarPeriodo(promedio.getFechaInicio(), promedio.getFechaFin()));
			response.status(200);
			response.type("application/json");

			return "Condicion creada exitosamente, regrese al menu metodologia para finalizar la carga de condiciones";

		} catch (JsonParseException jpe) {
			response.status(404);
			return "Exception";
		} catch (NoSeEncuentraElIndicadorException e) {
			e.printStackTrace();
			return "Exception";
		}
	});

	get("/condicionSumatoria", (request, response) -> {
		response.status(200);
		Map<String, Object> viewObjects = new HashMap<String, Object>();
		viewObjects.put("indicadores", mod.sendIndicadores());
		viewObjects.put("templateName", "condicionSumaPromeMediana.ftl");
		return new ModelAndView(viewObjects, "main.ftl");
	}, new FreeMarkerEngine());

	post("/condicionSumatoria", (request, response) -> {
		ObjectMapper mapper = new ObjectMapper();
		try {
			SumaPromMedianaWeb sumatoria = mapper.readValue(request.body(), SumaPromMedianaWeb.class);

			mod.createCondicionSumaPromeMediana("sumatoria", sumatoria.getNombreIndicador(),
					sumatoria.getComparador(), sumatoria.getValorAcomparar(), sumatoria.getOrdenamiento(),
					mod.armarPeriodo(sumatoria.getFechaInicio(), sumatoria.getFechaFin()));
			response.status(200);
			response.type("application/json");

			return "Condicion creada exitosamente, regrese al menu metodologia para finalizar la carga de condiciones";

		} catch (JsonParseException jpe) {
			response.status(404);
			return "Exception";
		} catch (NoSeEncuentraElIndicadorException e) {
			e.printStackTrace();
			return "Exception";
		}
	});

	get("/condicionDecreciente", (request, response) -> {
		response.status(200);
		Map<String, Object> viewObjects = new HashMap<String, Object>();
		viewObjects.put("indicadores", mod.sendIndicadores());
		viewObjects.put("templateName", "condicionDecreciente.ftl");
		return new ModelAndView(viewObjects, "main.ftl");
	}, new FreeMarkerEngine());

	post("/condicionDecreciente", (request, response) -> {
		ObjectMapper mapper = new ObjectMapper();
		try {
			DecrecienteWeb decreciente = mapper.readValue(request.body(), DecrecienteWeb.class);

			mod.createCondicionDecreciente(decreciente.getNombreIndicador(), decreciente.getAnios());

			response.status(200);
			response.type("application/json");

			return "Condicion creada exitosamente, regrese al menu metodologia para finalizar la carga de condiciones";

		} catch (JsonParseException jpe) {
			response.status(404);
			return "Exception";
		} catch (NoSeEncuentraElIndicadorException e) {
			e.printStackTrace();
			return "No se encuentra el indicador";
		}
	});

	get("/condicionCreciente", (request, response) -> {
		response.status(200);
		Map<String, Object> viewObjects = new HashMap<String, Object>();
		viewObjects.put("indicadores", mod.sendIndicadores());
		viewObjects.put("templateName", "condicionCreciente.ftl");

		return new ModelAndView(viewObjects, "main.ftl");
	}, new FreeMarkerEngine());

	post("/condicionCreciente", (request, response) -> {
		ObjectMapper mapper = new ObjectMapper();
		try {
			CrecienteWeb creciente = mapper.readValue(request.body(), CrecienteWeb.class);

			mod.createCondicionCreciente(creciente.getNombreIndicador(), creciente.getAnios());
			response.status(200);
			response.type("application/json");
			return "Condicion creada exitosamente, regrese al menu metodologia para finalizar la carga de condiciones";

		} catch (JsonParseException jpe) {
			response.status(404);
			return "Exception";
		} catch (NoSeEncuentraElIndicadorException e) {
			e.printStackTrace();
			return "No se encontro el indicador";
		}
	});
	

	get("/condicionLongevidad", (request, response) -> {
		response.status(200);
		Map<String, Object> viewObjects = new HashMap<String, Object>();
		viewObjects.put("templateName", "condicionLongevidad.ftl");
		return new ModelAndView(viewObjects, "main.ftl");
	}, new FreeMarkerEngine());

	post("/condicionLongevidad", (request, response) -> {
		ObjectMapper mapper = new ObjectMapper();
		try {
			LongevidadWeb lon = mapper.readValue(request.body(), LongevidadWeb.class);

			mod.createCondicionLongevidad(lon.getAnios());

			response.status(200);
			response.type("application/json");

			return "Condicion creada exitosamente, regrese al menu metodologia para finalizar la carga de condiciones";

		} catch (JsonParseException jpe) {
			response.status(404);
			return "Exception";
		}
	});
}
}