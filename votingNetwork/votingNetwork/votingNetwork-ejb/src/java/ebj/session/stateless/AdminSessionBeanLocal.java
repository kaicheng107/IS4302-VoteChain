/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ebj.session.stateless;

import entity.Admin;
import entity.Voter;
import exception.AdminNotFoundException;
import exception.InvalidLoginCredentialException;
import exception.VoterNotFoundException;
import javax.ejb.Local;

/**
 *
 * @author KaiCheng
 */
@Local
public interface AdminSessionBeanLocal {

    public Admin createNewAdmin(Admin admin);

    public Voter findVoterByNric(String nric) throws VoterNotFoundException;

    public void updateVoterStatus(Voter voter) throws VoterNotFoundException;

    public Admin getAdminByUsername(String username) throws AdminNotFoundException;

    public Admin adminLogin(String username, String password) throws InvalidLoginCredentialException;

}
