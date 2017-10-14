package Driver;

import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.put;
import static spark.Spark.staticFileLocation;

import java.io.IOException;
import java.io.StringWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import Model.Model;
import TemplateEngine.FreeMarkerEngine;
import User.IndicadorCalculable;
import User.IndicadorWeb;
import User.User;
import ar.edu.utn.dds.entidades.Empresas;
import ar.edu.utn.dds.entidades.Indicadores;
import ar.edu.utn.dds.excepciones.NoSeEncuentraElIndicadorException;
import ar.edu.utn.dds.excepciones.NoSeEncuentraLaCuentaEnElPeriodoException;
import ar.edu.utn.dds.modelo.Empresa;
import ar.edu.utn.dds.modelo.Indicador;
import ar.edu.utn.dds.modelo.Periodo;
import ar.edu.utn.dds.modelo.Traductor;
import spark.ModelAndView;
import spark.Spark;

/**
 *
 * @author prash_000
 */
public class MainClass {
    
    /**
     *  Entry Point
     * @param args
     */
    public static void main(String[] args) {
        staticFileLocation("/public");
        MainClass s = new MainClass();
        s.init();
    }
    
    /**
     *  Function for Routes
     */
    private void init() {
        Model mod = new Model();

        get("/", (request, response) -> {
           Map<String, Object> viewObjects = new HashMap<String, Object>();
           viewObjects.put("title", "TP ANUAL DDS");
           viewObjects.put("templateName", "home.ftl");
           return new ModelAndView(viewObjects, "main.ftl");
        }, new FreeMarkerEngine());
        
        /////////////////////////////////////////////////////////////////
        
        get("/calcularIndicador", (request, response) -> {    	  
        	response.status(200);          
           Map<String, Object> viewObjects = new HashMap<String, Object>();        
            viewObjects.put("templateName", "calcularIndicador.ftl");        
            return new ModelAndView (viewObjects, "main.ftl");
        }, new FreeMarkerEngine());
        
        post("/calcularIndicador", (request, response) -> {
            ObjectMapper mapper = new ObjectMapper();
            try {
                IndicadorCalculable i = mapper.readValue(request.body(),IndicadorCalculable.class);
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");            
                LocalDate fechaInicio = LocalDate.parse(i.getFechaInicio(), formatter);
                LocalDate fechaFin = LocalDate.parse(i.getFechaFin(), formatter);
                Periodo p=new Periodo(fechaInicio,fechaFin);
                Traductor t= new Traductor();
                List<Empresa>empresas=Empresas.getEmpresas();
                t.setEmpresas((ArrayList<Empresa>) empresas);
             
                t.setIndicadores((ArrayList<Indicador>) Indicadores.getIndicadores());
                if(mod.checkIndicadorCalculable(i)) {
                	
                	String resultado=String.valueOf(t.calcular(i.getNombreEmpresa(), p, i.getNombreIndicador()));
                    response.status(200);
                    response.type("application/json");               
                   return resultado; 
                }
                else {
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
                IndicadorWeb i = mapper.readValue(request.body(),IndicadorWeb.class);
                if (!i.isValid()) {
                    response.status(400);
                    return "Corregir los campos";
                }
                if(mod.checkIndicador(i.getId())) {
                    int id = mod.createIndicador(i.getId(),i.getNombre(),i.getExpresion());
                    Indicador indicadorApersistir =new Indicador(i.getNombre(),i.getExpresion());
                    Indicadores.persistirIndicador(indicadorApersistir);
                    response.status(200);
                    response.type("application/json");
                    return id;
                }
                else {
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
  
            return new ModelAndView (viewObjects, "main.ftl");
        }, new FreeMarkerEngine());
       
        get("/getCuentas/:id", (request, response) -> {
        	response.status(200); 
        	String id = request.params(":id");
           Map<String, Object> viewObjects = new HashMap<String, Object>();
            viewObjects.put("templateName", "mostrarCuentas.ftl");
           mod.getCuentas(id);
           response.redirect("/getCuentas");
            return new ModelAndView (viewObjects, "main.ftl");
        }, new FreeMarkerEngine());
        
        get("/getcuentas",(request,response) -> {
        	response.status(200);
        	return toJSON(mod.sendCuentas());
        });
      
        
        get("/getempresas", (request, response) -> {
            response.status(200);
            return toJSON(mod.sendEmpresas());
        });
        
        
        
        
        /////////////////////////////////////////////////////////////////

        
        get("/createUser", (request, response) -> {
           Map<String, Object> viewObjects = new HashMap<String, Object>();
           viewObjects.put("templateName", "createForm.ftl");
           return new ModelAndView(viewObjects, "main.ftl");
        }, new FreeMarkerEngine());
        
        post("/createUser", (request, response) -> {
            ObjectMapper mapper = new ObjectMapper();
            try {
                User u = mapper.readValue(request.body(), User.class);
                if (!u.isValid()) {
                    response.status(400);
                    return "Correct the fields";
                }
                if(mod.checkUser(u.getId())) {
                    int id = mod.createUser(u.getId(), u.getFirstName(), u.getMiddleName(), u.getLastName(),
                    u.getAge(), u.getGender(), u.getPhone(), u.getZip());
                    response.status(200);
                    response.type("application/json");
                    return id;
                }
                else {
                    response.status(400);
                    response.type("application/json");
                    return "User Already Exists";
                }
                } catch (JsonParseException jpe) {
                    response.status(404);
                    return "Exception";
                }
        });
        
        get("/getAllUsers", (request, response) -> {
            response.status(200);
            Map<String, Object> viewObjects = new HashMap<String, Object>();
            viewObjects.put("templateName", "showUser.ftl");
            return new ModelAndView(viewObjects, "main.ftl");
        }, new FreeMarkerEngine());

        get("/getusers", (request, response) -> {
            response.status(200);
            return toJSON(mod.sendElements());
        });


        
        get("/getusers", (request, response) -> {
            response.status(200);
            return toJSON(mod.sendElements());
        });
    
        
        
        
        

        get("/removeUser", (request, response) -> {
           Map<String, Object> viewObjects = new HashMap<String, Object>();
           viewObjects.put("templateName", "removeUser.ftl"); 
           viewObjects.put("users", toJSON(mod.sendUsersId()));
           return new ModelAndView(viewObjects, "main.ftl");
        }, new FreeMarkerEngine());

        put("/removeUser/:id", (request, response) -> {
            String id = request.params(":id");
            Map<String, Object> viewObjects = new HashMap<String, Object>();
            if(mod.removeUser(id)) return "User Removed";
            else return "No Such User Found";
        });
        
        get("/updateUser", (request, response) -> {
           Map<String, Object> viewObjects = new HashMap<String, Object>();
           viewObjects.put("templateName", "updateForm.ftl");
           return new ModelAndView(viewObjects, "main.ftl");
        }, new FreeMarkerEngine());
        
        post("/updateUser", (request, response) -> {
            ObjectMapper mapper = new ObjectMapper();
            try {
                User u = mapper.readValue(request.body(), User.class);
                if (!u.isValid()) {
                    response.status(400);
                    return "Correct the fields";
                }
                if(!mod.checkUser(u.getId())) {
                    int id = mod.updateUser(u.getId(), u.getFirstName(), u.getMiddleName(), u.getLastName(),
                    u.getAge(), u.getGender(), u.getPhone(), u.getZip());
                    response.status(200);
                    response.type("application/json");
                    return id;
                }
                else {
                    response.status(404);
                    return "User Does Not Exists";
                }
                } catch (JsonParseException jpe) {
                    response.status(404);
                    return "Exception";
                }
        });
        
    }
    
    /**
     *  This function converts an Object to JSON String
     * @param obj
     */
    private static String toJSON(Object obj) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            StringWriter sw = new StringWriter();
            mapper.writeValue(sw, obj);
            return sw.toString();
        }
        catch(IOException e) {
            System.err.println(e);
        }
        return null;
    }
    
}
