package modelo.vista;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Stack;

public class MinisterioIndustria {
	private Stack<Factorias> industrias = new Stack<Factorias>();
	private float demanda;
	private float produccion;
	private final int MIN_TRABAJADORES = 300;
	private final int MAX_TRABAJADORES = 1000;
	int numeroTrabajadoresNecesarios = 0;

	public float getProduccion() {
		return produccion;
	}

	public int getNumeroTrabajadoresNecesarios() {
		return numeroTrabajadoresNecesarios;
	}

	public int totalTrabajadores() {
		int total = 0;

		for (Factorias factorias : industrias) {
			total += factorias.getNumeroTrabajadores();
		}
		return total;
	}

	public MinisterioIndustria() {
		this.demanda = 96725;
		industrias.add(new Factorias(0));
	}

	public Stack<Factorias> getIndustrias() {
		return industrias;
	}

	public void setIndustrias(Stack<Factorias> industrias) {
		this.industrias = industrias;
	}

	public float getDemanda() {
		return demanda;
	}

	public void setDemanda(float demanda) {
		this.demanda = demanda;
	}

	public void aumentarDemanda() {
		demanda += demanda / 10;
	}

	public void reducirDemanda() {
		demanda -= demanda / 10;
	}

	public void reorganizarIndustrias() {
		for (Factorias i : industrias) {
			if (i.getNumeroTrabajadores() < MIN_TRABAJADORES) {
				for (Factorias m : industrias) {
					if (m.getNumeroTrabajadores() > MIN_TRABAJADORES) {
						do {
							i.contratarTrabajador(m.despedirTrabajador());
						} while (i.getNumeroTrabajadores() >= MIN_TRABAJADORES
								|| m.getNumeroTrabajadores() <= MIN_TRABAJADORES);
					}
				}
			}
		}
	}

	public void altaTrabajador(Ser ser) {
		boolean creacion = true;
		for (Factorias i : industrias) {
			if (i.getNumeroTrabajadores() < MAX_TRABAJADORES) {
				i.contratarTrabajador(ser);
				creacion = false;
			}
		}
		if (creacion) {
			industrias.add(new Factorias(industrias.firstElement().getIdFactoria() + 1));
			industrias.peek().contratarTrabajador(ser);
		}
	}

	public float calcularOcupacionTotal() {
		float porcentajeOcupacion = 0;
		int numeroIndustrias = industrias.size();

		for (Factorias i : industrias) {
			porcentajeOcupacion += i.getNumeroTrabajadores() / 1000f;
		}

		return (porcentajeOcupacion / numeroIndustrias) * 100;
	}

	public float calcularProduccionTotal() {
		produccion = 0;
		for (Factorias i : industrias) {
			if (!i.getTrabajadores().isEmpty()) {
				produccion += i.getNumeroTrabajadores() * MAX_TRABAJADORES;
			}
		}
		return produccion;
	}

	public void eliminarTrabajadoresMuertos(ArrayList<Ser> muertos) {
		for (Factorias empresa : industrias) {
			for (Ser ser : muertos) {
				if (empresa.getTrabajadores().contains(ser)) {
					empresa.trabajadorMuerto(ser);
				}
			}
		}
	}

	public void contratarDemandantes(LinkedList<Ser> demandantes) {
		float produccion = calcularProduccionTotal();
		numeroTrabajadoresNecesarios = 0;

		if (produccion < demanda) {
			numeroTrabajadoresNecesarios = (int) ((demanda - produccion) / 1000);
			for (int i = 0; i < numeroTrabajadoresNecesarios; i++) {
				if (!demandantes.isEmpty()) {
					altaTrabajador(demandantes.pop());
				}
			}
		}
	}

	public void jubilarTrabajadores() {
		for (Factorias factorias : industrias) {
			if (factorias.getTrabajadores().isEmpty()) {
				for (Ser ser : factorias.getTrabajadores()) {
					if (ser.getEdad() >= 65) {
						factorias.trabajadorMuerto(ser);
					}
				}
			}
		}
	}

	public void pagarSueldos() {

		for (Iterator<Factorias> iterator = industrias.iterator(); iterator.hasNext();) {
			Factorias factorias = (Factorias) iterator.next();

			for (Ser s : factorias.getTrabajadores()) {
				s.setSueldo(730f);
			}
		}
	}

	public void despedirTrabajadores(LinkedList<Ser> demandantes) {
		float diferenciaDemandaProduccion = produccion - demanda;
		int numeroDespidos = 0;
		if (diferenciaDemandaProduccion > 0) {
			numeroDespidos = (int) (diferenciaDemandaProduccion / 1000) + 1;
			for (int i = 0; i < numeroDespidos; i++) {
				if (!demandantes.isEmpty()) {

					bajaTrabajador(demandantes);
				}
			}
		}
	}

	public void bajaTrabajador(LinkedList<Ser> demandantes) {
		if (!industrias.isEmpty()) {
			if (!industrias.peek().getTrabajadores().isEmpty()) {
				demandantes.add(industrias.peek().despedirTrabajador());
			}
		}
	}

	public void eliminaIndustrias() {
		// primero recorro para ver la lista de plazas libres

		int numeroPlazasDisponibles = 0;
		int contadorFactorias = 0;
		Stack<Ser> trabajadoresRehubicados = new Stack<Ser>();
		for (Iterator<Factorias> iterator = industrias.iterator(); iterator.hasNext();) {
			Factorias factorias = (Factorias) iterator.next();
			numeroPlazasDisponibles += MAX_TRABAJADORES - factorias.getTrabajadores().size();
			if (contadorFactorias > 0) {
				if (factorias.getTrabajadores().size() <= numeroPlazasDisponibles) {
					trabajadoresRehubicados.addAll(factorias.getTrabajadores());
					factorias.getTrabajadores().clear();
					for (Factorias factoria : industrias) {
						for (int i = 0; factoria.getNumeroTrabajadores() < MAX_TRABAJADORES
								&& !trabajadoresRehubicados.isEmpty(); i++) {
							factoria.contratarTrabajador(trabajadoresRehubicados.pop());
							numeroPlazasDisponibles--;
						}
					}
				}
			}
			contadorFactorias++;
		}

		for (Iterator<Factorias> iterator = industrias.iterator(); iterator.hasNext();) {
			Factorias factoria = (Factorias) iterator.next();
			if (factoria.getTrabajadores().isEmpty()) {
				iterator.remove();
			}
		}

	}

}
