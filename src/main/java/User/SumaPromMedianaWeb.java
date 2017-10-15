package User;

public class SumaPromMedianaWeb {
	private String nombreIndicador;
	private String comparador;
	private String valorAcomparar;
	private String ordenamiento;

	public String getNombreIndicador() {
		return nombreIndicador;
	}

	public void setNombreIndicador(String nombreIndicador) {
		this.nombreIndicador = nombreIndicador;
	}

	public String getComparador() {
		return comparador;
	}

	public void setComparador(String comparador) {
		this.comparador = comparador;
	}

	public String getValorAcomparar() {
		return valorAcomparar;
	}

	public void setValorAcomparar(String valorAcomparar) {
		this.valorAcomparar = valorAcomparar;
	}

	public String getOrdenamiento() {
		return ordenamiento;
	}

	public void setOrdenamiento(String ordenamiento) {
		this.ordenamiento = ordenamiento;
	}

	public String getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(String fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public String getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(String fechaFin) {
		this.fechaFin = fechaFin;
	}

	private String fechaInicio;
	private String fechaFin;
}
