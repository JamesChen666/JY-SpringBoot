package com.boot.model;

import org.beetl.sql.core.annotatoin.Table;

import java.io.Serializable;
import java.util.Date;

/**
 * @author chenjiang
 */
@Table(name = "school_guidecourse")
public class Guidecourse implements Serializable {

    private Integer Id;
    private String FullName;
    private String Place;
    private String JobNumber;
    private Date ClassDate;
    private Date SignEndDate;
    private String Description;

    public void setId(Integer Id) {
        this.Id = Id;
    }

    public Integer getId() {
        return Id;
    }

    public void setFullName(String FullName) {
        this.FullName = FullName;
    }

    public String getFullName() {
        return FullName;
    }

    public void setPlace(String Place) {
        this.Place = Place;
    }

    public String getPlace() {
        return Place;
    }

    public String getJobNumber() {
        return JobNumber;
    }

    public void setJobNumber(String jobNumber) {
        JobNumber = jobNumber;
    }

    public void setClassDate(Date ClassDate) {
        this.ClassDate = ClassDate;
    }

    public Date getClassDate() {
        return ClassDate;
    }

    public void setSignEndDate(Date SignEndDate) {
        this.SignEndDate = SignEndDate;
    }

    public Date getSignEndDate() {
        return SignEndDate;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    public String getDescription() {
        return Description;
    }

    @Override
    public String toString() {
        return "Guidecourse{" +
                "Id='" + Id + '\'' +
                "FullName='" + FullName + '\'' +
                "Place='" + Place + '\'' +
                "JobNumber='" + JobNumber + '\'' +
                "ClassDate='" + ClassDate + '\'' +
                "SignEndDate='" + SignEndDate + '\'' +
                "Description='" + Description + '\'' +
                '}';
    }
}
