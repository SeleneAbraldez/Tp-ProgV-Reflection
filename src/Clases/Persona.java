package Clases;

import Anotaciones.Columna;
import Anotaciones.Id;
import Anotaciones.Tabla;

@Tabla(nombre = "tpr_personas")
public class Persona {
	
	@Id
	@Columna(nombre = "id")
	private int id;
	@Columna(nombre = "nombre")
	private String nombre;
	@Columna(nombre = "apellido")
	private String apellido;

	public Persona() {

	}

	public Persona(int i, String nombre, String apellido) {
		super();
		this.id = i;
		this.nombre = nombre;
		this.apellido = apellido;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}



	@Override
	public String toString() {
		return "Persona [id=" + id + ", nombre=" + nombre + ", apellido=" + apellido + "]";
	}

}