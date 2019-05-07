package modelo.vista;

import java.util.ArrayList;
import java.util.Stack;

public class Estado {
	private MinisterioEconomia finanzas = new MinisterioEconomia();
	private MinisterioIndustria industrias = new MinisterioIndustria();
	private MinisterioTiempo controlTiempo = new MinisterioTiempo();
	private Censo poblacion = new Censo();
	private float crecimientoAnual = 0f;
	private float produccionAnterior = 0f;

	public Estado() {
		gestionarPersonal();

	}

	public void play() {
		gestionarPersonal();
		gestionSeres();
		industrias.reorganizarIndustrias();
		industrias.eliminaIndustrias();
		controlTiempo.realizarCiclo(poblacion.getPoblacion());

	}

	public float calcularCrecimientoAnual() {

		float produccionActual = industrias.getProduccion();
		crecimientoAnual = ((produccionActual - produccionAnterior) / produccionAnterior) * 100;
		produccionAnterior = produccionActual;
		return crecimientoAnual;
	}

	private void gestionarPersonal() {
		industrias.jubilarTrabajadores();
		Stack<Ser> trabajadores = new Stack<>();
		for (Factorias factoria : industrias.getIndustrias()) {
			trabajadores.addAll(factoria.getTrabajadores());
		}
		float cantidad = (industrias.getDemanda() - industrias.getProduccion()) / 1000;
		if (cantidad > 0) {
			industrias.contratarDemandantes(poblacion.getDemandantes());
		} else if (cantidad < 0) {
			industrias.despedirTrabajadores(poblacion.getDemandantes());
		}
		industrias.calcularProduccionTotal();
		poblacion.actualizarDemandantes(trabajadores);
	}

	private void gestionSeres() {
		poblacion.jubilarSer();
		poblacion.nacimiento(industrias.getDemanda(), industrias.getProduccion());
		finanzas.pagarSubvenciones(poblacion.getDemandantes(), poblacion.getPoblacion());

		for (Factorias i : industrias.getIndustrias()) {
			finanzas.cobrarImpuestos(i.getTrabajadores());
		}
		poblacion.morir();
		industrias.eliminarTrabajadoresMuertos(poblacion.morir());
		poblacion.organizarColeccionciones();
		industrias.pagarSueldos();
	}

	public MinisterioEconomia getFinanzas() {
		return finanzas;
	}

	public MinisterioIndustria getIndustrias() {
		return industrias;
	}

	public MinisterioTiempo getControlTiempo() {
		return controlTiempo;
	}

	public Censo getPoblacion() {
		return poblacion;
	}

}
