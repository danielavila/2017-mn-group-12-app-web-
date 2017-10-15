package Model;

public class TablaEmpresa {
	private String id ;
	private String nombre;
	private String fechaInscripcion;

	
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
