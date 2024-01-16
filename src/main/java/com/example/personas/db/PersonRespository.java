package com.example.personas.db;

import com.example.personas.model.Person;

import javax.enterprise.context.ApplicationScoped;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class PersonRespository {

	private static final String FIND_ALL = "SELECT * FROM people";
	private static final String FIND_ALL_BY_ID = "SELECT * FROM people WHERE id = ?";
	private static final String INSERT = "INSERT INTO people (id, name, age) VALUES (?, ?, ?)";
	private static final String UPDATE = "UPDATE people SET name = ?, age = ? WHERE id = ?";
	private static final String DELETE = "DELETE FROM people WHERE id = ?";

	private final DataSource dataSource;

	public PersonRespository(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public List<Person> findAll() {
		List<Person> result = new ArrayList<>();
		try (Connection connection = dataSource.getConnection();
		     PreparedStatement statement = connection.prepareStatement(FIND_ALL);
		     ResultSet resultSet = statement.executeQuery()) {
			while (resultSet.next()) {
				result.add(
						new Person(
								UUID.fromString(resultSet.getString("id")),
								resultSet.getString("name"),
								resultSet.getInt("age")));
			}
		} catch (SQLException e) {
			//throw new PersistenceException("boom", e.getCause());
		}
		return result;
	}

	public Person findById(UUID id) {
		try (Connection connection = dataSource.getConnection();
		     PreparedStatement statement = connection.prepareStatement(FIND_ALL_BY_ID)) {
			statement.setObject(1, id);
			try (ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {
					return new Person(
							UUID.fromString(resultSet.getString("id")),
							resultSet.getString("name"),
							resultSet.getInt("age"));
				}
			}
		} catch (SQLException e) {
			//throw new PersistenceException(e);
		}
		return null;
	}

	public Person insert(Person person) {
		try (Connection connection = dataSource.getConnection();
		     PreparedStatement statement = connection.prepareStatement(INSERT)) {
			statement.setObject(1, person.getId());
			statement.setString(2, person.getNombre());
			statement.setInt(3, person.getEdad());
			statement.executeUpdate();
		} catch (SQLException e) {
			//throw new PersistenceException(e);
		}
		return person;
	}

	public Person update(Person person) {
		try (Connection connection = dataSource.getConnection();
		     PreparedStatement statement = connection.prepareStatement(UPDATE)) {
			statement.setString(1, person.getNombre());
			statement.setInt(2, person.getEdad());
			statement.setObject(3, person.getId());
			statement.executeUpdate();
		} catch (SQLException e) {
			//throw new PersistenceException(e)
		}
		return person;
	}

	public boolean deleteById(UUID id) {
		try (Connection connection = dataSource.getConnection();
		     PreparedStatement statement = connection.prepareStatement(DELETE)) {
			statement.setObject(1, id);
			return statement.executeUpdate() == 1;
		} catch (SQLException e) {
			//throw new PersistenceException(e);
			System.out.println("Delete exception");
		}
		return true;
	}

}
