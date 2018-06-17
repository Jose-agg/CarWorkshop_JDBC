package uo.ri.business.impl;

import java.util.List;
import java.util.Map;

import uo.ri.business.ForemanService;
import uo.ri.business.impl.foreman.AddClient;
import uo.ri.business.impl.foreman.DeleteClient;
import uo.ri.business.impl.foreman.DetailsClient;
import uo.ri.business.impl.foreman.FindAllClients;
import uo.ri.business.impl.foreman.FindRecomendationsClient;
import uo.ri.business.impl.foreman.UpdateClient;
import uo.ri.common.BusinessException;

public class ForemanServiceImpl implements ForemanService {

	@Override
	public void newClient(String dni, String nombre, String apellidos,
			String zipcode, String telefono, String email, String recomendador)
			throws BusinessException {
		AddClient addClient = new AddClient(dni, nombre, apellidos, zipcode,
				telefono, email, recomendador);
		addClient.execute();
	}

	@Override
	public void deleteClient(String idCliente) throws BusinessException {
		DeleteClient deleteClient = new DeleteClient(idCliente);
		deleteClient.execute();
	}

	@Override
	public Map<String, Object> detailClient(String idCliente)
			throws BusinessException {
		DetailsClient detailsClient = new DetailsClient(idCliente);
		return detailsClient.execute();
	}

	@Override
	public List<Map<String, Object>> listClients() throws BusinessException {
		FindAllClients findAllClients = new FindAllClients();
		return findAllClients.execute();
	}

	@Override
	public Map<String, Object> listRecomendationsClient(String idCliente)
			throws BusinessException {
		FindRecomendationsClient findRecomendationsClient = new FindRecomendationsClient(
				idCliente);
		return findRecomendationsClient.execute();
	}

	@Override
	public void updateClient(String idCliente, String dni, String nombre,
			String apellidos, String zipcode, String telefono, String email)
			throws BusinessException {
		UpdateClient updateClient = new UpdateClient(idCliente, dni, nombre,
				apellidos, zipcode, telefono, email);
		updateClient.execute();
	}

}
