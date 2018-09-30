package com.boot.model;

import org.beetl.sql.core.annotatoin.Table;

import java.io.Serializable;

/**
 * @author chenjiang
 */
@Table(name = "student_resume")
public class Resume implements Serializable {

    private Integer Id;
    private Integer StudentId;
    private String SelfVideoName;
    private String SelfVideoUrl;
    private String FileName;
    private String FileUrl;

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public void setStudentId(Integer StudentId) {
        this.StudentId = StudentId;
    }

    public Integer getStudentId() {
        return StudentId;
    }

    public void setSelfVideoName(String SelfVideoName) {
        this.SelfVideoName = SelfVideoName;
    }

    public String getSelfVideoName() {
        return SelfVideoName;
    }

    public void setSelfVideoUrl(String SelfVideoUrl) {
        this.SelfVideoUrl = SelfVideoUrl;
    }

    public String getSelfVideoUrl() {
        return SelfVideoUrl;
    }

    public void setFileName(String FileName) {
        this.FileName = FileName;
    }

    public String getFileName() {
        return FileName;
    }

    public void setFileUrl(String FileUrl) {
        this.FileUrl = FileUrl;
    }

    public String getFileUrl() {
        return FileUrl;
    }

    @Override
    public String toString() {
        return "Resume{" +
                "Id='" + Id + '\'' +
                "StudentId='" + StudentId + '\'' +
                "SelfVideoName='" + SelfVideoName + '\'' +
                "SelfVideoUrl='" + SelfVideoUrl + '\'' +
                "FileName='" + FileName + '\'' +
                "FileUrl='" + FileUrl + '\'' +
                '}';
    }
}
