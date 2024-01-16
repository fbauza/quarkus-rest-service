package com.example.personas.model;

import java.util.UUID;

public class Person {
	private UUID id;
	private String nombre;
	private Integer edad;

	public Person() {
	}

	public Person(UUID id, String nombre, Integer edad) {
		this.id = id;
		this.nombre = nombre;
		this.edad = edad;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Integer getEdad() {
		return edad;
	}

	public void setEdad(Integer edad) {
		this.edad = edad;
	}
}
