/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ebj.session.stateless.VoterSessionBeanLocal;
import entity.Voter;
import enumeration.VoterState;
import exception.InvalidLoginCredentialException;
import java.io.IOException;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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

    public void login(ActionEvent event) throws IOException {
        try {
            System.err.println("****************Trying to Login*******************");

            Voter voter = voterSessionBeanLocal.voterLogin(getNric(), getPassword());
            if (voter.getVoteState().equals(VoterState.ELIGIBLE)) {
                FacesContext.getCurrentInstance().getExternalContext().getSession(true);
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("isLogin", true);
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("voter", voter);
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("voted", false);
                
                System.err.println("****************End of Login*******************");
                FacesContext.getCurrentInstance().getExternalContext().redirect("uniqueCode.xhtml");
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Voter " + voter.getFirstName() + " " + voter.getLastName() + " is not eligible for voting or have voted already!", null));
            }
        } catch (InvalidLoginCredentialException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid login credential: " + ex.getMessage(), null));
        }
    }

    public void logout(ActionEvent event) throws IOException {
        ((HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true)).invalidate();
        FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
    }

    public String imagePath() {
        HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String url = req.getRequestURL().toString();
        String pathImg = url.substring(0, url.length() - req.getRequestURI().length()) + "/votingNetwork-war/images/assets/img_eld_logo.png";
        System.out.println("*********Image Path using code**********"+pathImg);
        return url.substring(0, url.length() - req.getRequestURI().length()) + "/votingNetwork-war/images/assets/img_eld_logo.png";
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
