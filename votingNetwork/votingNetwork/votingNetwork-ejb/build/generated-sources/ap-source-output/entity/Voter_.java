package entity;

import entity.Address;
import enumeration.Sex;
import enumeration.VoterState;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-04-05T21:19:53")
@StaticMetamodel(Voter.class)
public class Voter_ { 

    public static volatile SingularAttribute<Voter, String> firstName;
    public static volatile SingularAttribute<Voter, String> lastName;
    public static volatile SingularAttribute<Voter, String> password;
    public static volatile SingularAttribute<Voter, Address> address;
    public static volatile SingularAttribute<Voter, VoterState> voteState;
    public static volatile SingularAttribute<Voter, Sex> sex;
    public static volatile SingularAttribute<Voter, Integer> phoneNum;
    public static volatile SingularAttribute<Voter, Long> id;
    public static volatile SingularAttribute<Voter, String> nric;
    public static volatile SingularAttribute<Voter, String> card;

}