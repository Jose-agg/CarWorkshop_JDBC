package uo.ri.ui.foreman.action.clientes;

import alb.util.console.Console;
import alb.util.menu.Action;
import uo.ri.business.ForemanService;
import uo.ri.common.BusinessException;
import uo.ri.conf.ServicesFactory;
import uo.ri.ui.util.Printer;

/**
 * Interacción con el usuario para la creación de un nuevo Cliente
 * 
 * @author José Antonio García García
 *
 */
public class AddClientAction implements Action {

	@Override
	public void execute() throws BusinessException {

		String dni = Console.readString("DNI");
		String nombre = Console.readString("Nombre");
		String apellidos = Console.readString("Apellidos");
		String zipcode = Console.readString("Codigo Postal");
		String telefono = Console.readString("Telefono");
		String email = Console.readString("Email");
		String recomendador = getRecomendador();

		ForemanService foremanService = ServicesFactory.getForemanService();
		foremanService.newClient(dni, nombre, apellidos, zipcode, telefono,
				email, recomendador);

		Printer.printAddClient();
	}

	private String getRecomendador() {
		boolean respuesta = Console
				.readString("¿Viene recomendado por alguien? (s/n) ")
				.equalsIgnoreCase("s");
		if (respuesta) {
			return Console
					.readString("Escriba el identificador de su recomendador");
		}
		return "0";
	}
}
