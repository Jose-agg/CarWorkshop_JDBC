package uo.ri.ui.cash.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import alb.util.console.Console;
import alb.util.menu.Action;
import uo.ri.business.CashService;
import uo.ri.common.BusinessException;
import uo.ri.conf.ServicesFactory;
import uo.ri.ui.util.Printer;

public class FacturarReparacionesAction implements Action {

	@Override
	public void execute() throws BusinessException {
		List<Long> idsAveria = new ArrayList<Long>();

		// pedir las averias a incluir en la factura
		do {
			Long id = Console.readLong("ID de averia");
			idsAveria.add(id);
		} while (masAverias());

		CashService cashService = ServicesFactory.getCashService();
		Map<String, Object> map = cashService.createInvoiceFor(idsAveria);
		Printer.printFacturarReparaciones(map);
	}

	private boolean masAverias() {
		return Console.readString("¿Añadir más averias? (s/n) ")
				.equalsIgnoreCase("s");
	}
}
