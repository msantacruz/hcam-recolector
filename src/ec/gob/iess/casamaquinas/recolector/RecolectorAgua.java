package ec.gob.iess.casamaquinas.recolector;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.boris.winrun4j.AbstractService;
import org.boris.winrun4j.ServiceException;

public class RecolectorAgua extends AbstractService {

	private static final Logger logger = LogManager.getLogger(RecolectorAgua.class);
	
	@Override
	public int serviceMain(String[] arg0) throws ServiceException {
		String path = "c:\\temp\\hcam-cliente\\";
		try {
			Process processAgua = Runtime.getRuntime().exec("c:\\Python27\\python.exe "+ path + "recolector.py");
			while(!shutdown) {
				if (!processAgua.isAlive()) {
					processAgua = Runtime.getRuntime().exec("c:\\Python27\\python.exe "+ path + "recolector.py");
				}	
				Thread.sleep(10000);
			}
		} catch(Throwable e) {
			logger.error("Error al recolectar agua", e);
		}
		return 0;
	}

}
