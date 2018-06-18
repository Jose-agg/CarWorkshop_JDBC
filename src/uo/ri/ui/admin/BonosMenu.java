package uo.ri.ui.admin;

import alb.util.menu.BaseMenu;
import uo.ri.ui.admin.action.bonos.BonosCantidadFacturasAction;
import uo.ri.ui.admin.action.bonos.BonosNumeroAveriasAction;
import uo.ri.ui.admin.action.bonos.ListarResumenBonosAction;
import uo.ri.ui.admin.action.bonos.MostrarDetallesBonosClienteAction;

public class BonosMenu extends BaseMenu {

	public BonosMenu() {
		menuOptions = new Object[][] { 
			{ "Administrador > Gestión de bonos", 	null },

			{ "Generar bonos por 3 averias", 					BonosNumeroAveriasAction.class },
			{ "Generar bonos de facturas superiores a 500€", 	BonosCantidadFacturasAction.class },
			{ "Listar bonos de un cliente",						MostrarDetallesBonosClienteAction.class },
			{ "Listar resumen de bonos por cliente",			ListarResumenBonosAction.class }
		};
	}
}
