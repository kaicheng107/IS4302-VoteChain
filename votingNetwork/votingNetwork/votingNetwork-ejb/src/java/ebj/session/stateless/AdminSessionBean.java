/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ebj.session.stateless;

import entity.Admin;
import entity.Voter;
import enumeration.VoterState;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import exception.VoterNotFoundException;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;
import exception.InvalidLoginCredentialException;
import exception.AdminNotFoundException;

/**
 *
 * @author KaiCheng
 */
@Stateless
public class AdminSessionBean implements AdminSessionBeanLocal {

    @PersistenceContext(unitName = "votingNetwork-ejbPU")
    private EntityManager em;

    public AdminSessionBean() {
    }
    
    @Override
    public Admin createNewAdmin(Admin admin) {
        em.persist(admin);
        em.flush();

        return admin;
    }
    
    /******************************Admin Related admin Function********************************************/
    @Override
    public Admin getAdminByUsername(String username) throws AdminNotFoundException {
        Query query = em.createQuery("SELECT a FROM Admin a WHERE a.username = :username").setParameter("username", username);

        try {
            return (Admin) query.getSingleResult();
        } catch (NoResultException | NonUniqueResultException ex) {
            throw new AdminNotFoundException("Admin username " + username + " does not exist!");
        }
    }

    @Override
    public Admin adminLogin(String username, String password) throws InvalidLoginCredentialException {
        try {
            Admin admin = getAdminByUsername(username);

            if (admin.getPassword().equals(password)) {
                return admin;
            } else {
                throw new InvalidLoginCredentialException("Username does not exist or invalid password!");
            }
        } catch (AdminNotFoundException ex) {
            throw new InvalidLoginCredentialException("Username does not exist or invalid password!");
        }
    }
    
    
    /******************************Voter Related admin Function********************************************/
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
}
