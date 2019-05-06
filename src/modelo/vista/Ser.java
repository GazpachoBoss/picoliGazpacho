package modelo.vista;


public class Ser {

	private String nombre;
	private int numeroIdentificacion; // es unico y no varía
	private float esperanzaVida; // aleatorio
	private float necesidadVital;
	private int edad;
	private float sueldo;
	private float ahorros;

	public Ser(String nombre, int numeroIdentificacion, int esperanzaVida) {
		super();
		this.nombre = nombre;
		this.numeroIdentificacion = numeroIdentificacion;
		this.esperanzaVida = esperanzaVida;
		this.edad = 0;
		this.ahorros=0f;
		this.necesidadVital=365;
	}

	public float getEsperanzaVida() {
		return esperanzaVida;
	}

	public void setEsperanzaVida(float esperanzaVida) {
		this.esperanzaVida = esperanzaVida;
	}

	public float getNecesidadVital() {
		return necesidadVital;
	}

	public void setNecesidadVital(float necesidadVital) {
		this.necesidadVital = necesidadVital;
	}

	public int getEdad() {
		return edad;
	}

	public void setEdad(int edad) {
		this.edad = edad;
	}

	public String getNombre() {
		return nombre;
	}

	public int getNumeroIdentificacion() {
		return numeroIdentificacion;
	}

	public float getAhorros() {
		return ahorros;
	}

	public void setAhorros(float ahorros) {
		this.ahorros = ahorros;
	}

	public float getSueldo() {
		return sueldo;
	}

	public void setSueldo(float sueldo) {
		this.sueldo = sueldo;
	}
}

