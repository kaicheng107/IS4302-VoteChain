/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ebj.session.stateless.AdminSessionBeanLocal;
import entity.Admin;
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
@Named(value = "adminLoginManagedBean")
@RequestScoped
public class AdminLoginManagedBean {

    @EJB(name = "AdminSessionBeanLocal")
    private AdminSessionBeanLocal adminSessionBeanLocal;

    private String username;
    private String password;
    /**
     * Creates a new instance of AdminLoginManagedBean
     */
    public AdminLoginManagedBean() {
    }
    
    public void login(ActionEvent event) throws IOException{
        try{
            System.err.println("****************Trying to Login*******************");
            
            Admin admin = adminSessionBeanLocal.adminLogin(getUsername(), getPassword());
            FacesContext.getCurrentInstance().getExternalContext().getSession(true);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("isLogin", true);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("admin", admin);
            
            System.err.println("****************End of Login*******************");
            FacesContext.getCurrentInstance().getExternalContext().redirect("checkNric.xhtml");
        }catch(InvalidLoginCredentialException ex){
           FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid login credential: " + ex.getMessage(), null)); 
        }
    }
    
    public String imagePath(){
    HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
    String url = req.getRequestURL().toString();
    String pathImg =url.substring(0, url.length() - req.getRequestURI().length()) + "/votingNetwork-war/images/assets/img_eld_logo.png";
    //System.out.println("*********Image Path using code**********"+pathImg);
    return url.substring(0, url.length() - req.getRequestURI().length()) + "/votingNetwork-war/images/assets/img_eld_logo.png";
    }
    
    public void logout(ActionEvent event) throws IOException
    {
        ((HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true)).invalidate();
        FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
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
