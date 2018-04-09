/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ebj.session.stateless;

import entity.Vote;
import entity.Voter;
import enumeration.VoterState;
import exception.InvalidLoginCredentialException;
import exception.VoteNotFoundException;
import exception.VoterNotFoundException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author KaiCheng
 */
@Stateless
public class VoterSessionBean implements VoterSessionBeanLocal {

    @PersistenceContext(unitName = "votingNetwork-ejbPU")
    private EntityManager em;

    public VoterSessionBean() {
    }
    
    
    @Override
    public Voter CreateVoter(Voter voter){
        em.persist(voter.getAddress());
        em.persist(voter);
        em.flush();
        
        return voter;
    }
    
    @Override
    public Voter getVoterByUsername(String nric) throws VoterNotFoundException {
        Query query = em.createQuery("SELECT v FROM Voter v WHERE v.nric = :nric").setParameter("nric", nric);
        
        System.err.println("****************Querying Username*******************");
        
        try {
            return (Voter) query.getSingleResult();
        } catch (NoResultException | NonUniqueResultException ex) {
            throw new VoterNotFoundException("Voter nric " + nric + " does not exist!");
        }
    }

    @Override
    public Voter voterLogin(String nric, String password) throws InvalidLoginCredentialException {
        try {
            System.err.println("****************Getting user BY USERNAME*******************");
            Voter voter = getVoterByUsername(nric);
            System.err.println("****************Get Back Username*******************");
            if (voter.getPassword().equals(password)) {
                System.err.println("****************Voter Login*******************");
                return voter;
            } else {
                throw new InvalidLoginCredentialException("Nric does not exist or invalid password!");
            }
        } catch (VoterNotFoundException ex) {
            throw new InvalidLoginCredentialException("Nric does not exist or invalid password!");
        }
    }
    
    @Override
    public Voter findVoterByNric(String nric) throws VoterNotFoundException {
        Query query = em.createQuery("SELECT v FROM Voter v WHERE v.nric = :nric").setParameter("nric", nric);

        try {
            return (Voter) query.getSingleResult();
        } catch (NoResultException | NonUniqueResultException ex) {
            throw new VoterNotFoundException("Voter NRIC " + nric + "does not exist!");
        }
    }

    @Override
    public void updateVoterStatus(Voter voter) throws VoterNotFoundException {
        if (voter.getId() != null) {
            Voter voterToUpdate = findVoterByNric(voter.getNric());
            if (voter.getNric().equals(voterToUpdate.getNric())) {
                voterToUpdate.setVoteState(VoterState.VOTED);
            }
        } else {
            throw new VoterNotFoundException("Invalid NRIC provided");
        }
    }
    
    @Override
    public void updateVote(Vote vote) throws VoteNotFoundException{
        if(vote.getId()!=null){
            System.err.println("****Update Vote****"+vote.getCandidate().get(0).getVote());
            
            Vote voteToUpdate = getVoteByUniqueCode(vote.getUniqueCode());
            
            for(int x=0;x<voteToUpdate.getCandidate().size();x++){
                voteToUpdate.getCandidate().get(x).setVote(vote.getCandidate().get(x).getVote());
            }
            
            
        }else{
            throw new VoteNotFoundException("Vote ID provided is Invalid!");
        }
    }
    
    @Override
    public Vote getVoteByUniqueCode(String uniqueCode) throws VoteNotFoundException{
     Query query = em.createQuery("SELECT v FROM Vote v WHERE v.uniqueCode = :code").setParameter("code", uniqueCode);
        
        System.err.println("****************Querying uniqueCode*******************");
        
        try {
            Vote vote = (Vote) query.getSingleResult();
            vote.getCandidate().size();
            return vote;
        } catch (NoResultException | NonUniqueResultException ex) {
            throw new VoteNotFoundException("Vote Unique Code " + uniqueCode + " does not exist!");
        }
    }
}
