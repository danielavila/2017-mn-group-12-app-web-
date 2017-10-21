package ar.edu.utn.dds.modeloWeb;

public class IndicadorWeb implements Validable {
	

	private String nombre;
	private String expresion;



	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getExpresion() {
		return expresion;
	}

	public void setExpresion(String expresion) {
		this.expresion = expresion;
	}

	@Override
	public boolean isValid() {
		if ( nombre == null || expresion == null)
			return false;
		if ( nombre.isEmpty() || expresion.isEmpty())
			return false;
		if ( !nombre.startsWith("i_"))
			return false;
		
		return true;
	}

}
