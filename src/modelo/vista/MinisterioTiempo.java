package modelo.vista;

import java.util.ArrayList;

public class MinisterioTiempo {

	public void realizarCiclo(ArrayList<Ser> poblacion) {
		for (Ser ser : poblacion) {
			ser.setEdad(ser.getEdad() + 1);
		}
	}

}
