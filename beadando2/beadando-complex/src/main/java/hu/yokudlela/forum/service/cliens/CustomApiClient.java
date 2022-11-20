package hu.yokudlela.forum.service.cliens;

import hu.yokudlela.media.java.clients.api.ImageControllerApi;
import hu.yokudlela.media.java.clients.invoker.auth.OAuth;
import hu.yokudlela.media.java.clients.model.ImageProcessModel;
import hu.yokudlela.table.utils.logging.CustomRequestLoggingFilter;
import hu.yokudlela.table.utils.request.RequestBean;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.media.multipart.file.FileDataBodyPart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 *
 * @author oe
 */
public class CustomApiClient extends ApiClient{

    public CustomApiClient() {super();}
    
    protected void updateParamsForAuth(String[] authNames, List<Pair> queryParams, Map<String, String> headerParams) {
    for (String authName : authNames) {
      Authentication auth = authentications.get(authName);
      if (auth != null) 
      auth.applyToParams(queryParams, headerParams);
    }
  }
    
}
