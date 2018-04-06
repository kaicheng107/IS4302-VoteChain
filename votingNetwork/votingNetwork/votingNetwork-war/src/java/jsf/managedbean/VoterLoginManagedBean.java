/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ebj.session.stateless.VoterSessionBeanLocal;
import entity.Voter;
import exception.InvalidLoginCredentialException;
import java.io.IOException;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

/**
 *
 * @author KaiCheng
 */
@Named(value = "voterLoginManagedBean")
@RequestScoped
public class VoterLoginManagedBean {

    @EJB(name = "VoterSessionBeanLocal")
    private VoterSessionBeanLocal voterSessionBeanLocal;
    
    private String nric;
    private String password;
    
    public VoterLoginManagedBean() {
    }
    
    public void login(ActionEvent event) throws IOException{
        try{
                System.err.println("****************Trying to Login*******************");
            Voter voter = voterSessionBeanLocal.voterLogin(getNric(), getPassword());
            FacesContext.getCurrentInstance().getExternalContext().getSession(true);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("isLogin", true);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("voter", voter);
            System.err.println("****************End of Login*******************");
            FacesContext.getCurrentInstance().getExternalContext().redirect("uniqueCode.xhtml");
        }catch(InvalidLoginCredentialException ex){
           FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid login credential: " + ex.getMessage(), null)); 
        }
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

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }
    
}
