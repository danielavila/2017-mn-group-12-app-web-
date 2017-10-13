package User;

public class IndicadorWeb implements Validable {
	private String id;

	private String nombre;
	private String expresion;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

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
		if (id == null || nombre == null || expresion == null)
			return false;
		if (id.isEmpty() || nombre.isEmpty() || expresion.isEmpty())
			return false;

		if (id.length() > 8) {
			return false;
		}
		return true;
	}

}
