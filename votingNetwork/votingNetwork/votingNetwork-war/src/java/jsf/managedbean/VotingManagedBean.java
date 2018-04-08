/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ebj.session.stateless.VoterSessionBeanLocal;
import entity.Candidate;
import entity.Vote;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

/**
 *
 * @author KaiCheng
 */
@Named(value = "votingManagedBean")
@RequestScoped
public class VotingManagedBean {

    @EJB(name = "VoterSessionBeanLocal")
    private VoterSessionBeanLocal voterSessionBeanLocal;
    
    private Vote vote;
    private List<Candidate> listCandidate;
    private List<SelectItem> selectItems;

    public VotingManagedBean() {
        listCandidate = new ArrayList<>();
        selectItems = new ArrayList<>();
    }
    
    @PostConstruct
    public void postCanstruct(){
        vote = (Vote)FacesContext.getCurrentInstance().getExternalContext().getFlash().get("vote");
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("vote", vote);
        
        listCandidate = vote.getCandidate();
        
         for (Candidate candidate : listCandidate) {
            selectItems.add(new SelectItem(candidate, candidate.getCandidateName()));
        }
         
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("listCandidate", listCandidate);
    }
    
    
    /**
     * @return the vote
     */
    public Vote getVote() {
        return vote;
    }

    /**
     * @param vote the vote to set
     */
    public void setVote(Vote vote) {
        this.vote = vote;
    }

    /**
     * @return the listCandidate
     */
    public List<Candidate> getListCandidate() {
        return listCandidate;
    }

    /**
     * @param listCandidate the listCandidate to set
     */
    public void setListCandidate(List<Candidate> listCandidate) {
        this.listCandidate = listCandidate;
    }

    /**
     * @return the selectItems
     */
    public List<SelectItem> getSelectItems() {
        return selectItems;
    }

    /**
     * @param selectItems the selectItems to set
     */
    public void setSelectItems(List<SelectItem> selectItems) {
        this.selectItems = selectItems;
    }
    
    
}
