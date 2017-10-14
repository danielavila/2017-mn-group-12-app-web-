package Model;

import java.net.MalformedURLException;
import java.net.URL;

public class TablaEmpresa {
	private String id ;
	private String nombre;
	private String fechaInscripcion;
	private URL cuentas;

	
	public void setLinkCuentas(String id) throws MalformedURLException {
		URL domain = new URL("http://localhost:4567/getEmpresas");
		URL url = new URL(domain + "/"+id);

			this.cuentas =url;
	}
	
	public URL getLinkCuentas() {
		return cuentas;
	}
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getFechaInscripcion() {
		return fechaInscripcion;
	}
	public void setFechaInscripcion(String fechaInscripcion) {
		this.fechaInscripcion = fechaInscripcion;
	}

	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
