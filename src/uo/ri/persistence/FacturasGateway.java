package uo.ri.persistence;

import java.sql.Connection;
import java.util.Map;

public interface FacturasGateway {
	public void setConnection(Connection connection);

	public Long getLastInvoiceNumber();

	public long crearFactura(Map<String, Object> mapa);

}
