package com.ridematefinder.sql;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Route generated by hbm2java
 */
@Entity
@Table(name="route"
    ,schema="public"
)
public class Route  implements java.io.Serializable {


     private UUID id;
     private Car car;
     private User user;
     private String startPoint;
     private String endPoint;
     private Date dateOfRide;
     private String urlToMaps;
     private Integer availableSeats;
     private Set passengerses = new HashSet(0);

    public Route() {
    }

	
    public Route(UUID id) {
        this.id = id;
    }
    public Route(UUID id, Car car, User user, String startPoint, String endPoint, Date dateOfRide, String urlToMaps, Integer availableSeats, Set passengerses) {
       this.id = id;
       this.car = car;
       this.user = user;
       this.startPoint = startPoint;
       this.endPoint = endPoint;
       this.dateOfRide = dateOfRide;
       this.urlToMaps = urlToMaps;
       this.availableSeats = availableSeats;
       this.passengerses = passengerses;
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
    @JoinColumn(name="car_id")
    public Car getCar() {
        return this.car;
    }
    
    public void setCar(Car car) {
        this.car = car;
    }

@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="user_id")
    public User getUser() {
        return this.user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }

    
    @Column(name="start_point", length=255)
    public String getStartPoint() {
        return this.startPoint;
    }
    
    public void setStartPoint(String startPoint) {
        this.startPoint = startPoint;
    }

    
    @Column(name="end_point", length=255)
    public String getEndPoint() {
        return this.endPoint;
    }
    
    public void setEndPoint(String endPoint) {
        this.endPoint = endPoint;
    }

    @Temporal(TemporalType.DATE)
    @Column(name="date_of_ride", length=13)
    public Date getDateOfRide() {
        return this.dateOfRide;
    }
    
    public void setDateOfRide(Date dateOfRide) {
        this.dateOfRide = dateOfRide;
    }

    
    @Column(name="url_to_maps", length=511)
    public String getUrlToMaps() {
        return this.urlToMaps;
    }
    
    public void setUrlToMaps(String urlToMaps) {
        this.urlToMaps = urlToMaps;
    }

    
    @Column(name="available_seats")
    public Integer getAvailableSeats() {
        return this.availableSeats;
    }
    
    public void setAvailableSeats(Integer availableSeats) {
        this.availableSeats = availableSeats;
    }

@OneToMany(fetch=FetchType.LAZY, mappedBy="route")
    public Set getPassengerses() {
        return this.passengerses;
    }
    
    public void setPassengerses(Set passengerses) {
        this.passengerses = passengerses;
    }




}


