package com.example.personas;

import com.example.personas.db.PersonRespository;
import com.example.personas.model.Person;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.UUID;

@Path("/api")
public class Personas {

	private final PersonRespository personRespository;

	public Personas(PersonRespository personRespository) {
		this.personRespository = personRespository;
	}

	@GET
	@Path("/lista")
	@Produces(MediaType.TEXT_PLAIN)
	public String listaPersona() {
		return "Lista Personas";
	}

	@GET
	@Path("/personas")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Person> allPersons() {
		return personRespository.findAll();
	}

	@GET
	@Path("/persona/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Person findById(@PathParam("id") UUID id) {
		return personRespository.findById(id);
	}

	@PUT
	@Path("/update/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Person updateById(@PathParam("id") UUID id, Person person) {
		Person updatePerson = personRespository.findById(id);
		if (updatePerson != null) {
			personRespository.update(
					new Person(id, person.getNombre(), person.getEdad())
			);
			updatePerson.setEdad(person.getEdad());
			updatePerson.setNombre(person.getNombre());
		}

		return updatePerson;
	}

	@POST
	@Path("/add")
	@Produces(MediaType.APPLICATION_JSON)
	public Person addPerson(Person person) {
		return personRespository.insert(
			new Person(UUID.randomUUID(), person.getNombre(), person.getEdad())
		);
	}

	@DELETE
	@Path("/delete/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Person deletePerson(@PathParam("id") UUID id) {
		Person personaABorrar = personRespository.findById(id);
		if (personaABorrar != null) {
			personRespository.deleteById(id);
		}

		return personaABorrar;
	}
}
