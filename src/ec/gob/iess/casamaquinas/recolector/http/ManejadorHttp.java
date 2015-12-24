package ec.gob.iess.casamaquinas.recolector.http;

import java.io.IOException;
import java.util.List;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import ec.gob.iess.casamaquinas.recolector.dto.EstadoBombasDTO;
import ec.gob.iess.casamaquinas.recolector.dto.PresionFlujoDTO;
import ec.gob.iess.casamaquinas.recolector.dto.ReplicacionAguaDTO;
import ec.gob.iess.casamaquinas.recolector.dto.ReplicacionConsumoAguaDTO;

public class ManejadorHttp {

	//private static final String urlBase = "http://localhost:8080/hcam";
	private static final String urlBase = "http://hcam-iess.rhcloud.com";
	
	public Boolean enviarRegistrosAgua(List<ReplicacionAguaDTO> listado) {
		Gson gSon=  new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
		String json = gSon.toJson(listado);
		return enviar(json, urlBase + "/ReceptorAgua");
	}
	
	public Boolean enviarRegistrosConsumoAgua(List<ReplicacionConsumoAguaDTO> listado) {
		Gson gSon=  new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
		String json = gSon.toJson(listado);
		return enviar(json, urlBase + "/ReceptorConsumoAgua");
	}
	
	public Boolean enviarRegistrosConsumoMesAgua(List<ReplicacionConsumoAguaDTO> listado) {
		Gson gSon=  new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
		String json = gSon.toJson(listado);
		return enviar(json, urlBase + "/ReceptorConsumoMesAgua");
	}

	public void enviarRegistroPresion(PresionFlujoDTO presion) {
		Gson gSon=  new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
		String json = gSon.toJson(presion);
		enviar(json, urlBase + "/ReceptorPresion");
	}
	
	public void enviarRegistroEstadoBombas(EstadoBombasDTO estadoBombas) {
		Gson gSon=  new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
		String json = gSon.toJson(estadoBombas);
		enviar(json, urlBase + "/ReceptorEstadoBombas");
	}
	
	private Boolean enviar(String json, String url) {
		CloseableHttpClient httpclient = null;
		try {
			httpclient = HttpClients.createDefault();

			StringEntity requestEntity = new StringEntity(json,
					ContentType.APPLICATION_JSON);

			HttpPost postMethod = new HttpPost(url);
			postMethod.setEntity(requestEntity);

			CloseableHttpResponse response = httpclient.execute(postMethod);
			try {
				System.out.println("----------------------------------------");
				System.out.println(response.getStatusLine());
				System.out.println(EntityUtils.toString(response.getEntity()));
				if (response.getStatusLine().getStatusCode()!=200) {
					return false;
				}
			} finally {
				response.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				if (httpclient != null)
					httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return true;
	}
	
}
