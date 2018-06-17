package uo.ri.ui.foreman.action.clientes;

import java.util.List;
import java.util.Map;

import alb.util.menu.Action;
import uo.ri.business.ForemanService;
import uo.ri.common.BusinessException;
import uo.ri.conf.ServicesFactory;
import uo.ri.ui.util.Printer;

public class ListClientAction implements Action {

	@Override
	public void execute() throws BusinessException {

		ForemanService foremanService = ServicesFactory.getForemanService();
		List<Map<String, Object>> lista = foremanService.listClients();

		Printer.printListClients(lista);
	}
}
