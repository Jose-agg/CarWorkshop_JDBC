package uo.ri.business.impl.admin.bonos;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import alb.util.jdbc.Jdbc;
import uo.ri.common.BusinessException;
import uo.ri.conf.PersistenceFactory;
import uo.ri.persistence.AveriasGateway;
import uo.ri.persistence.BonosGateway;
import uo.ri.persistence.ClientesGateway;
import uo.ri.persistence.VehiculosGateway;

public class GenerarBonosNumeroAverias {

	private Connection connection;
	private BonosGateway bonosGateway;
	private ClientesGateway clientesGateway;
	private AveriasGateway averiasGateway;
	private VehiculosGateway vehiculosGateway;

	private void prepareDB() throws SQLException {
		this.connection = Jdbc.getConnection();
		connection.setAutoCommit(false);
		bonosGateway = PersistenceFactory.getBonosGateway();
		bonosGateway.setConnection(connection);
		clientesGateway = PersistenceFactory.getClientesGateway();
		clientesGateway.setConnection(connection);
		averiasGateway = PersistenceFactory.getAveriasGateway();
		averiasGateway.setConnection(connection);
		vehiculosGateway = PersistenceFactory.getVehiculosGateway();
		vehiculosGateway.setConnection(connection);
	}

	public void execute() throws BusinessException {
		try {
			prepareDB();
			generarBonosNumeroAverias();
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

	/**
	 * Metodo que recorre todos los clientes contanto su numero de averias.
	 * 
	 * Si tiene mas de tres averias se crea un bono para ese cliente.
	 */
	private void generarBonosNumeroAverias() throws BusinessException {
		List<Long> listaClientes, listaVehiculos, listaAverias,
				listaAveriasNoUsadas;

		listaClientes = clientesGateway.getListaClientes();
		for (Long cliente : listaClientes) {
			listaVehiculos = vehiculosGateway.getVehiculosPorCliente(cliente);
			listaAveriasNoUsadas = new ArrayList<>();

			for (Long vehiculo : listaVehiculos) {
				listaAverias = averiasGateway.getAveriasNoUsadas(vehiculo);

				for (Long averia : listaAverias) {
					listaAveriasNoUsadas.add(averia);
				}
			}
			comprobarCreacionBono(cliente, listaAveriasNoUsadas);
		}
	}

	/**
	 * Metodo que comprueba si el cliente cumple los requisitos para crear bonos
	 * por cantidad de averias. Si los cumple crea el bono
	 * 
	 * @param cliente Identificador del cliente a buscar
	 * @param listaAveriasNoUsadas Lista de averias que se comprobarán si 
	 * 		cumplen los requisitos
	 */
	private void comprobarCreacionBono(Long cliente, List<Long> lista) {
		int numAverias = lista.size() - lista.size() % 3;
		int numBonos = numAverias / 3;

		for (int i = 0; i < numAverias; i++) {
			averiasGateway.actualizarAveriaUsadaBono(lista.get(i));
		}
		for (int i = 0; i < numBonos; i++) {
			int codigo = bonosGateway.getUltimoCodigo();
			String code = "B" + (codigo + 10);
			bonosGateway.insertarBonosMedioPago(code, 20, cliente,
					"Por tres averias");
		}
	}

}
