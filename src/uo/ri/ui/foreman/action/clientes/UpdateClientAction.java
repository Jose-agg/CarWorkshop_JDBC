package uo.ri.ui.foreman.action.clientes;

import alb.util.console.Console;
import alb.util.menu.Action;
import uo.ri.business.ForemanService;
import uo.ri.common.BusinessException;
import uo.ri.conf.ServicesFactory;
import uo.ri.ui.util.Printer;

public class UpdateClientAction implements Action {

	@Override
	public void execute() throws BusinessException {

		String idCliente = Console.readString("ID del cliente");
		String dni = Console.readString("DNI");
		String nombre = Console.readString("Nombre");
		String apellidos = Console.readString("Apellidos");
		String zipcode = Console.readString("Codigo Postal");
		String telefono = Console.readString("Telefono");
		String email = Console.readString("Email");

		ForemanService foremanService = ServicesFactory.getForemanService();
		foremanService.updateClient(idCliente, dni, nombre, apellidos, zipcode,
				telefono, email);

		Printer.printUpdateClient();
	}

}
