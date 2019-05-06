package modelo.vista;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class Censo {
	private ArrayList<Ser> poblacion = new ArrayList<Ser>();
	private LinkedList<Ser> demandantes = new LinkedList<Ser>();
	private HashSet<Integer> identificacion = new HashSet<Integer>();
	private int nacimientos=0; 
	private int muertos=0; 
	private int jubilados=0;
	
	public int getNacimientos() {
		int total=nacimientos;
		nacimientos=0;
		return total;
	}
	public int getJubiladosNuevos() {
		return jubilados;
	}
	
	public int getMuertos() {
		return muertos;
	}
	
	public HashSet<Integer> getIdentificacion() {
		return identificacion;
	}
	
	public int getPoblacionTotal(int numeroTrabajadores) {
		int total=0;
		
		total+=numeroMenores()+numeroJubilados()+numeroTrabajadores;
		return total;
	}

	public Comparator<Ser> getComparador() {
		return comparador;
	}

	public Comparator<Ser> getComparadorNV() {
		return comparadorNV;
	}
	 public int numeroJubilados() {
	    	int posInicial=0;
	    	for (Ser ser : poblacion) {
				if (ser.getEdad()>64) {
					posInicial++;
				}
			}
			return posInicial;
		}
	 public int numeroMenores() {
	    	int posInicial=0;
	    		for (Ser ser : poblacion) {
	    			if(ser.getEdad()<18) {
	    				posInicial++;
	    			}
	    		}
	    	return posInicial;
		}
	 

	public Censo() {
		super();
		for (int i = 0; i < 50;) {
			Ser ser = new Ser(crearNombre(), CrearIdentificacion(), (int) (Math.random() * (90)));
			ser.setEdad((int) (Math.random() * (18)));
			if (ser.getEdad()<ser.getEsperanzaVida()) {
				poblacion.add(ser);
				i++;
			}
		}
		for (int i = 0; i < 100;) {
			Ser ser = new Ser(crearNombre(), CrearIdentificacion(), (int) (Math.random() * (90)));
			ser.setEdad((int) (Math.random() * (65 - 18) + 18));
			if (ser.getEdad()<ser.getEsperanzaVida()) {
				poblacion.add(ser);
				demandantes.push(ser);
				i++;
			}
		}
		for (int i = 0; i < 30;) {
			Ser ser = new Ser(crearNombre(), CrearIdentificacion(), (int) (Math.random() * (90)));
			ser.setEdad((int) (Math.random() * (90 - 65) + 65));
			if (ser.getEdad()<ser.getEsperanzaVida()) {
				ser.setNecesidadVital(365/2);
				poblacion.add(ser);
				i++;
			}
		}
		organizarColeccionciones();
	}

	private Comparator<Ser> comparador = new Comparator<Ser>() {
		@Override
		public int compare(Ser o1, Ser o2) {
			return o1.getEdad() - o2.getEdad();
		}
	};
	private Comparator<Ser> comparadorNV = new Comparator<Ser>() {

		@Override

		public int compare(Ser o1, Ser o2) {
			double cosa = o1.getAhorros() * 100000;
			double cosaDos = o2.getAhorros() * 100000;
			return (int) cosa - (int) (cosaDos);
		}
	};

	public LinkedList<Ser> getDemandantes() {
		return demandantes;
	}

	public void organizarColeccionciones() {
		Collections.sort(poblacion, comparador);
		Collections.sort(demandantes, comparadorNV);
	}

	public void setDemandantes(LinkedList<Ser> demandantes) {
		this.demandantes = demandantes;
	}

	public ArrayList<Ser> getPoblacion() {
		return poblacion;
	}

	public void setPoblacion(ArrayList<Ser> poblacion) {
		this.poblacion = poblacion;
	}
	
	public void actualizarDemandantes(Stack <Ser> trabajadores) {
		for (Ser ser : poblacion) {
			if (ser.getEdad()>17&&ser.getEdad()<65&&!trabajadores.contains(ser)&&!demandantes.contains(ser)) {
				demandantes.push(ser);
			}
		}
	}
	
// recibes dos parametros nuevos produccion y demanda
// for lo recorres tantas veces como (demanda-produccion)/1000
	public void nacimiento(float demanda, float produccion) {
		int CuantiaNacimientos=(int) ((demanda-produccion)/1000);
		if (nacimientos+CuantiaNacimientos<0) {
			nacimientos=0;
		}else {
			nacimientos+=CuantiaNacimientos;
		}
		for (int i = 0; i < nacimientos; i++) {
			Ser menor = new Ser(crearNombre(), CrearIdentificacion(), (int) (Math.random() * (90)));
			poblacion.add(menor);			
		}
	}
	public void jubilarSer() {
		jubilados=0;
		for (Ser ser : poblacion) {
			if (ser.getEdad()==65) {
				ser.setNecesidadVital(365f/2f);
				nacimientos++;
				jubilados++;
			}
		}
	}

	public ArrayList<Ser> morir() {
		ArrayList<Ser> trabajadoresMuertos = new ArrayList<>();
		ArrayList<Ser> personasMuertas=new ArrayList<Ser>();
		for (Iterator<Ser> iterator = poblacion.iterator(); iterator.hasNext();) {
			Ser ser = (Ser) iterator.next();
			if (ser.getEdad() >= ser.getEsperanzaVida()) {
				if (!demandantes.contains(ser) && ser.getEdad() > 17 && ser.getEdad() < 65) {
					trabajadoresMuertos.add(ser);
				}
				personasMuertas.add(ser);
				muertos++;
			}
		}
		for (Ser ser : personasMuertas) {
			if (ser.getEdad()>64) {
				jubilados--;
			}
			if (jubilados<0) {
				jubilados=0;
			}
		}
		demandantes.removeAll(personasMuertas);
		poblacion.removeAll(personasMuertas);
		return trabajadoresMuertos;
	}

	public int CrearIdentificacion() {
		int indice;
		do {
			indice = (int) (Math.random() * 9999999);
		} while (!identificacion.add(indice));
		return indice;
	}

	public String crearNombre() {
		String nombre = "";
		int indice;
		for (int i = 0; i < 10; i++) {
			indice = (int) (Math.random() * (122 - 97) + 97);
			nombre = nombre + (char) (indice);
		}
		return nombre;
	}

	public void reducirEV() {
		float reduccion=0f;
		for (Ser ser : poblacion) {
			reduccion = (ser.getNecesidadVital() - ser.getAhorros()) / ser.getNecesidadVital();
			if (reduccion > 0) {
				if (reduccion >= 0.5) {
					reduccion = 0.5f;
				}
			}
			ser.setEsperanzaVida(ser.getEsperanzaVida() - reduccion);
		}
	}

}
