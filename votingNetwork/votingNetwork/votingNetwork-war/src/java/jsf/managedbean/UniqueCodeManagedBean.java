/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ebj.session.stateless.VoterSessionBeanLocal;
import entity.Vote;
import exception.VoteNotFoundException;
import java.io.IOException;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

/**
 *
 * @author KaiCheng
 */
@Named(value = "uniqueCodeManagedBean")
@RequestScoped
public class UniqueCodeManagedBean {

    @EJB(name = "VoterSessionBeanLocal")
    private VoterSessionBeanLocal voterSessionBeanLocal;

    /**
     * Creates a new instance of UniqueCodeManagedBean
     */
    
    private String uniqueCode;
    
    public UniqueCodeManagedBean() {
        
    }
    public void uniqueCodeSubmit(ActionEvent event) throws IOException{
        try{
            System.err.println("****************Trying to Query Unique Code*******************");
            Vote vote =voterSessionBeanLocal.getVoteByUniqueCode(getUniqueCode());
            FacesContext.getCurrentInstance().getExternalContext().getFlash().put("vote", vote);
            FacesContext.getCurrentInstance().getExternalContext().redirect("voting.xhtml");
            
        }catch(VoteNotFoundException ex){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid Unique Code: " + ex.getMessage(), null)); 
        }
    }
    /**
     * @return the uniqueCode
     */
    public String getUniqueCode() {
        return uniqueCode;
    }

    /**
     * @param uniqueCode the uniqueCode to set
     */
    public void setUniqueCode(String uniqueCode) {
        this.uniqueCode = uniqueCode;
    }
    
    
    
}
