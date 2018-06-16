package uo.ri.ui.admin;

import alb.util.menu.BaseMenu;
import uo.ri.ui.admin.action.bonos.BonosCantidadFacturasAction;
import uo.ri.ui.admin.action.bonos.BonosNumeroAveriasAction;

public class BonosMenu extends BaseMenu {

	public BonosMenu() {
		menuOptions = new Object[][] { 
			{ "Administrador > Gestión de bonos", 	null },

			{ "Generar bonos por 3 averias", 					BonosNumeroAveriasAction.class },
			{ "Generar bonos de facturas superiores a 500€", 	BonosCantidadFacturasAction.class },
		};
	}
}
