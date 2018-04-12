/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ebj.session.stateless.AdminSessionBeanLocal;
import entity.Voter;
import enumeration.VoterState;
import exception.VoterNotFoundException;
import java.io.IOException;
import javax.annotation.PostConstruct;
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
@Named(value = "updateNricManagedBean")
@RequestScoped
public class UpdateNricManagedBean {

    @EJB(name = "AdminSessionBeanLocal")
    private AdminSessionBeanLocal adminSessionBeanLocal;

    /**
     * Creates a new instance of UpdateNricManagedBean
     */
    private Boolean notVoted;
    private Voter voter;

    public UpdateNricManagedBean() throws IOException {
        voter = (Voter) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("voter");
        
        System.err.println("*********Getting NRIC***********" + voter.getNric() + "*******" + voter.getVoteState().toString());
        
        if (voter.getVoteState().equals(VoterState.ELIGIBLE)) {
            setNotVoted((Boolean) true);
        } else {
            setNotVoted((Boolean) false);
        }
    }

    public void updateNric(ActionEvent event) throws IOException {
        System.err.println("*********Update NRIC***********" + voter.getNric());
        try {

            adminSessionBeanLocal.updateVoterStatus((Voter) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("voter"));
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "The status of the voter have being updated!", null));
            FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("voter", null);
            FacesContext.getCurrentInstance().getExternalContext().redirect("checkNric.xhtml");
        } catch (VoterNotFoundException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid NRIC: " + ex.getMessage(), null));
        }
    }

    public void back(ActionEvent event) throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect("checkNric.xhtml");
    }

    /**
     * @return the notVoted
     */
    public Boolean getNotVoted() {
        return notVoted;
    }

    /**
     * @param notVoted the notVoted to set
     */
    public void setNotVoted(Boolean notVoted) {
        this.notVoted = notVoted;
    }

    /**
     * @return the voter
     */
    public Voter getVoter() {
        return voter;
    }

    /**
     * @param voter the voter to set
     */
    public void setVoter(Voter voter) {
        this.voter = voter;
    }

}
