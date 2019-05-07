package modelo.vista;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Stack;

import org.ietf.jgss.Oid;
import org.junit.Ignore;
import org.junit.jupiter.api.Test;

class TestGlobal {
	@Test
	void testMorir() {
		Ser t1 = new Ser("pepe", 1234, 55);
		t1.setEdad(56);
		Ser t2 = new Ser("carlos", 1324, 10);
		t2.setEdad(8);
		Ser t3 = new Ser("ivan", 1543, 69);
		t3.setEdad(70);
		Censo censo = new Censo();
		censo.getPoblacion().add(t1);
		censo.getPoblacion().add(t2);
		censo.getPoblacion().add(t3);
		ArrayList<Ser> muertosTest = new ArrayList<Ser>();
		muertosTest.add(t1);
		System.out.println(censo.getPoblacion().size());
		ArrayList<Ser> muertos = censo.morir();
		assertTrue(muertos.containsAll(muertosTest));
		System.out.println(censo.getPoblacion().size());
		censo.organizarColeccionciones();
		int posFinal = 0;
		for (int i = 0; i < censo.getPoblacion().size(); i++) {
			if (censo.getPoblacion().get(i).getEdad() < 18) {
				posFinal = i;
			}
		}
		Ser[] seres = new Ser[5];
		for (int i = 0; i < 5; i++) {
			Ser ser = new Ser("PACO " + i, i, 67 + i);
			seres[i] = ser;
			ser.setAhorros(460);
			ser.setNecesidadVital(365);
			censo.getPoblacion().add(ser);
		}
		System.out.println("antes de los cambios");
		for (int i = 0; i < seres.length; i++) {
			System.out.println("esperanza de vida: " + seres[i].getEsperanzaVida());
			System.out.println("ahorros: " + seres[i].getAhorros());
		}
		censo.reducirEV();
		System.out.println("despues de los cambios");
		for (int i = 0; i < seres.length; i++) {
			System.out.println("esperanza de vida: " + seres[i].getEsperanzaVida());
			System.out.println("ahorros: " + seres[i].getAhorros());
		}

		System.out.println(censo.numeroJubilados());
		Censo cesi = new Censo();
		System.out.println(cesi.getPoblacion().size());
		System.out.println(posFinal);

	}

	@Test
	void testIndustria() {
		Estado estado = new Estado();
		LinkedList<Ser> demandantes = new LinkedList<Ser>();
		for (int i = 0; i < 25000; i++) {
			Ser ser = new Ser("PACO " + i, i, 67 + i);
			demandantes.add(ser);
		}
		estado.getIndustrias().setDemanda(10000000);
		System.out.println(estado.getIndustrias().totalTrabajadores());
		estado.getIndustrias().contratarDemandantes(demandantes);
		System.out.println(estado.getIndustrias().totalTrabajadores());
		System.out.println(estado.getIndustrias().getIndustrias().size());
		assertTrue(estado.getIndustrias().getIndustrias().size() == estado.getIndustrias().getDemanda() / 1000 / 1000);
		System.out.println(estado.getPoblacion().getDemandantes().size());
		System.out.println(estado.getPoblacion().getPoblacion().addAll(demandantes));
		estado.getIndustrias().aumentarDemanda();
		for (Ser ser : estado.getPoblacion().getPoblacion()) {
			System.out.println(ser.getNombre());
		}
	}

	@Test
	void testEconomia() {
		Estado estado = new Estado();
		LinkedList<Ser> poblacion = new LinkedList<Ser>();
		for (int i = 0; i < 200; i++) {
			Ser ser = new Ser("Juanito " + i, i, 67 + i);
			ser.setEdad(10);
			poblacion.add(ser);
		}
		for (int i = 0; i < 400; i++) {
			Ser ser = new Ser("Juan " + i, i, 67 + i);
			ser.setEdad(30);
			poblacion.add(ser);
		}
		for (int i = 0; i < 200; i++) {
			Ser ser = new Ser("Don Juan " + i, i, 67 + i);
			ser.setEdad(66);
			poblacion.add(ser);
		}
		estado.getFinanzas().setFondosEstado(200000);
		System.out.println(estado.getFinanzas().comprobarFondosEstadoParaSubvenciones(poblacion.size()));
	}
}
