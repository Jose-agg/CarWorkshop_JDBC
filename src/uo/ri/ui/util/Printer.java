package uo.ri.ui.util;

import java.util.List;
import java.util.Map;

import alb.util.console.Console;

public class Printer {

	public static void printAddMechanic() {
		Console.println("Nuevo mecánico añadido");
	}

	public static void printDeleteMechanic() {
		Console.println("Se ha eliminado el mecánico");
	}

	public static void printListMechanics(List<Map<String, Object>> list) {
		Console.println("\nListado de mecánicos\n");
		for (Map<String, Object> map : list) {
			Console.printf("\t%d %s %s\n", map.get("idMecanico"),
					map.get("nombre"), map.get("apellidos"));
		}
	}

	public static void printUpdateMechanic() {
		Console.println("Mecánico actualizado");
	}

	public static void printFacturarReparaciones(Map<String, Object> map) {
		Console.printf("Factura nº: %d\n", map.get("numeroFactura"));
		Console.printf("\tFecha: %1$td/%1$tm/%1$tY\n", map.get("fechaFactura"));
		Console.printf("\tTotal: %.2f €\n", map.get("totalFactura"));
		Console.printf("\tIva: %.1f %% \n", map.get("iva"));
		Console.printf("\tTotal con IVA: %.2f €\n", map.get("totalConIva"));
	}

	public static void printBonosNumeroAverias() {
		Console.println("Generados los bonos por 3 averias");
	}

	public static void printBonosCantidadFacturas() {
		Console.println(
				"Generados los bonos para las facturas superiores a 500€");
	}

}
