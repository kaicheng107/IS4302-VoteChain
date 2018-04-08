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
import javax.ejb.Local;

/**
 *
 * @author KaiCheng
 */
@Local
public interface EDSSessionBeanLocal {

    public EDS CreateEDS(EDS eds);

    public EDS GetEDSByUsername(String username) throws EDSNotFoundException;

    public EDS EDSLogin(String username, String password) throws InvalidLoginCredentialException;

    public Candidate CreateCandidate(Candidate cand);

    public Vote CreateVote(Vote vote)throws NotUniqueCodeException;

    public Vote GetVoteById(Long id) throws VoteNotFoundException;

    public void UpdateVote(Vote vote) throws VoteNotFoundException;

    public List<Candidate> getAllCandidate();

    public List<Vote> getAllVote();

}
