package uo.ri.persistence.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import alb.util.jdbc.Jdbc;
import uo.ri.conf.Conf;
import uo.ri.persistence.MediosPagoGateway;

public class MediosPagoGatewayImpl implements MediosPagoGateway {

	private Connection connection;

	@Override
	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	@Override
	public void addMetalicoById(Long idCliente) {
		PreparedStatement pst = null;

		try {
			pst = connection.prepareStatement(Conf.get("SQL_ADD_CASH_CLIENT"));
			pst.setLong(1, idCliente);
			pst.executeUpdate();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			Jdbc.close(pst);
		}
	}

	@Override
	public void deleteMetalicoById(Long idCliente) {
		PreparedStatement pst = null;

		try {
			pst = connection
					.prepareStatement(Conf.get("SQL_DELETE_CASH_CLIENT"));
			pst.setLong(1, idCliente);
			pst.executeUpdate();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			Jdbc.close(pst);
		}
	}

}
