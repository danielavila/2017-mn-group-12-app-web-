package Model;



public class TablaEmpresa {
	private String nombre;
	private String fechaInscripcion;
	private String cuentas;

	
	public void setLinkCuentas(String id) {
		
			this.cuentas ="http://localhost:4567/getCuentas/"+id;
	
	}
	
	public String getLinkCuentas() {
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
}
