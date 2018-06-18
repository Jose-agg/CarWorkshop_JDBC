package uo.ri.persistence.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import alb.util.jdbc.Jdbc;
import uo.ri.conf.Conf;
import uo.ri.persistence.ClientesGateway;

/**
 * Clase que se encarga de gestionar la persistencia de 
 * la entidad Clientes
 * 
 * @author José Antonio García García
 *
 */
public class ClientesGatewayImpl implements ClientesGateway {

	private Connection connection;

	@Override
	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	@Override
	public List<Long> getListaClientes() {
		PreparedStatement pst = null;
		ResultSet rs = null;
		List<Long> list = new ArrayList<Long>();

		try {
			pst = connection.prepareStatement(Conf.get("SQL_FIND_ALL_CLIENTS"));

			rs = pst.executeQuery();
			while (rs.next()) {
				list.add(rs.getLong(1));
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			Jdbc.close(rs, pst);
		}
		return list;
	}

	@Override
	public Map<String, Object> getDetallesCliente(Long idCliente) {
		PreparedStatement pst = null;
		ResultSet rs = null;
		Map<String, Object> mapa = null;

		try {
			pst = connection.prepareStatement(Conf.get("SQL_DETAILS_CLIENT"));
			pst.setLong(1, idCliente);
			rs = pst.executeQuery();

			while (rs.next()) {
				mapa = new HashMap<String, Object>();
				mapa.put("dni", rs.getString("dni"));
				mapa.put("nombre", rs.getString("nombre"));
				mapa.put("apellidos", rs.getString("apellidos"));
				mapa.put("zipcode", rs.getString("zipcode"));
				mapa.put("telefono", rs.getString("telefono"));
				mapa.put("email", rs.getString("email"));
			}

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			Jdbc.close(rs, pst);
		}
		return mapa;
	}

	@Override
	public Long getClienteID(String dni) {
		PreparedStatement pst = null;
		ResultSet rs = null;
		Long idCliente = 0L;

		try {
			pst = connection.prepareStatement(Conf.get("SQL_FIND_CLIENTE_ID"));
			pst.setString(1, dni);
			rs = pst.executeQuery();

			while (rs.next()) {
				idCliente = rs.getLong("id");
			}

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			Jdbc.close(rs, pst);
		}
		return idCliente;
	}

	@Override
	public void addCliente(String dni, String nombre, String apellidos,
			String zipcode, String telefono, String email, Long recomendador) {
		PreparedStatement pst = null;

		try {
			pst = connection.prepareStatement(
					Conf.get("SQL_INSERT_CLIENT_RECOMMENDED"));
			pst.setString(1, dni);
			pst.setString(2, nombre);
			pst.setString(3, apellidos);
			pst.setString(4, zipcode);
			pst.setString(5, telefono);
			pst.setString(6, email);
			pst.setLong(7, recomendador);
			pst.executeUpdate();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			Jdbc.close(pst);
		}
	}

	@Override
	public void addCliente(String dni, String nombre, String apellidos,
			String zipcode, String telefono, String email) {
		PreparedStatement pst = null;

		try {
			pst = connection.prepareStatement(
					Conf.get("SQL_INSERT_CLIENT_NOT_RECOMMENDED"));
			pst.setString(1, dni);
			pst.setString(2, nombre);
			pst.setString(3, apellidos);
			pst.setString(4, zipcode);
			pst.setString(5, telefono);
			pst.setString(6, email);
			pst.executeUpdate();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			Jdbc.close(pst);
		}
	}

	@Override
	public boolean comprobarClienteConFacturasAbonadas(Long idRecomendador) {
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			pst = connection
					.prepareStatement(Conf.get("SQL_CHECK_CLIENT_INVOICE"));
			pst.setLong(1, idRecomendador);
			rs = pst.executeQuery();

			while (rs.next()) {
				if (rs.getString(1).equals("ABONADA")) {
					return true;
				}
			}

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			Jdbc.close(rs, pst);
		}
		return false;
	}

	@Override
	public void deleteClient(Long idCliente) {
		PreparedStatement pst = null;

		try {
			pst = connection.prepareStatement(Conf.get("SQL_DELETE_CLIENT"));
			pst.setLong(1, idCliente);
			pst.executeUpdate();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			Jdbc.close(pst);
		}
	}

	@Override
	public List<Map<String, Object>> findAllClients() {
		PreparedStatement pst = null;
		ResultSet rs = null;
		List<Map<String, Object>> lista = new ArrayList<Map<String, Object>>();
		Map<String, Object> mapa = null;

		try {
			pst = connection.prepareStatement(Conf.get("SQL_FIND_ALL_CLIENTS"));
			rs = pst.executeQuery();

			while (rs.next()) {
				mapa = new HashMap<String, Object>();
				mapa.put("dni", rs.getString("dni"));
				mapa.put("nombre", rs.getString("nombre"));
				mapa.put("apellidos", rs.getString("apellidos"));
				mapa.put("id", rs.getLong("id"));
				lista.add(mapa);
			}

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			Jdbc.close(rs, pst);
		}
		return lista;
	}

	@Override
	public Map<String, Object> findRecomendedClients(Long idCliente) {
		PreparedStatement pst = null;
		ResultSet rs = null;
		Map<String, Object> mapa = new HashMap<String, Object>();

		try {
			pst = connection.prepareStatement(
					Conf.get("SQL_FIND_ALL_RECOMENDED_CLIENTS"));
			pst.setLong(1, idCliente);
			rs = pst.executeQuery();
			int contador = 1;
			while (rs.next()) {
				mapa.put("usuario" + contador, rs.getString("nombre") + " - "
						+ rs.getString("apellidos") + " - " + rs.getLong("id"));
			}

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			Jdbc.close(rs, pst);
		}
		return mapa;
	}

	@Override
	public void updateClient(Long idCliente, String nombre, String apellidos,
			String zipcode, String telefono, String email) {
		PreparedStatement pst = null;

		try {
			pst = connection.prepareStatement(Conf.get("SQL_UPDATE_CLIENT"));
			pst.setString(1, nombre);
			pst.setString(2, apellidos);
			pst.setString(3, zipcode);
			pst.setString(4, telefono);
			pst.setString(5, email);
			pst.setLong(6, idCliente);
			pst.executeUpdate();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			Jdbc.close(pst);
		}
	}

	@Override
	public Long findIdClienteByIdFactura(Long idFactura) {
		PreparedStatement pst = null;
		ResultSet rs = null;
		Long idCliente = 0L;

		try {
			pst = connection.prepareStatement(
					Conf.get("SQL_FIND_CLIENTE_ID_BY_ID_FACTURA"));
			pst.setLong(1, idFactura);
			rs = pst.executeQuery();

			while (rs.next()) {
				idCliente = rs.getLong("cliente_id");
			}

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			Jdbc.close(rs, pst);
		}
		return idCliente;
	}

}
