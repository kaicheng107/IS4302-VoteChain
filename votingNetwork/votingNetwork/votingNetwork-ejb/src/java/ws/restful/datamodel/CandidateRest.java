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
@XmlType(name = "candidateRest", propOrder = {
    "id",
    "candidateName"
})
public class CandidateRest {
    
    private String id;
    private String candidateName;

    public CandidateRest() {
    }

    public CandidateRest(String id, String candidateName) {
        this.id = id;
        this.candidateName = candidateName;
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the candidateName
     */
    public String getCandidateName() {
        return candidateName;
    }

    /**
     * @param candidateName the candidateName to set
     */
    public void setCandidateName(String candidateName) {
        this.candidateName = candidateName;
    }
    
    
}
