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
import java.util.Objects;
import javax.ejb.Stateless;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import ws.restful.datamodel.PlaceVoteModel;

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
    public Voter CreateVoter(Voter voter) {
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
    public void updateVote(Vote vote) throws VoteNotFoundException {
        if (vote.getId() != null) {
            System.err.println("****Update Vote****" + vote.getCandidate().get(0).getVote());

            Vote voteToUpdate = getVoteByUniqueCode(vote.getUniqueCode());

            for (int x = 0; x < voteToUpdate.getCandidate().size(); x++) {
                if (!Objects.equals(voteToUpdate.getCandidate().get(x).getVote(), vote.getCandidate().get(x).getVote())) {
                    voteToUpdate.getCandidate().get(x).setVote(vote.getCandidate().get(x).getVote());
          
                     Client client = ClientBuilder.newClient();

                    WebTarget target = client.target("http://localhost:3001/api/org.acme.voting.PlaceVotes");
                    System.out.println("********************* REquesting ********" + target.getUri());
                    String candidate = "resource:org.acme.voting.Candidate#";
                    PlaceVoteModel pvm = new PlaceVoteModel(voteToUpdate.getUniqueCode(), candidate.concat(voteToUpdate.getCandidate().get(x).getId().toString()));

                    Response rs = target.request().post(Entity.json(pvm));
                    if (rs.getStatus() != 200) {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "New vote creation fail, please contact system admin! " + rs.getStatus(), null));
                    }
                    
                    client.close();
                   
                }
            }

        } else {
            throw new VoteNotFoundException("Vote ID provided is Invalid!");
        }
    }

    @Override
    public Vote getVoteByUniqueCode(String uniqueCode) throws VoteNotFoundException {
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
