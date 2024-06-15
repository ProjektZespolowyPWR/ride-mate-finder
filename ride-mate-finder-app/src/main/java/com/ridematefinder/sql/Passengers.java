package com.ridematefinder.sql;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.UUID;

/**
 * Passengers generated by hbm2java
 */
@Entity
@Table(name="passengers"
    ,schema="public"
)
public class Passengers  implements java.io.Serializable {


     private UUID id;
     private User user;
     private Route route;
     private String passengerSpot;
     private Integer accepted;

    public Passengers() {
    }

	
    public Passengers(UUID id) {
        this.id = id;
    }
    public Passengers(UUID id, User user, Route route, String passengerSpot, Integer accepted) {
       this.id = id;
       this.user = user;
       this.route = route;
       this.passengerSpot = passengerSpot;
       this.accepted = accepted;
    }
   
     @Id 

    
    @Column(name="id", unique=true, nullable=false)
    public UUID getId() {
        return this.id;
    }
    
    public void setId(UUID id) {
        this.id = id;
    }

@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="passenger_id")
    public User getUser() {
        return this.user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }

@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="route_id")
    public Route getRoute() {
        return this.route;
    }
    
    public void setRoute(Route route) {
        this.route = route;
    }

    
    @Column(name="passenger_spot", length=255)
    public String getPassengerSpot() {
        return this.passengerSpot;
    }
    
    public void setPassengerSpot(String passengerSpot) {
        this.passengerSpot = passengerSpot;
    }

    
    @Column(name="accepted")
    public Integer getAccepted() {
        return this.accepted;
    }
    
    public void setAccepted(Integer accepted) {
        this.accepted = accepted;
    }




}


