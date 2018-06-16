package uo.ri.ui.admin;

import alb.util.menu.BaseMenu;
import uo.ri.ui.admin.action.mecanicos.AddMechanicAction;
import uo.ri.ui.admin.action.mecanicos.DeleteMechanicAction;
import uo.ri.ui.admin.action.mecanicos.ListMechanicsAction;
import uo.ri.ui.admin.action.mecanicos.UpdateMechanicAction;

public class MecanicosMenu extends BaseMenu {

	public MecanicosMenu() {
		menuOptions = new Object[][] { 
			{"Administrador > Gestión de mecánicos", null},
			
			{ "Añadir mecánico", 				AddMechanicAction.class }, 
			{ "Modificar datos de mecánico", 	UpdateMechanicAction.class }, 
			{ "Eliminar mecánico", 				DeleteMechanicAction.class }, 
			{ "Listar mecánicos", 				ListMechanicsAction.class },
		};
	}

}
