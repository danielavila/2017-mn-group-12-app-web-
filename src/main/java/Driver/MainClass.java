package Driver;

import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.staticFileLocation;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.PersistenceException;

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
import User.MetodologiaAplicable;
import User.MetodologiaWeb;
import User.SumaPromMedianaWeb;
import ar.edu.utn.dds.entidades.Empresas;
import ar.edu.utn.dds.modelo.Empresa;
import ar.edu.utn.dds.excepciones.MetodologiaYaExisteException;
import ar.edu.utn.dds.excepciones.NoHayCondicionesException;
import ar.edu.utn.dds.excepciones.NoHayEmpresasQueCumplanLaCondicionException;
import ar.edu.utn.dds.excepciones.NoSeEncuentraElIndicadorException;
import ar.edu.utn.dds.excepciones.NoSeEncuentraLaCuentaEnElPeriodoException;
import ar.edu.utn.dds.excepciones.NoSeEncuentraLaCuentaException;
import ar.edu.utn.dds.excepciones.NoSeEncuentraLaEmpresaException;
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

				return "Condicion creada exitosamente, regrese al menu crear metodologia para seguir agregandole condiciones";

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

				return "Condicion creada exitosamente, regrese al menu crear metodologia para seguir agregandole condiciones";

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

				return "Condicion creada exitosamente, regrese al menu crear metodologia para seguir agregandole condiciones";

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

				return "Condicion creada exitosamente, regrese al menu crear metodologia para seguir agregandole condiciones";

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
				return "Condicion creada exitosamente, regrese al menu crear metodologia para seguir agregandole condiciones";

			} catch (JsonParseException jpe) {
				response.status(404);
				return "Exception";
			} catch (NoSeEncuentraElIndicadorException e) {
				e.printStackTrace();
				return "No se encontro el indicador";
			}
		});
		

		get("condicionLongevidad", (request, response) -> {
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

		get("/calcularIndicador", (request, response) -> {
			response.status(200);
			Map<String, Object> viewObjects = new HashMap<String, Object>();
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

		get("/getEmpresas", (request, response) -> {
			response.status(200);
			mod.getEmpresas();
			Map<String, Object> viewObjects = new HashMap<String, Object>();
			viewObjects.put("empresas", mod.sendEmpresas());
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
