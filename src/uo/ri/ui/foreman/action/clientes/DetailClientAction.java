package uo.ri.ui.foreman.action.clientes;

import java.util.Map;

import alb.util.console.Console;
import alb.util.menu.Action;
import uo.ri.business.ForemanService;
import uo.ri.common.BusinessException;
import uo.ri.conf.ServicesFactory;
import uo.ri.ui.util.Printer;

public class DetailClientAction implements Action {

	@Override
	public void execute() throws BusinessException {

		String idCliente = Console.readString("Id de cliente");

		ForemanService foremanService = ServicesFactory.getForemanService();
		Map<String, Object> detalles = foremanService.detailClient(idCliente);

		Printer.printDetailClient(detalles);
	}
}
