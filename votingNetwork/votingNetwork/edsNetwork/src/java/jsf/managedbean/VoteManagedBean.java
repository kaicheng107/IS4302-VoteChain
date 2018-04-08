/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ebj.session.stateless.EDSSessionBeanLocal;
import entity.Candidate;
import entity.Vote;
import enumeration.VotingState;
import exception.NotUniqueCodeException;
import exception.VoteNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

/**
 *
 * @author KaiCheng
 */
@Named(value = "voteManagedBean")
@ViewScoped
public class VoteManagedBean implements Serializable {

    @EJB(name = "EDSSessionBeanLocal")
    private EDSSessionBeanLocal eDSSessionBeanLocal;

    private List<Vote> votes;
    private List<Vote> filterVotes;
    private Vote newVote;
    private Vote selectVoteToView;
    private Vote selectVoteToUpdate;
    private List<Candidate> listCandidate;
    private List<SelectItem> selectItems;

    public VoteManagedBean() {
        votes = new ArrayList<>();
        filterVotes = new ArrayList<>();
        selectItems = new ArrayList<>();
        
        newVote = new Vote();
    }

    @PostConstruct
    public void postConstruct() {
        listCandidate = eDSSessionBeanLocal.getAllCandidate();

        for (Candidate candidate : listCandidate) {
            selectItems.add(new SelectItem(candidate, candidate.getCandidateName()));
        }

        votes = eDSSessionBeanLocal.getAllVote();
        filterVotes = votes;
        
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("listCandidate", listCandidate);
    }

    @PreDestroy
    public void preDestroy() {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("listCandidate", null);
    }

    public void createNewVote(ActionEvent event) throws IOException {

        for (int x = 0; x < newVote.getCandidate().size(); x++) {
            
            if (newVote.getCandidate().get(x).getVote() != 0) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Candidate Choose is not a  newly created candidate!", null));
                return;
            }
        }

        try {
            newVote.setVotingState(VotingState.PREPARATION);

            Vote updateVote = eDSSessionBeanLocal.CreateVote(newVote);
            votes.add(updateVote);

            newVote = new Vote();

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "New product created successfully (Vote ID: " + updateVote.getId().toString() + ")", null));
        } catch (NotUniqueCodeException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while creating the new vote: " + ex.getMessage(), null));
        }
    }

    public void openVote(ActionEvent event) throws IOException {
        try {
            selectVoteToUpdate =(Vote)event.getComponent().getAttributes().get("voteToOpen");
            selectVoteToUpdate.setVotingState(VotingState.OPEN);

            eDSSessionBeanLocal.UpdateVote(selectVoteToUpdate);

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Voting has open successfully (Vote ID: " + selectVoteToUpdate.getId().toString() + ")", null));
        } catch (VoteNotFoundException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occurred when opening vote: " + ex.getMessage(), null));
        }
    }

    public void closeVote(ActionEvent event) throws IOException {
        try {
            selectVoteToUpdate =(Vote)event.getComponent().getAttributes().get("voteToClosed");
            selectVoteToUpdate.setVotingState(VotingState.CLOSED);

            eDSSessionBeanLocal.UpdateVote(selectVoteToUpdate);

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Voting has closed successfully (Vote ID: " + selectVoteToUpdate.getId().toString() + ")", null));
        } catch (VoteNotFoundException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occurred when opening vote: " + ex.getMessage(), null));
        }
    }

    /**
     * @return the votes
     */
    public List<Vote> getVotes() {
        return votes;
    }

    /**
     * @param votes the votes to set
     */
    public void setVotes(List<Vote> votes) {
        this.votes = votes;
    }

    /**
     * @return the filterVotes
     */
    public List<Vote> getFilterVotes() {
        return filterVotes;
    }

    /**
     * @param filterVotes the filterVotes to set
     */
    public void setFilterVotes(List<Vote> filterVotes) {
        this.filterVotes = filterVotes;
    }

    /**
     * @return the newVote
     */
    public Vote getNewVote() {
        return newVote;
    }

    /**
     * @param newVote the newVote to set
     */
    public void setNewVote(Vote newVote) {
        this.newVote = newVote;
    }

    /**
     * @return the selectVoteToView
     */
    public Vote getSelectVoteToView() {
        return selectVoteToView;
    }

    /**
     * @param selectVoteToView the selectVoteToView to set
     */
    public void setSelectVoteToView(Vote selectVoteToView) {
        this.selectVoteToView = selectVoteToView;
    }

    /**
     * @return the selectVoteToUpdate
     */
    public Vote getSelectVoteToUpdate() {
        return selectVoteToUpdate;
    }

    /**
     * @param selectVoteToUpdate the selectVoteToUpdate to set
     */
    public void setSelectVoteToUpdate(Vote selectVoteToUpdate) {
        this.selectVoteToUpdate = selectVoteToUpdate;
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
