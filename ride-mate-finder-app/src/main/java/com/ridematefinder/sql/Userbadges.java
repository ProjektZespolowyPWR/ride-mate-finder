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
 * Userbadges generated by hbm2java
 */
@Entity
@Table(name="userbadges", schema="public")
public class Userbadges  implements java.io.Serializable {


     private UUID id;
     private Badges badges;
     private User user;

    public Userbadges() {
    }

	
    public Userbadges(UUID id) {
        this.id = id;
    }
    public Userbadges(UUID id, Badges badges, User user) {
       this.id = id;
       this.badges = badges;
       this.user = user;
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
    @JoinColumn(name="badge_id")
    public Badges getBadges() {
        return this.badges;
    }
    
    public void setBadges(Badges badges) {
        this.badges = badges;
    }

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="user_id")
    public User getUser() {
        return this.user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }
}
