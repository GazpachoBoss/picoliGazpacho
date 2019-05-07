package modelo.vista;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Stack;

public class MinisterioEconomia {
	private float fondosEstado;
	private final float NV_GENERAL = 365;

	public MinisterioEconomia() {
		this.fondosEstado = 96725;
	}

	public float getFondosEstado() {
		return fondosEstado;
	}

	public void setFondosEstado(float fondosEstado) {
		this.fondosEstado = fondosEstado;
	}

	public void cobrarImpuestos(Stack<Ser> trabajadores) {
		for (Ser m : trabajadores) {
			fondosEstado += m.getSueldo() / 4;
			m.setSueldo(m.getSueldo() - m.getSueldo() / 4);
			m.setAhorros(m.getAhorros() + m.getSueldo());
			m.setSueldo(0f);
		}
	}

	public void pagarSubvenciones(LinkedList<Ser> demandantes, ArrayList<Ser> poblacion) {
		int numeroMenores = 0;

		for (Ser p : poblacion) {
			if (p.getEdad() < 18) {
				numeroMenores++;
			}
		}

		pagarSubvencionesMenores(poblacion, numeroMenores);
		pagarSubvencionesDemandantes(demandantes);
		pagarSubvencionesJubilados(poblacion);
	}

	private void pagarSubvencionesDemandantes(LinkedList<Ser> demandantes) {
		for (Ser p : demandantes) {
			if (fondosEstado >= NV_GENERAL / 2 && p.getEdad() < 65 && p.getEdad() >= 18
					&& p.getAhorros() < NV_GENERAL) {
				p.setAhorros(NV_GENERAL / 2);
				fondosEstado -= NV_GENERAL / 2;
			}
		}
	}

	private void pagarSubvencionesJubilados(ArrayList<Ser> poblacion) {
		for (Ser p : poblacion) {
			if (fondosEstado >= NV_GENERAL / 2 && p.getEdad() >= 65 && p.getAhorros() < NV_GENERAL / 2) {
				p.setAhorros(NV_GENERAL / 2);
				fondosEstado -= NV_GENERAL / 2;
			}
		}

	}

	private void pagarSubvencionesMenores(ArrayList<Ser> poblacion, int numeroMenores) {
		if (comprobarFondosEstadoParaSubvenciones(numeroMenores)) {
			for (int i = 0; i < numeroMenores; i++) {
				poblacion.get(i).setAhorros(NV_GENERAL / 2);
				fondosEstado -= NV_GENERAL / 2;
			}
		} else {
			for (int i = 0; i < numeroMenores; i++) {
				poblacion.get(i).setAhorros(fondosEstado / numeroMenores);
			}
			fondosEstado = 0;
		}
	}

	public boolean comprobarFondosEstadoParaSubvenciones(int numeroSeres) {
		boolean subvencionable = false;

		if (fondosEstado >= numeroSeres * (NV_GENERAL / 2)) {
			subvencionable = true;
		}

		return subvencionable;
	}

}
