/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ebj.session.stateless;

import entity.Vote;
import entity.Voter;
import exception.InvalidLoginCredentialException;
import exception.VoteNotFoundException;
import exception.VoterNotFoundException;
import javax.ejb.Local;

/**
 *
 * @author KaiCheng
 */
@Local
public interface VoterSessionBeanLocal {

    public Voter CreateVoter(Voter voter);

    public Voter getVoterByUsername(String nric) throws VoterNotFoundException;

    public Voter voterLogin(String nric, String password) throws InvalidLoginCredentialException;

    public Voter findVoterByNric(String nric) throws VoterNotFoundException;

    public void updateVoterStatus(Voter voter) throws VoterNotFoundException;

    public void updateVote(Vote vote) throws VoteNotFoundException;

    public Vote getVoteByUniqueCode(String uniqueCode) throws VoteNotFoundException;
}
