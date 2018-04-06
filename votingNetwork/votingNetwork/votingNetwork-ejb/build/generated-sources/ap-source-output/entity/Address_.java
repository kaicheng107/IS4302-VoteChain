package entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-04-06T13:19:28")
@StaticMetamodel(Address.class)
public class Address_ { 

    public static volatile SingularAttribute<Address, String> country;
    public static volatile SingularAttribute<Address, String> street;
    public static volatile SingularAttribute<Address, String> postcode;
    public static volatile SingularAttribute<Address, Long> id;
    public static volatile SingularAttribute<Address, String> region;
    public static volatile SingularAttribute<Address, String> house;

}