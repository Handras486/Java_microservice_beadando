
package hu.yokudlela.forum.service.cliens;

import hu.yokudlela.media.java.clients.api.ImageControllerApi;
import hu.yokudlela.media.java.clients.invoker.auth.OAuth;
import hu.yokudlela.table.utils.logging.CustomRequestLoggingFilter;
import hu.yokudlela.table.utils.request.RequestBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author Krisztian
 */
@Service
@Slf4j
public class MediaImageControllerService {
    
    @Value("${client.media}")
    private String path;
    
    @Autowired
    private RequestBean rqb;
    
    public ImageControllerApi getClientInstance(){
        ImageControllerApi api = new ImageControllerApi();
        api.getApiClient().setBasePath(path);
        api.getApiClient().addDefaultHeader(CustomRequestLoggingFilter.REQUEST_ID, rqb.getRequestId());
        return new ImageControllerApi();
    }
    
    public ImageControllerApi getClientInstanceWithToken(String pToken){
        ImageControllerApi api = new ImageControllerApi(new CustomApiClient());
        api.getApiClient().setBasePath(path);
        api.getApiClient().addDefaultHeader(CustomRequestLoggingFilter.REQUEST_ID, rqb.getRequestId());
        ((OAuth)api.getApiClient().getAuthentication("oauth2")).setAccessToken(pToken.substring("Bearer ".length()));
        return api;
    }
}
