package com.ridematefinder.sql;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;

/**
 * Driver generated by hbm2java
 */
@Entity
@Table(name="driver", schema="public")
public class Driver  implements java.io.Serializable {


    private UUID id;
    private String name;
    private String description;

    public Driver() {
    }

	
    public Driver(UUID id, String name) {
        this.id = id;
        this.name = name;
    }
    public Driver(UUID id, String name, String description) {
       this.id = id;
       this.name = name;
       this.description = description;
    }
   
    @Id
    @Column(name="id", unique=true, nullable=false)
    public UUID getId() {
        return this.id;
    }
    
    public void setId(UUID id) {
        this.id = id;
    }

    
    @Column(name="name", nullable=false, length=255)
    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    
    @Column(name="description")
    public String getDescription() {
        return this.description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
}


