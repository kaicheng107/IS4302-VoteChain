/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ebj.session.stateless;

import entity.Candidate;
import entity.EDS;
import entity.Vote;
import exception.EDSNotFoundException;
import exception.InvalidLoginCredentialException;
import exception.NotUniqueCodeException;
import exception.VoteNotFoundException;
import java.util.List;
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

        return cand;
    }

    @Override
    public List<Candidate> getAllCandidate() {
        return em.createQuery("SELECT c FROM Candidate c ORDER BY c.id ASC").getResultList();
    }

    //Use for create Vote
    @Override
    public Vote CreateVote(Vote vote) throws NotUniqueCodeException {
        List<Vote> checkVote = (List<Vote>) em.createQuery("SELECT v FROM Vote v WHERE v.uniqueCode=:code").setParameter("code", vote.getUniqueCode()).getResultList();
        System.err.println(checkVote);
        if (checkVote.isEmpty()) {

            em.persist(vote);
            em.flush();

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
        } else {
            throw new VoteNotFoundException("Vote ID provided is Invalid!");
        }
    }
}
