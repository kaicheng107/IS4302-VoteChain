/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.ResponseProcessingException;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import ws.restful.datamodel.CandidateRest;

/**
 *
 * @author KaiCheng
 */
public class CreateCandidate {

    public CreateCandidate() {
    }
    
//    
//    public Response createCandidate(CandidateRest cr){
//        Client client = ClientBuilder.newClient();
//        try{
//        WebTarget target = client.target("http://localhost:3000/api/org.acme.voting.createCandidate");
//        Response rs = target.request(MediaType.APPLICATION_JSON).post(Entity.json(cr));
//        return rs;
//        }catch(ResponseProcessingException ex){
//            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "New Candidate created fail, please contact system admin! Error: "+ex.getMessage(), null));
//            Response error = new Response();
//}
//        }
//    }
}
