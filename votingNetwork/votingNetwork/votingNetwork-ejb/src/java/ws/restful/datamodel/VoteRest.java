/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.datamodel;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author KaiCheng
 */
@XmlRootElement
@XmlType(name = "voteRest", propOrder = {
    "uniqueCode",
    "candidate"
})
public class VoteRest {
    
    private String uniqueCode;
    private List<Integer> candidate;

    public VoteRest() {
        candidate = new ArrayList<>();
    }

    public VoteRest(String uniqueCode, List<Integer> candidate) {
        this.uniqueCode = uniqueCode;
        this.candidate = candidate;
    }

    /**
     * @return the uniqueCode
     */
    public String getUniqueCode() {
        return uniqueCode;
    }

    /**
     * @param uniqueCode the uniqueCode to set
     */
    public void setUniqueCode(String uniqueCode) {
        this.uniqueCode = uniqueCode;
    }

    /**
     * @return the candidate
     */
    public List<Integer> getCandidate() {
        return candidate;
    }

    /**
     * @param candidate the candidate to set
     */
    public void setCandidate(List<Integer> candidate) {
        this.candidate = candidate;
    }
    
    
}
