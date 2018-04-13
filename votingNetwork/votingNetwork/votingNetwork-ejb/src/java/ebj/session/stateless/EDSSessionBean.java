/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ebj.session.stateless;

import entity.Candidate;
import entity.EDS;
import entity.Vote;
import enumeration.VotingState;
import exception.EDSNotFoundException;
import exception.InvalidLoginCredentialException;
import exception.NotUniqueCodeException;
import exception.VoteNotFoundException;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.ResponseProcessingException;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import ws.restful.datamodel.CandidateRest;
import ws.restful.datamodel.UpdateVoteRest;
import ws.restful.datamodel.VoteRest;

/**
 *
 * @author KaiCheng
 */
@Stateless
public class EDSSessionBean implements EDSSessionBeanLocal {

    @PersistenceContext(unitName = "votingNetwork-ejbPU")
    private EntityManager em;

    public EDSSessionBean() {
    }

//******************************EDS Related EDS Function********************************************
    @Override
    public EDS CreateEDS(EDS eds) {
        em.persist(eds);
        em.flush();
        return eds;
    }

    @Override
    public EDS GetEDSByUsername(String username) throws EDSNotFoundException {
        Query query = em.createQuery("SELECT e FROM EDS e WHERE e.username=:username").setParameter("username", username);

        try {
            return (EDS) query.getSingleResult();
        } catch (NoResultException | NonUniqueResultException ex) {
            throw new EDSNotFoundException("EDS username " + username + " does not exists!");
        }
    }

    @Override
    public EDS EDSLogin(String username, String password) throws InvalidLoginCredentialException {
        try {
            EDS eds = GetEDSByUsername(username);

            if (password.equals(eds.getPassword())) {
                return eds;
            } else {
                throw new InvalidLoginCredentialException("Username does not exist or invalid password!");
            }
        } catch (EDSNotFoundException ex) {
            throw new InvalidLoginCredentialException("Username does not exist or invalid password!");
        }
    }

    //****************************** Voting Related EDS Functions*********************************************
    //Create New Candidate
    @Override
    public Candidate CreateCandidate(Candidate cand) {
        em.persist(cand);
        em.flush();
        em.refresh(cand);
        System.out.println("******Persist succesfuly ***********");

        Client client = ClientBuilder.newClient();
        try {
            WebTarget target = client.target("http://localhost:3000/api/org.acme.voting.createCandidate");
            System.out.println("********************* REquesting ********" + target.getUri());

            CandidateRest newCandiate = new CandidateRest(cand.getId().toString(), cand.getCandidateName());

            Response rs = target.request().post(Entity.json(newCandiate));
        } catch (ResponseProcessingException ex) {
            ex.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "New Candidate created fail, please contact system admin! Error: " + ex.getMessage(), null));
            throw new NotFoundException("New Canddiate fail to create! Please contact system admin!");
        }
        client.close();

        return cand;
    }

    @Override
    public List<Candidate> getAllCandidate() {
        return em.createQuery("SELECT c FROM Candidate c ORDER BY c.id ASC").getResultList();
    }

    @Override
    public List<Candidate> getAllZeroVoteCandidate() {
        return em.createQuery("SELECT c FROM Candidate c WHERE c.vote=0 ORDER BY c.id ASC").getResultList();
    }

    //Use for create Vote
    @Override
    public Vote CreateVote(Vote vote) throws NotUniqueCodeException {
        List<Vote> checkVote = (List<Vote>) em.createQuery("SELECT v FROM Vote v WHERE v.uniqueCode=:code").setParameter("code", vote.getUniqueCode()).getResultList();
        System.err.println(checkVote);
        if (checkVote.isEmpty()) {

            em.persist(vote);
            em.flush();
            em.refresh(vote);

            Client client = ClientBuilder.newClient();
            WebTarget target = client.target("http://localhost:3000/api/org.acme.voting.CreateVoting");
            System.out.println("********************* Requesting ********");

            VoteRest vr = new VoteRest();
            vr.setUniqueCode(vote.getUniqueCode());

            List<Integer> candidateId = new ArrayList<>();

            for (int i = 0; i < vote.getCandidate().size(); i++) {
                candidateId.add(vote.getCandidate().get(i).getId().intValue());
            }
            vr.setCandidate(candidateId);

            Response rs = target.request().post(Entity.json(vr));
            if (rs.getStatus() != 200) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "New vote creation fail, please contact system admin! Error: "+rs.getStatus(), null));
            }
            client.close();
            return vote;
        } else {
            throw new NotUniqueCodeException("The Unique Code enter is not unique!");
        }

    }

    @Override
    public List<Vote> getAllVote() {
        return em.createQuery("SELECT v FROM Vote v ORDER BY v.id ASC").getResultList();
    }

    @Override
    public Vote GetVoteById(Long id) throws VoteNotFoundException {
        Query query = em.createQuery("SELECT v FROM Vote v WHERE v.id=:id").setParameter("id", id);

        try {
            return (Vote) query.getSingleResult();
        } catch (NoResultException | NonUniqueResultException ex) {
            throw new VoteNotFoundException("Vote ID provided is Invalid!");
        }
    }

    //Use for Open Vote and Closed Vote
    @Override
    public void UpdateVote(Vote vote) throws VoteNotFoundException {
        if (vote.getId() != null) {
            em.merge(vote);

            Client client = ClientBuilder.newClient();

            if (vote.getVotingState().equals(VotingState.OPEN)) {

                WebTarget target = client.target("http://localhost:3000/api/org.acme.voting.OpenVoting");
                System.out.println("********************* Requesting ********");

                UpdateVoteRest uvr = new UpdateVoteRest(vote.getUniqueCode());
                Response rs = target.request().post(Entity.json(uvr));
                if (rs.getStatus() != 200) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Fail to update of Vote, please contact system admin! Error: " + rs.getStatus(), null));
                }

            } else if (vote.getVotingState().equals(VotingState.CLOSED)) {

                WebTarget target = client.target("http://localhost:3000/api/org.acme.voting.CloseVoting");
                System.out.println("********************* Requesting ********");

                UpdateVoteRest uvr = new UpdateVoteRest(vote.getUniqueCode());
                Response rs = target.request().post(Entity.json(uvr));
                if (rs.getStatus() != 200) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Fail to update of Vote, please contact system admin! Error: " + rs.getStatus(), null));
                }
            }
            client.close();

        } else {
            throw new VoteNotFoundException("Vote ID provided is Invalid!");
        }
    }
}
