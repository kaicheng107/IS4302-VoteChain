/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ebj.session.stateless.EDSSessionBeanLocal;
import entity.Candidate;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;


/**
 *
 * @author KaiCheng
 */
@Named(value = "candidateManagedBean")
@RequestScoped
public class CandidateManagedBean {

    @EJB(name = "EDSSessionBeanLocal")
    private EDSSessionBeanLocal eDSSessionBeanLocal;
    
    private List<Candidate> candidateList;
    private List<Candidate> filteredCandidateList;
    private Candidate newCandidate;
    
    public CandidateManagedBean() {
        candidateList = new ArrayList<>();
        filteredCandidateList = new ArrayList<>();
        
        newCandidate = new Candidate();
    }
    
    @PostConstruct
    public void postConstruct()
    {
        setCandidateList(eDSSessionBeanLocal.getAllCandidate());
        setFilteredCandidateList(getCandidateList());
    }
    
    public void createNewCandidate(ActionEvent event) throws IOException{
        
        
        
        try{
            getNewCandidate().setVote(0);
        
        System.out.println("****************** candinate name = " + newCandidate.getCandidateName() );
         System.out.println("****************** vote name = " + newCandidate.getVote() );
        Candidate candidate = eDSSessionBeanLocal.CreateCandidate(getNewCandidate());
//        
//        CandidateRest cr = new CandidateRest(candidate.getId().toString(), candidate.getCandidateName());
//        
//        CreateCandidate cc = new CreateCandidate();
//        
//        Response rs = cc.addCandidate(cr);
//        
//        if(rs.getStatus()!=200){
//            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "New Candidate not created successfully (Candidate ID: " + candidate.getId().toString() + ")", null));
//        }
        
        
        getCandidateList().add(candidate);
        
        setNewCandidate(new Candidate());
        
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "New Candidate created successfully (Candidate ID: " + candidate.getId().toString() + ")", null));
            
        }catch(Exception ex){
             System.out.println("****************** Exception in managed bean*****" );
             ex.printStackTrace();
        }
        
    }

    /**
     * @return the candidateList
     */
    public List<Candidate> getCandidateList() {
        return candidateList;
    }

    /**
     * @param candidateList the candidateList to set
     */
    public void setCandidateList(List<Candidate> candidateList) {
        this.candidateList = candidateList;
    }

    /**
     * @return the filteredCandidateList
     */
    public List<Candidate> getFilteredCandidateList() {
        return filteredCandidateList;
    }

    /**
     * @param filteredCandidateList the filteredCandidateList to set
     */
    public void setFilteredCandidateList(List<Candidate> filteredCandidateList) {
        this.filteredCandidateList = filteredCandidateList;
    }

    /**
     * @return the newCandidate
     */
    public Candidate getNewCandidate() {
        return newCandidate;
    }

    /**
     * @param newCandidate the newCandidate to set
     */
    public void setNewCandidate(Candidate newCandidate) {
        this.newCandidate = newCandidate;
    }
    
    
}
