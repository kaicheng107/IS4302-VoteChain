/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ebj.session.stateless.AdminSessionBeanLocal;
import entity.Vote;
import entity.Voter;
import exception.VoteNotFoundException;
import exception.VoterNotFoundException;
import java.io.IOException;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpSession;

/**
 *
 * @author KaiCheng
 */
@Named(value = "checkManagedBean")
@RequestScoped
public class CheckManagedBean {

    @EJB(name = "AdminSessionBeanLocal")
    private AdminSessionBeanLocal adminSessionBeanLocal;
    
    private String nric;
    /**
     * Creates a new instance of CheckManagedBean
     */
    public CheckManagedBean() {
    }
    
    public void CheckNric(ActionEvent event) throws IOException{
        try{
            System.err.println("****************Trying to Query NRIC*******************");
            Voter voter = adminSessionBeanLocal.findVoterByNric(getNric());
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("voter", voter);
            FacesContext.getCurrentInstance().getExternalContext().redirect("updateNric.xhtml");
            
        }catch(VoterNotFoundException ex){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid NRIC: " + ex.getMessage(), null)); 
        }
    }
    
    public void logout(ActionEvent event) throws IOException
    {
        System.err.println("*****************Logout of Website**********************");
        ((HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true)).invalidate();
        FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
    }

    /**
     * @return the nric
     */
    public String getNric() {
        return nric;
    }

    /**
     * @param nric the nric to set
     */
    public void setNric(String nric) {
        this.nric = nric;
    }
    
    
    
}
