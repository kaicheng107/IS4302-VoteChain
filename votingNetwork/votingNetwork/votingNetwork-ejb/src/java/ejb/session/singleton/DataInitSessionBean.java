/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.singleton;

import ebj.session.stateless.AdminSessionBeanLocal;
import ebj.session.stateless.EDSSessionBeanLocal;
import ebj.session.stateless.VoterSessionBeanLocal;
import entity.Address;
import entity.Admin;
import entity.EDS;
import entity.Voter;
import enumeration.Sex;
import enumeration.VoterState;
import exception.EDSNotFoundException;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.LocalBean;
import javax.ejb.Startup;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.ResponseProcessingException;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import ws.restful.datamodel.CandidateRest;

/**
 *
 * @author KaiCheng
 */
@Singleton
@LocalBean
@Startup
public class DataInitSessionBean {

    @EJB(name = "VoterSessionBeanLocal")
    private VoterSessionBeanLocal voterSessionBeanLocal;

    @EJB(name = "EDSSessionBeanLocal")
    private EDSSessionBeanLocal eDSSessionBeanLocal;

    @EJB(name = "AdminSessionBeanLocal")
    private AdminSessionBeanLocal adminSessionBeanLocal;

    @PersistenceContext(unitName = "votingNetwork-ejbPU")
    private EntityManager em;

    public DataInitSessionBean() {
    }

   @PostConstruct
   public void PostConstruct(){
       try{
           eDSSessionBeanLocal.GetEDSByUsername("EDSingapore");
       }catch(EDSNotFoundException ex){
           initializeData();
       }
   }

    private void initializeData() {
        try{
            //Create New EDS    
            eDSSessionBeanLocal.CreateEDS(new EDS("EDS", "EDSingapore", "password", "EDS@voting-network"));
            //Create New Admin
            adminSessionBeanLocal.createNewAdmin(new Admin("Euno CC 1", "Euno1", "password"));
            adminSessionBeanLocal.createNewAdmin(new Admin("Euno CC 2", "Euno2", "password"));
            adminSessionBeanLocal.createNewAdmin(new Admin("East Coast CC 1", "Eastcoast1", "password"));
            adminSessionBeanLocal.createNewAdmin(new Admin("East Coast CC 2", "Eastcoast2", "password"));
            //Create New Voter
            voterSessionBeanLocal.CreateVoter(new Voter("S1234567A", "password", 90908080, "Tom", "Tan", VoterState.ELIGIBLE, Sex.MALE, new Address("Blk 4 #04-13", "Eunos", "Singapore", "123456", "Eunos"), "voterHub@voting-network"));
            voterSessionBeanLocal.CreateVoter(new Voter("S9675432A", "password", 96969696, "Timothy", "Chew", VoterState.ELIGIBLE, Sex.MALE, new Address("Blk 249 #10-22", "Bishan", "Singapore", "387249", "Bishan"), "voterHub@voting-network"));
            voterSessionBeanLocal.CreateVoter(new Voter("S7654321F", "password", 91234567, "Rebecca", "Tan", VoterState.ELIGIBLE, Sex.FEMALE, new Address("Blk 12 #9-10", "Kent Ridge Street 12", "Singapore", "387249", "Kent Ridge"), "voterHub@voting-network"));
            voterSessionBeanLocal.CreateVoter(new Voter("S1446543H", "password", 90987654, "Alice", "Tan", VoterState.ELIGIBLE, Sex.FEMALE, new Address("Blk 4 #13-10", "Clementi", "Singapore", "152658", "Clementi"), "voterHub@voting-network"));
            voterSessionBeanLocal.CreateVoter(new Voter("S9675643L", "password", 94538712, "David", "Tan", VoterState.ELIGIBLE, Sex.MALE, new Address("Blk 724 #02-186", "Redhill", "Singapore", "758362", "Redhill"), "voterHub@voting-network"));
            voterSessionBeanLocal.CreateVoter(new Voter("S9619075X", "password", 96697974, "Tina", "Cheng", VoterState.ELIGIBLE, Sex.FEMALE, new Address("Blk 186 #18-23", "Haw Par Villa", "Singapore", "786186", "Haw Par Villa"), "voterHub@voting-network"));
            voterSessionBeanLocal.CreateVoter(new Voter("S9142637E", "password", 96354850, "Alvin", "Low", VoterState.ELIGIBLE, Sex.MALE, new Address("Blk 198 #01-10", "Tiong Bahru", "Singapore", "128724", "Tiong Bahru"), "voterHub@voting-network"));
            voterSessionBeanLocal.CreateVoter(new Voter("S6890642D", "password", 86754826, "Wendy", "Cheng", VoterState.ELIGIBLE, Sex.FEMALE, new Address("Blk 91 #19-21", "Marymount", "Singapore", "172091", "Marymount"), "voterHub@voting-network"));
            voterSessionBeanLocal.CreateVoter(new Voter("S8746590G", "password", 9253748, "Jeslyn", "Low", VoterState.ELIGIBLE, Sex.FEMALE, new Address("Blk 724 #11-03", "Pasir Ris", "Singapore", "144567", "Pasir Ris"), "voterHub@voting-network"));
            voterSessionBeanLocal.CreateVoter(new Voter("S9674829F", "password", 92679354, "Maria", "Alvarez", VoterState.ELIGIBLE, Sex.FEMALE, new Address("Blk 14 #14-16", "Bukit Batok", "Singapore", "842014", "Bukit Batok"), "voterHub@voting-network"));
            
            
        }catch(Exception ex){
            System.err.println("********** DataInitializationSessionBean.initializeData(): An error has occurred while loading initial test data: " + ex.getMessage());
        }
    }


}
