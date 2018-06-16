package uo.ri.conf;

import java.io.IOException;
import java.util.Properties;

/**
 * Clase que nos permita externalizar las consultas que serán invocadas 
 * por la capa de persistencia
 * 
 * @author José Antonio García García
 *
 */
public class Conf {

	public static final String CONF_FILE = "configuration.properties";

	private static Conf instance;
	private Properties properties;

	private Conf() {
		properties = new Properties();
		try {
			properties.load(
					Conf.class.getClassLoader().getResourceAsStream(CONF_FILE));
		} catch (IOException ioe) {
			throw new RuntimeException(
					"No se puede cargar el fichero de propiedades");
		}
	}

	public static String get(String key) {
		return getInstance().getProperty(key);
	}

	private String getProperty(String key) {
		String value = properties.getProperty(key);
		if (value == null)
			throw new RuntimeException(
					"No se ha podido encontrar la propiedad en el"
							+ " fichero de configuracion");
		return value;
	}

	public static Conf getInstance() {
		if (instance == null)
			instance = new Conf();
		return instance;
	}

}
