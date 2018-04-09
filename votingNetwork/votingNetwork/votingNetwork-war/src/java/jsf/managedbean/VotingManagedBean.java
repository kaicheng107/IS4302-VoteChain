/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ebj.session.stateless.VoterSessionBeanLocal;
import entity.Candidate;
import entity.Vote;
import entity.Voter;
import exception.VoteNotFoundException;
import exception.VoterNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
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
    private Candidate selectedCandidate;

    public VotingManagedBean() {
        listCandidate = new ArrayList<>();
        selectItems = new ArrayList<>();

        selectedCandidate = new Candidate();
    }

    @PostConstruct
    public void postCanstruct() {
        vote = (Vote) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("vote");
        System.err.println("******" + vote.getUniqueCode());
        try {
            vote = voterSessionBeanLocal.getVoteByUniqueCode(vote.getUniqueCode());
        } catch (VoteNotFoundException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Unexpected error: " + ex.getMessage(), null));
        }

        System.err.println("****************vote***********" + vote.getUniqueCode());
        listCandidate = vote.getCandidate();

        for (Candidate candidate : listCandidate) {
            selectItems.add(new SelectItem(candidate, candidate.getCandidateName()));
        }

        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("listCandidate", listCandidate);
    }

    @PreDestroy
    public void preDestroy() {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("listCandidate", null);
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("vote", null);
    }

    public void submitVote(ActionEvent event) throws IOException {
        if (selectedCandidate == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Please select a candidate", null));
            return;
        }

        try {

            for (int x = 0; x < vote.getCandidate().size(); x++) {
                if (vote.getCandidate().get(x).getId().equals(selectedCandidate.getId())) {
                    vote.getCandidate().get(x).setVote(vote.getCandidate().get(x).getVote() + 1);
                    System.err.println(vote.getCandidate().get(x).getCandidateName() + "********" + vote.getCandidate().get(x).getVote());
                    break;
                }
            }

            voterSessionBeanLocal.updateVote(vote);
            selectedCandidate = new Candidate();

            voterSessionBeanLocal.updateVoterStatus((Voter) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("voter"));

            FacesContext.getCurrentInstance().getExternalContext().redirect("done.xhtml");

        } catch (VoteNotFoundException | VoterNotFoundException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Unexpected error has occured when updating the vote :" + ex.getMessage(), null));
        }

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

    /**
     * @return the selectedCandidate
     */
    public Candidate getSelectedCandidate() {
        return selectedCandidate;
    }

    /**
     * @param selectedCandidate the selectedCandidate to set
     */
    public void setSelectedCandidate(Candidate selectedCandidate) {
        this.selectedCandidate = selectedCandidate;
    }

}
