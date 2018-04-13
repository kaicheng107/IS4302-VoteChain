/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.datamodel;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author KaiCheng
 */
@XmlRootElement
@XmlType(name = "voteRest", propOrder = {
    "vote"
})
public class UpdateVoteRest {
    
    private String vote;

    public UpdateVoteRest() {
    }

    public UpdateVoteRest(String vote) {
        this.vote = vote;
    }
 
    /**
     * @return the vote
     */
    public String getVote() {
        return vote;
    }

    /**
     * @param vote the vote to set
     */
    public void setVote(String vote) {
        this.vote = vote;
    }

    
}
