package Model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.script.ScriptException;

import User.IndicadorCalculable;
import ar.edu.utn.dds.entidades.Empresas;
import ar.edu.utn.dds.entidades.Indicadores;
import ar.edu.utn.dds.excepciones.NoHayEmpresasQueCumplanLaCondicionException;
import ar.edu.utn.dds.excepciones.NoSeEncuentraElIndicadorException;
import ar.edu.utn.dds.excepciones.NoSeEncuentraLaCuentaEnElPeriodoException;
import ar.edu.utn.dds.excepciones.NoSeEncuentraLaCuentaException;
import ar.edu.utn.dds.excepciones.NoSeEncuentraLaEmpresaException;
import ar.edu.utn.dds.excepciones.NoSePudoOrdenarLaCondicionException;
import ar.edu.utn.dds.modelo.Condicion;
import ar.edu.utn.dds.modelo.Creciente;
import ar.edu.utn.dds.modelo.Decreciente;
import ar.edu.utn.dds.modelo.Empresa;
import ar.edu.utn.dds.modelo.Filtro;
import ar.edu.utn.dds.modelo.FiltroSegunEcuacion;
import ar.edu.utn.dds.modelo.Indicador;
import ar.edu.utn.dds.modelo.Longevidad;
import ar.edu.utn.dds.modelo.Mediana;
import ar.edu.utn.dds.modelo.Metodologia;
import ar.edu.utn.dds.modelo.OrdenaAplicandoCriterioOrdenamiento;
import ar.edu.utn.dds.modelo.Periodo;
import ar.edu.utn.dds.modelo.Promedio;
import ar.edu.utn.dds.modelo.PuntajeEmpresa;
import ar.edu.utn.dds.modelo.Sumatoria;
import ar.edu.utn.dds.modelo.Traductor;


public class Model {

	private Map<String, Object> empresas;

	private Map<String, Object> cuenta;
	private Map<String, Object> indicadores;

	private Metodologia meto;
	private Traductor t = new Traductor();

	/**
	 * Constructor
	 */
	public Model() {
		Empresas.setEmpresas();
		Indicadores.setIndicadores();
		t.cargarTraductor();
		this.empresas = new HashMap<>();
		this.cuenta = new HashMap<>();
		this.indicadores = new HashMap<>();
		
		Indicadores.getIndicadores().stream().forEach(unI->indicadores.put(String.valueOf(unI.getId()), unI));
		
		
	this.meto = new Metodologia();
	}


	/// CREATES

	public int createIndicador(String nombre, String expresion) {

		Indicador indicadorApersistir = new Indicador(nombre,expresion);
		Indicadores.persistirIndicador(indicadorApersistir);
		indicadores.put(String.valueOf(indicadorApersistir.getId()), indicadorApersistir);
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

	public int createCondicionCreciente(String nombre, String anios) throws NoSeEncuentraElIndicadorException {
		Creciente cre;
		cre = new Creciente(t.buscarIndicador(nombre), t);
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
	public int createCondicionSumaPromeMediana(String tipo,String nombreInd, String comparador,String valorAcomparar, String ordenamiento,Periodo periodo) throws NoSeEncuentraElIndicadorException {
		Condicion cond1;
		Condicion cond2;
		if(tipo.equals("sumatoria")) {
			Sumatoria sum = new Sumatoria(t.buscarIndicador(nombreInd), t);
		 cond1 = new FiltroSegunEcuacion(sum, Integer.valueOf(valorAcomparar), comparador, periodo);
		 cond2 = new OrdenaAplicandoCriterioOrdenamiento(sum, periodo, ordenamiento);
		}
		else {
			if (tipo.equals("promedio")) {
				Promedio promedio =new Promedio(t.buscarIndicador(nombreInd), t);
				 cond1 = new FiltroSegunEcuacion(promedio, Integer.valueOf(valorAcomparar), comparador, periodo);
				 cond2 = new OrdenaAplicandoCriterioOrdenamiento(promedio, periodo, ordenamiento);
			}
			else {
				Mediana mediana= new Mediana(t.buscarIndicador(nombreInd), t);
				 cond1 = new FiltroSegunEcuacion(mediana, Integer.valueOf(valorAcomparar), comparador, periodo);
				 cond2 = new OrdenaAplicandoCriterioOrdenamiento(mediana, periodo, ordenamiento);
			}
		}
		
		meto.agregarCondicion(cond1);
		meto.agregarCondicion(cond2);
		return 1;
	}
	public ArrayList<PuntajeEmpresa> aplicarMetodologia(String nombre,List<Empresa> empresas) throws NoHayEmpresasQueCumplanLaCondicionException, NoSeEncuentraLaEmpresaException, ScriptException, NoSePudoOrdenarLaCondicionException, NoSeEncuentraLaCuentaException, NoSeEncuentraLaCuentaEnElPeriodoException, NoSeEncuentraElIndicadorException {
	return meto.aplicarMetodologia(empresas);
		
		
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

			empresas.put(id, te);
			
			
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



	// FUNCIONES

	public String calcularIndicador(IndicadorCalculable i, Periodo p)
			throws NoSeEncuentraLaEmpresaException, NoSeEncuentraLaCuentaException,
			NoSeEncuentraLaCuentaEnElPeriodoException, NoSeEncuentraElIndicadorException {
		return String.valueOf(t.calcular(i.getNombreEmpresa(), p, i.getNombreIndicador()));
	}
	public Periodo armarPeriodo(String fechaInicio, String fechaFin) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
		LocalDate fechaI = LocalDate.parse(fechaInicio, formatter);
		LocalDate fechaF = LocalDate.parse(fechaFin, formatter);
		Periodo p = new Periodo(fechaI, fechaF);
		return p;
	}

	//// SENDS

	public List<Object> sendEmpresas() {
		List<Object> ret = new ArrayList<>(empresas.values());
		return ret;
	}


	public List<Object> sendEmpresasID() {
		List<Object> ret = new ArrayList<>(empresas.keySet());
		return ret;
	}

	public List<Object> sendCuentas() {

		List<Object> ret = new ArrayList<>(cuenta.values());
		return ret;
	}
	public List<Object> sendIndicadores() {
		
		
		List<Object> ret = new ArrayList<>(indicadores.values());
		return ret;
		 
	}

}
