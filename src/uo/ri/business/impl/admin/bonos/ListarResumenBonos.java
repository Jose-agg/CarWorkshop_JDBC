package uo.ri.business.impl.admin.bonos;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import alb.util.jdbc.Jdbc;
import alb.util.math.Round;
import uo.ri.common.BusinessException;
import uo.ri.conf.PersistenceFactory;
import uo.ri.persistence.BonosGateway;
import uo.ri.persistence.ClientesGateway;

public class ListarResumenBonos {

	private Connection connection;
	private BonosGateway bonosGateway;
	private ClientesGateway clientesGateway;

	private void prepareDB() throws SQLException {
		this.connection = Jdbc.getConnection();
		connection.setAutoCommit(false);
		bonosGateway = PersistenceFactory.getBonosGateway();
		bonosGateway.setConnection(connection);
		clientesGateway = PersistenceFactory.getClientesGateway();
		clientesGateway.setConnection(connection);
	}

	public List<Map<String, Object>> execute() throws BusinessException {
		List<Map<String, Object>> listaClientes = null;
		try {
			prepareDB();
			listaClientes = listarBonosPorCliente();
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
		return listaClientes;
	}

	/**
	 * 
	 * @return
	 */
	private List<Map<String, Object>> listarBonosPorCliente() {
		List<Map<String, Object>> listaClientes, listaBonos,
				listaClientesBonos = new ArrayList<Map<String, Object>>();
		Map<String, Object> mapa;
		listaClientes = clientesGateway.findAllClients();
		for (Map<String, Object> cliente : listaClientes) {
			listaBonos = bonosGateway
					.findBonosCliente((Long) cliente.get("id"));
			listaBonos = añadirInformacionAgregada(listaBonos);

			mapa = new HashMap<String, Object>();
			mapa.put("dni", cliente.get("dni"));
			mapa.put("nombre",
					cliente.get("nombre") + " " + cliente.get("apellidos"));

			for (Map<String, Object> bono : listaBonos) {
				if (bono.get("tipo") == "InfoAgregada") {
					mapa.put("numBonos", bono.get("numBonos"));
					mapa.put("total", bono.get("total"));
					mapa.put("consumido", bono.get("consumido"));
					mapa.put("restante", bono.get("restante"));
				}
			}
			listaClientesBonos.add(mapa);
		}
		return listaClientesBonos;
	}

	/**
	 * Metodo que añade informacion agregada a la lista de bonos:
	 *  - Numero de bonos
	 *  - Importe total
	 *  - Importe consumido
	 *  - Importe restante
	 *  
	 *  Tambien comprueba los bonos sin descripcion. Si la descripcion es null 
	 *  se sustituye por ""
	 * 
	 * @param listaBonos Lista de bonos inicial
	 * @return lista de bonos con la informacion agregada
	 */
	private List<Map<String, Object>> añadirInformacionAgregada(
			List<Map<String, Object>> listaBonos) {
		int numBonos;
		double total, consumido = 0, restante = 0;

		numBonos = listaBonos.size();
		for (Map<String, Object> mapa : listaBonos) {
			consumido += (double) mapa.get("acumulado");
			restante += (double) mapa.get("disponible");
			if (mapa.get("descripcion") == null) {
				mapa.remove("descripcion");
				mapa.put("descripcion", "");
			}
		}
		total = consumido + restante;
		total = Round.twoCents(total);
		consumido = Round.twoCents(consumido);
		restante = Round.twoCents(restante);

		Map<String, Object> mapa = new HashMap<String, Object>();
		mapa.put("tipo", "InfoAgregada");
		mapa.put("numBonos", numBonos);
		mapa.put("total", total);
		mapa.put("consumido", consumido);
		mapa.put("restante", restante);
		listaBonos.add(mapa);
		return listaBonos;
	}
}
