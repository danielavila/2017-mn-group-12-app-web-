package Model;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import ar.edu.utn.dds.entidades.Empresas;
import ar.edu.utn.dds.modelo.Empresa;

/**
 *
 * @author prash_000
 */
public class Model {
    private Map<String, Object> user;
    private Map<String, Object> empresa;

    /**
     * Constructor
     */
    public Model() {
        this.user = new HashMap<>();
        this.empresa = new HashMap<>();
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
    public void getEmpresas(){
    	
    	 Empresas.setEmpresas();
    	 Empresas.getEmpresas().forEach(unaE->{
    		 DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd LLLL yyyy");
    		 TablaEmpresa te=new TablaEmpresa();
    		 te.setNombre(unaE.getNombre());
    		 te.setFechaInscripcion(unaE.getFechaInscripcion().format(formatter));
    		 empresa.put(String.valueOf(unaE.getId()), te);
    	 });
    	
    }
    public List<Empresa> getEmpHandle() {
    	Empresas.setEmpresas();
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

    public List sendUsersId() {
        List<Object> ret = new ArrayList<>(user.keySet());
        return ret;
    }
}
