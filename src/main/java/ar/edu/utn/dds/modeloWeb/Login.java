package ar.edu.utn.dds.modeloWeb;

public class Login {
	private String usuario;
	private String contrasenia;
	
	public Login(){
        usuario = "mati";
        contrasenia = "1234";
    }
	
	
	
	public String getNombre() {
		return usuario;
	}
	public void setNombre(String usuario) {
		this.usuario = usuario;
	}
	public String getContrasenia() {
		return contrasenia;
	}
	public void setContrasenia(String contrasenia) {
		this.contrasenia = contrasenia;
	}
	
}
