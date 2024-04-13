package com.ridematefinder.sql;
// Generated 13 kwi 2024, 15:14:44 by Hibernate Tools 6.4.4.Final


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.Version;
import java.sql.Timestamp;

/**
 * FlywaySchemaHistory generated by hbm2java
 */
@Entity
@Table(name="flyway_schema_history"
    ,schema="public"
)
public class FlywaySchemaHistory  implements java.io.Serializable {


     private int installedRank;
     private String version;
     private String description;
     private String type;
     private String script;
     private Integer checksum;
     private String installedBy;
     private Timestamp installedOn;
     private int executionTime;
     private boolean success;

    public FlywaySchemaHistory() {
    }

	
    public FlywaySchemaHistory(int installedRank, String description, String type, String script, String installedBy, Timestamp installedOn, int executionTime, boolean success) {
        this.installedRank = installedRank;
        this.description = description;
        this.type = type;
        this.script = script;
        this.installedBy = installedBy;
        this.installedOn = installedOn;
        this.executionTime = executionTime;
        this.success = success;
    }
    public FlywaySchemaHistory(int installedRank, String description, String type, String script, Integer checksum, String installedBy, Timestamp installedOn, int executionTime, boolean success) {
       this.installedRank = installedRank;
       this.description = description;
       this.type = type;
       this.script = script;
       this.checksum = checksum;
       this.installedBy = installedBy;
       this.installedOn = installedOn;
       this.executionTime = executionTime;
       this.success = success;
    }
   
     @Id 

    
    @Column(name="installed_rank", unique=true, nullable=false)
    public int getInstalledRank() {
        return this.installedRank;
    }
    
    public void setInstalledRank(int installedRank) {
        this.installedRank = installedRank;
    }

    @Version
    @Column(name="version", length=50)
    public String getVersion() {
        return this.version;
    }
    
    public void setVersion(String version) {
        this.version = version;
    }

    
    @Column(name="description", nullable=false, length=200)
    public String getDescription() {
        return this.description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }

    
    @Column(name="type", nullable=false, length=20)
    public String getType() {
        return this.type;
    }
    
    public void setType(String type) {
        this.type = type;
    }

    
    @Column(name="script", nullable=false, length=1000)
    public String getScript() {
        return this.script;
    }
    
    public void setScript(String script) {
        this.script = script;
    }

    
    @Column(name="checksum")
    public Integer getChecksum() {
        return this.checksum;
    }
    
    public void setChecksum(Integer checksum) {
        this.checksum = checksum;
    }

    
    @Column(name="installed_by", nullable=false, length=100)
    public String getInstalledBy() {
        return this.installedBy;
    }
    
    public void setInstalledBy(String installedBy) {
        this.installedBy = installedBy;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="installed_on", nullable=false, length=29)
    public Timestamp getInstalledOn() {
        return this.installedOn;
    }
    
    public void setInstalledOn(Timestamp installedOn) {
        this.installedOn = installedOn;
    }

    
    @Column(name="execution_time", nullable=false)
    public int getExecutionTime() {
        return this.executionTime;
    }
    
    public void setExecutionTime(int executionTime) {
        this.executionTime = executionTime;
    }

    
    @Column(name="success", nullable=false)
    public boolean isSuccess() {
        return this.success;
    }
    
    public void setSuccess(boolean success) {
        this.success = success;
    }




}


