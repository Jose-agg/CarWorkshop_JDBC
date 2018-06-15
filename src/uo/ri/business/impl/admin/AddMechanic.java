package uo.ri.business.impl.admin;

import java.sql.Connection;
import java.sql.SQLException;

import alb.util.jdbc.Jdbc;
import uo.ri.conf.PersistenceFactory;
import uo.ri.persistence.MecanicosGateway;

public class AddMechanic {

	private String nombre;
	private String apellidos;

	private Connection connection;
	private MecanicosGateway mecanicosGateway;

	public AddMechanic(String nombre, String apellidos) {
		this.nombre = nombre;
		this.apellidos = apellidos;
	}

	private void prepareDB() throws SQLException {
		this.connection = Jdbc.getConnection();
		connection.setAutoCommit(false);
		mecanicosGateway = PersistenceFactory.getMecanicosGateway();
		mecanicosGateway.setConnection(connection);
	}

	public void execute() {
		try {
			prepareDB();
			mecanicosGateway.addMechanic(nombre, apellidos);
			connection.commit();

		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException ex) {
			}
			throw new RuntimeException(e);
		} finally {
			Jdbc.close(connection);
		}
	}
}
