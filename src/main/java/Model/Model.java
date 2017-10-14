package Model;

import java.net.MalformedURLException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import User.IndicadorCalculable;
import ar.edu.utn.dds.entidades.Empresas;
import ar.edu.utn.dds.entidades.Indicadores;
import ar.edu.utn.dds.modelo.Empresa;

/**
 *
 * @author prash_000
 */
public class Model {
    private Map<String, Object> user;
    private Map<String, Object> empresa;
    private Map<String, Object> cuenta;
    private Map<String, Object> indicador;
    private Map<String, Object> indicadores;

    /**
     * Constructor
     */
    public Model() {
    	Empresas.setEmpresas();
    	Indicadores.setIndicadores();
        this.user = new HashMap<>();
        this.empresa = new HashMap<>();
        this.cuenta = new HashMap<>();
        this.indicador=new HashMap<>();
        this.indicadores=new HashMap<>();
    }
    
    /**
     * This function Creates a new User
     * @param id
     * @param fname
     * @param mname
     * @param lname
     * @param age
     * @param gender
     * @param phone
     * @param zip
     * @return
     */
    public int createUser(String id, String fname, String mname, String lname, int age, char gender, String phone, long zip) {
        UserTable usr = new UserTable();
        usr.setId(id);
        usr.setNombre(fname);
        usr.setSegudoNombre(mname);
        usr.setApellido(lname);
        usr.setEdad(age);
        usr.setSexo(gender);
        usr.setTelefono(phone);
        usr.setCodigoPostal(zip);
        user.put(id,usr);
        return 1;
    }
    
    
    public int createIndicador(String id, String nombre, String expresion) {       
        TablaIndicador ind=new TablaIndicador();
        ind.setId(id);
        ind.setNombre(nombre);
        ind.setExpresion(expresion);    
       indicador.put(id,ind);
        return 1;
    }
    
    
    
    
    public void getEmpresas(){
    	
    	
    	 Empresas.getEmpresas().forEach(unaE->{
    		 DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd LLLL yyyy");
    		 TablaEmpresa te=new TablaEmpresa();
    		 String id = String.valueOf(unaE.getId());
    		 te.setId(id);
    		 te.setNombre(unaE.getNombre());
    		 te.setFechaInscripcion(unaE.getFechaInscripcion().format(formatter));
    		 try {
				te.setLinkCuentas(id);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		 empresa.put(id, te);
    	 });
    	
    }
    
    
    public void getCuentas(String id) {
    	this.cuenta.clear();
    	Empresas.getEmpresas().forEach(unaE -> {
    		if(String.valueOf(unaE.getId()).equals(id)) {
    			unaE.getCuentas().forEach(unaC -> {
    				TablaCuenta tc = new TablaCuenta();
    				tc.setNombre(unaC.getNombre());
    				tc.setValor(unaC.getValor());
    				cuenta.put(String.valueOf(unaC.getId()), tc);
    			});
    		}
    	});
    }
    
    
    public List<Empresa> getEmpHandle() {
    	
    	return Empresas.getEmpresas();
    }
    
    /**
     * Check to find if a user is available
     * @param id
     * @return
     */
    public boolean checkUser(String id) {
        Iterator it = user.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            UserTable u = (UserTable)pair.getValue();
            if((u.getId().equals(id)))
                return false;
        }
        return true;
    }
    public boolean checkIndicador(String id) {
        Iterator it = indicador.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            TablaIndicador i = (TablaIndicador)pair.getValue();
            if((i.getId().equals(id)))
                return false;
        }
        return true;
    }
    
    /**
     * Similar function to update a user
     * @param id
     * @param fname
     * @param mname
     * @param lname
     * @param age
     * @param gender
     * @param phone
     * @param zip
     * @return
     */
    public int updateUser(String id, String fname, String mname, String lname, int age, char gender, String phone, long zip) {
        UserTable usr = (UserTable)user.get(id);
        usr.setNombre(fname);
        usr.setSegudoNombre(mname);
        usr.setApellido(lname);
        usr.setEdad(age);
        usr.setSexo(gender);
        usr.setTelefono(phone);
        usr.setCodigoPostal(zip);
        user.put(id,usr);
        return 1;
    }

    public boolean removeUser(String id) {
        if(!checkUser(id)) {
            user.remove(id);
            return true;    
        }
        return false;
    }
    
    /**
     * This function creates a list of all users
     * @return
     */
    public List sendElements() {
        List<Object> ret = new ArrayList<>(user.values());
        return ret;
    }
    
    public List sendEmpresas() {
    	List<Object> ret = new ArrayList<>(empresa.values());
    	return ret;
    }
    public List sendEmpresasID() {
    	List<Object> ret = new ArrayList<>(empresa.keySet());
    	return ret;
    }
    
    public List sendCuentas() {

    	List<Object> ret = new ArrayList<>(cuenta.values());
    	return ret;
    }

    public List sendUsersId() {
        List<Object> ret = new ArrayList<>(user.keySet());
        return ret;
    }
    
    public List sendNombresInd() {
    	Indicadores.getIndicadores().stream().forEach(unI->
    	indicadores.put(String.valueOf(unI.getId()), unI.getNombre()));
    	List<Object> ret = new ArrayList<>(indicadores.values());
        return ret ;
    }

	public boolean checkIndicadorCalculable(IndicadorCalculable i) {
		if(Empresas.getEmpresas().stream().noneMatch(unaE->unaE.getNombre().equals(i.getNombreEmpresa()))) {
			return false;
		}
		
		return true;
	}
    
}
