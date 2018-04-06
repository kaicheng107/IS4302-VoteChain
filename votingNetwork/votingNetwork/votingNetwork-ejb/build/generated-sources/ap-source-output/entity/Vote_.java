package entity;

import entity.Candidate;
import enumeration.VotingState;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-04-06T13:19:28")
@StaticMetamodel(Vote.class)
public class Vote_ { 

    public static volatile SingularAttribute<Vote, String> UniqueCode;
    public static volatile ListAttribute<Vote, Candidate> candidate;
    public static volatile SingularAttribute<Vote, VotingState> votingState;
    public static volatile SingularAttribute<Vote, Long> id;

}