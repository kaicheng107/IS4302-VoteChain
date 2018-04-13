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
@XmlType(name = "placeVoteModel", propOrder = {
    "vote",
    "candidate"
})
public class PlaceVoteModel {
    private String vote;
    private String candidate;

    public PlaceVoteModel() {
    }

    public PlaceVoteModel(String vote, String candidate) {
        this.vote = vote;
        this.candidate = candidate;
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

    /**
     * @return the candidate
     */
    public String getCandidate() {
        return candidate;
    }

    /**
     * @param candidate the candidate to set
     */
    public void setCandidate(String candidate) {
        this.candidate = candidate;
    }
    
}
