package uo.ri.ui.foreman;

import alb.util.menu.BaseMenu;
import uo.ri.ui.foreman.action.clientes.AddClientAction;
import uo.ri.ui.foreman.action.clientes.DeleteClientAction;
import uo.ri.ui.foreman.action.clientes.DetailClientAction;
import uo.ri.ui.foreman.action.clientes.ListClientAction;
import uo.ri.ui.foreman.action.clientes.ListClientRecomendationsAction;
import uo.ri.ui.foreman.action.clientes.UpdateClientAction;

public class ClientesMenu extends BaseMenu {

	public ClientesMenu() {
		menuOptions = new Object[][] { 
			{ "Jefe de Taller > Gestión de Clientes", null },

			{ "Añadir cliente", 					AddClientAction.class },  
			{ "Eliminar cliente",					DeleteClientAction.class },
			{ "Detalles cliente", 					DetailClientAction.class },
			{ "Listar clientes", 					ListClientAction.class },  
			{ "Listar recomendaciones de clientes", ListClientRecomendationsAction.class }, 
			{ "Modificar datos de cliente", 		UpdateClientAction.class }, 
		};
	}

}
