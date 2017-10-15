package Driver;

import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.staticFileLocation;

import java.io.IOException;
import java.io.StringWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import Model.Model;
import TemplateEngine.FreeMarkerEngine;
import User.CrecienteWeb;
import User.DecrecienteWeb;
import User.IndicadorCalculable;
import User.IndicadorWeb;
import User.LongevidadWeb;
import User.MetodologiaWeb;
import ar.edu.utn.dds.entidades.Indicadores;
import ar.edu.utn.dds.excepciones.NoSeEncuentraElIndicadorException;
import ar.edu.utn.dds.modelo.Indicador;
import ar.edu.utn.dds.modelo.Periodo;
import spark.ModelAndView;

public class MainClass {

	public static void main(String[] args) {
		staticFileLocation("/public");
		MainClass s = new MainClass();
		s.init();
	}

	/**
	 * Function for Routes
	 */

	private void init() {
		Model mod = new Model();

		get("/", (request, response) -> {
			Map<String, Object> viewObjects = new HashMap<String, Object>();
			viewObjects.put("title", "TP ANUAL DDS");
			viewObjects.put("templateName", "home.ftl");
			return new ModelAndView(viewObjects, "main.ftl");
		}, new FreeMarkerEngine());

		get("/condicionDecreciente", (request, response) -> {
			response.status(200);
			Map<String, Object> viewObjects = new HashMap<String, Object>();
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

				return "Condicion creada exitosamente, regrese al menu crear metodologia para seguir agregandole condiciones";

			} catch (JsonParseException jpe) {
				response.status(404);
				return "Exception";
			} catch (NoSeEncuentraElIndicadorException e) {
				e.printStackTrace();
				return "Exception";
			}
		});

		get("/condicionCreciente", (request, response) -> {
			response.status(200);
			Map<String, Object> viewObjects = new HashMap<String, Object>();
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

				return "Condicion creada exitosamente, regrese al menu crear metodologia para seguir agregandole condiciones";

			} catch (JsonParseException jpe) {
				response.status(404);
				return "Exception";
			} catch (NoSeEncuentraElIndicadorException e) {
				e.printStackTrace();
				return "Exception";
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

				return "Condicion creada exitosamente, regrese al menu crear metodologia para seguir agregandole condiciones";

			} catch (JsonParseException jpe) {
				response.status(404);
				return "Exception";
			}
		});

		get("/crearMetodologia", (request, response) -> {
			response.status(200);
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
			}
		});

		get("/calcularIndicador", (request, response) -> {
			response.status(200);
			Map<String, Object> viewObjects = new HashMap<String, Object>();
			viewObjects.put("templateName", "calcularIndicador.ftl");
			return new ModelAndView(viewObjects, "main.ftl");
		}, new FreeMarkerEngine());

		post("/calcularIndicador", (request, response) -> {
			ObjectMapper mapper = new ObjectMapper();
			try {
				IndicadorCalculable i = mapper.readValue(request.body(), IndicadorCalculable.class);
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
				LocalDate fechaInicio = LocalDate.parse(i.getFechaInicio(), formatter);
				LocalDate fechaFin = LocalDate.parse(i.getFechaFin(), formatter);
				Periodo p = new Periodo(fechaInicio, fechaFin);

				if (mod.checkIndicadorCalculable(i)) {

					String resultado = mod.calcularIndicador(i, p);
					response.status(200);
					response.type("application/json");
					System.out.println(resultado);
					return resultado;
				} else {
					response.status(400);
					response.type("application/json");
					return "La empresa no existe";
				}
			} catch (JsonParseException jpe) {
				response.status(404);
				return "Exception";
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
				if (mod.checkIndicador(i.getId())) {
					int id = mod.createIndicador(i.getId(), i.getNombre(), i.getExpresion());
					Indicador indicadorApersistir = new Indicador(i.getNombre(), i.getExpresion());
					Indicadores.persistirIndicador(indicadorApersistir);
					response.status(200);
					response.type("application/json");
					return id;
				} else {
					response.status(400);
					response.type("application/json");
					return "Ya existe el indicador";
				}
			} catch (JsonParseException jpe) {
				response.status(404);
				return "Exception";
			}
		});

		get("/getEmpresas", (request, response) -> {
			response.status(200);
			mod.getEmpresas();
			Map<String, Object> viewObjects = new HashMap<String, Object>();
			viewObjects.put("templateName", "mostrarEmpresa.ftl");
			return new ModelAndView(viewObjects, "main.ftl");
		}, new FreeMarkerEngine());

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

		get("/getempresas", (request, response) -> {
			response.status(200);
			return toJSON(mod.sendEmpresas());
		});

	}

	/**
	 * This function converts an Object to JSON String
	 * 
	 * @param obj
	 */
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
