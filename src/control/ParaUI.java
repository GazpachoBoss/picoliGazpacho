package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import modelo.vista.Datos;
import modelo.vista.DatosEstadoGlobal;
import modelo.vista.DatosEstadoLocal;
import modelo.vista.DatosPoblacion;
import modelo.vista.Estado;
import vista.Comunicador;
import vista.UI;

public class ParaUI extends UI {

	Estado estado = new Estado();

	public ParaUI() {
		getBtnIncrementarPorcentajeProduccion().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				estado.getIndustrias().aumentarDemanda();
			}
		});
		getBtnDecrementarPorcentajeProduccion().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				estado.getIndustrias().reducirDemanda();
			}
		});
		getBtnPasarPeriodo().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				DatosEstadoGlobal datosEstadoGlobal = new DatosEstadoGlobal(estado.getIndustrias().getDemanda(), estado.getIndustrias().getProduccion(), estado.getFinanzas().getFondosEstado(),
						0);
				DatosEstadoLocal datosEstadoLocal = new DatosEstadoLocal(estado.getIndustrias().getIndustrias().size(), estado.getIndustrias().calcularOcupacionTotal());
				DatosPoblacion datosPoblacion = new DatosPoblacion(estado.getPoblacion().getPoblacionTotal( estado.getIndustrias().totalTrabajadores()), estado.getPoblacion().numeroMenores(), estado.getIndustrias().totalTrabajadores(), estado.getPoblacion().numeroJubilados(),
						estado.getPoblacion().getNacimientos(), estado.getPoblacion().getMuertos(), estado.getPoblacion().getJubiladosNuevos(), estado.getIndustrias().getNumeroTrabajadoresNecesarios());
				setDatosEnElInterfazUsuario(datosPoblacion, datosEstadoLocal, datosEstadoGlobal);
				estado.play();
			}
		});
	}

	public void setDatosEnElInterfazUsuario(DatosPoblacion datosPoblacion, DatosEstadoLocal datosEstadoLocal,
			DatosEstadoGlobal datosEstadoGlobal) {
		rellenarComunicador(comunicadorPoblacion, datosPoblacion);
		rellenarComunicador(comunicadorEstadoLocal, datosEstadoLocal);
		rellenarComunicador(comunicadorEstadoGlobal, datosEstadoGlobal);
	}

	public void rellenarComunicador(Comunicador comunicador, Datos datos) {
		comunicador.asignarValores(datos);
	}

}
