/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import enumeration.VotingState;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 *
 * @author KaiCheng
 */
@Entity
public class Vote implements Serializable {

   

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String uniqueCode;
    @OneToMany
    private List<Candidate> candidate;
    private VotingState votingState;

    public Vote() {
        candidate = new ArrayList<>();
    }

    public Vote(String UniqueCode, List<Candidate> candidate, VotingState votingState) {
        this.uniqueCode = UniqueCode;
        this.candidate = candidate;
        this.votingState = votingState;
    }
    
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Vote)) {
            return false;
        }
        Vote other = (Vote) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.vote[ id=" + id + " ]";
    }
     /**
     * @return the UniqueCode
     */
    public String getUniqueCode() {
        return uniqueCode;
    }

    /**
     * @param UniqueCode the UniqueCode to set
     */
    public void setUniqueCode(String uniqueCode) {
        this.uniqueCode = uniqueCode;
    }

    /**
     * @return the candidate
     */
    public List<Candidate> getCandidate() {
        return candidate;
    }

    /**
     * @param candidate the candidate to set
     */
    public void setCandidate(List<Candidate> candidate) {
        this.candidate = candidate;
    }

    /**
     * @return the votingState
     */
    public VotingState getVotingState() {
        return votingState;
    }

    /**
     * @param votingState the votingState to set
     */
    public void setVotingState(VotingState votingState) {
        this.votingState = votingState;
    }
}
