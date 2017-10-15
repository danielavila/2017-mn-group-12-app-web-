package Model;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import User.IndicadorCalculable;
import ar.edu.utn.dds.entidades.Empresas;
import ar.edu.utn.dds.entidades.Indicadores;
import ar.edu.utn.dds.excepciones.NoSeEncuentraElIndicadorException;
import ar.edu.utn.dds.excepciones.NoSeEncuentraLaCuentaEnElPeriodoException;
import ar.edu.utn.dds.excepciones.NoSeEncuentraLaCuentaException;
import ar.edu.utn.dds.excepciones.NoSeEncuentraLaEmpresaException;
import ar.edu.utn.dds.modelo.Condicion;
import ar.edu.utn.dds.modelo.Creciente;
import ar.edu.utn.dds.modelo.Decreciente;
import ar.edu.utn.dds.modelo.Empresa;
import ar.edu.utn.dds.modelo.Filtro;
import ar.edu.utn.dds.modelo.Longevidad;
import ar.edu.utn.dds.modelo.Metodologia;
import ar.edu.utn.dds.modelo.Periodo;
import ar.edu.utn.dds.modelo.Traductor;


public class Model {

	private Map<String, Object> empresa;
	private Map<String, Object> cuenta;
	private Map<String, Object> indicador;

	private Metodologia meto;
	private Traductor t = new Traductor();

	/**
	 * Constructor
	 */
	public Model() {
		Empresas.setEmpresas();
		Indicadores.setIndicadores();
		t.cargarTraductor();
		this.empresa = new HashMap<>();
		this.cuenta = new HashMap<>();
		this.indicador = new HashMap<>();
	this.meto = new Metodologia();
	}


	/// CREATES

	public int createIndicador(String id, String nombre, String expresion) {
		TablaIndicador ind = new TablaIndicador();
		ind.setId(id);
		ind.setNombre(nombre);
		ind.setExpresion(expresion);
		indicador.put(id, ind);
		return 1;
	}

	public int createMetodologia(String nombre) {

		meto.setNombre(nombre);
		return 1;
	}

	public int createCondicionLongevidad(String anios) {
		Longevidad lon = new Longevidad(t);
		Condicion cond = new Filtro(lon, Integer.parseInt(anios));
		meto.agregarCondicion(cond);

		return 1;
	}

	public int createCondicionCreciente(String nombreInd, String anios) throws NoSeEncuentraElIndicadorException {
		Creciente cre;

		cre = new Creciente(t.buscarIndicador(nombreInd), t);

		Condicion condcre = new Filtro(cre, Integer.valueOf(anios));
		meto.agregarCondicion(condcre);

		return 1;
	}

	public int createCondicionDecreciente(String nombreInd, String anios) throws NoSeEncuentraElIndicadorException {
		Decreciente decre;

		decre = new Decreciente(t.buscarIndicador(nombreInd), t);

		Condicion condDecre = new Filtro(decre, Integer.valueOf(anios));
		meto.agregarCondicion(condDecre);

		return 1;
	}

	/////// GETS
	public void getEmpresas() {

		Empresas.getEmpresas().forEach(unaE -> {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd LLLL yyyy");
			TablaEmpresa te = new TablaEmpresa();
			String id = String.valueOf(unaE.getId());
			te.setId(id);
			te.setNombre(unaE.getNombre());
			te.setFechaInscripcion(unaE.getFechaInscripcion().format(formatter));

			empresa.put(id, te);
		});

	}

	public void getCuentas(String id) {
		this.cuenta.clear();
		Empresas.getEmpresas().forEach(unaE -> {
			if (String.valueOf(unaE.getId()).equals(id)) {
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

	////// CHECKS

	public boolean checkIndicadorCalculable(IndicadorCalculable i) {
		if (Empresas.getEmpresas().stream().noneMatch(unaE -> unaE.getNombre().equals(i.getNombreEmpresa()))) {
			return false;
		}

		return true;
	}

	public boolean checkIndicador(String id) {
		Iterator<Map.Entry<String, Object>> it = indicador.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, Object> pair = it.next();
			TablaIndicador i = (TablaIndicador) pair.getValue();
			if ((i.getId().equals(id)))
				return false;
		}
		return true;
	}

	// FUNCIONES

	public String calcularIndicador(IndicadorCalculable i, Periodo p)
			throws NoSeEncuentraLaEmpresaException, NoSeEncuentraLaCuentaException,
			NoSeEncuentraLaCuentaEnElPeriodoException, NoSeEncuentraElIndicadorException {
		return String.valueOf(t.calcular(i.getNombreEmpresa(), p, i.getNombreIndicador()));
	}

	//// SENDS

	public List<Object> sendEmpresas() {
		List<Object> ret = new ArrayList<>(empresa.values());
		return ret;
	}

	public List<Object> sendEmpresasID() {
		List<Object> ret = new ArrayList<>(empresa.keySet());
		return ret;
	}

	public List<Object> sendCuentas() {

		List<Object> ret = new ArrayList<>(cuenta.values());
		return ret;
	}

}
