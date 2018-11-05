package com.boot.model;

import org.beetl.sql.core.annotatoin.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * @author chenjiang
 */
@Table(name="Corp_Recruit")
public class Recruit implements Serializable{

	  private Integer Id;
	  private Integer TypeId;
	  private Integer CorpId;
	  private Integer PlaceId;
	  private Integer OpenTypeId;
	  private Date UseDate;
	  private String StartHour;
	  private String EndHour;
	  private String RecuitFiles;
	  private String Files;
	  private Integer Status;
	  private Integer ExamineUserId;
	  private Date ExamineDate;
	  private String ExamineRemark;
	  private String IdCardPicPos;
	  private String IdCardPicNeg;

	  public void setId(Integer Id) {
		this.Id = Id;
	  }
	  public Integer getId() {
		return Id;
	  }

	  public Integer getTypeId() {
		return TypeId;
	}

	  public void setTypeId(Integer typeId) {
		TypeId = typeId;
	}

	public void setCorpId(Integer CorpId) {
		this.CorpId = CorpId;
	  }
	  public Integer getCorpId() {
		return CorpId;
	  }
	  public void setPlaceId(Integer PlaceId) {
		this.PlaceId = PlaceId;
	  }
	  public Integer getPlaceId() {
		return PlaceId;
	  }
	  public void setOpenTypeId(Integer OpenTypeId) {
		this.OpenTypeId = OpenTypeId;
	  }
	  public Integer getOpenTypeId() {
		return OpenTypeId;
	  }
	  public void setUseDate(Date UseDate) {
		this.UseDate = UseDate;
	  }
	  public Date getUseDate() {
		return UseDate;
	  }
	  public void setStartHour(String StartHour) {
		this.StartHour = StartHour;
	  }
	  public String getStartHour() {
		return StartHour;
	  }
	  public void setEndHour(String EndHour) {
		this.EndHour = EndHour;
	  }
	  public String getEndHour() {
		return EndHour;
	  }
	  public void setRecuitFiles(String RecuitFiles) {
		this.RecuitFiles = RecuitFiles;
	  }
	  public String getRecuitFiles() {
		return RecuitFiles;
	  }

	  public String getFiles() {
		return Files;
	  }

	  public void setFiles(String files) {
		Files = files;
	  }

	  public void setStatus(Integer Status) {
		this.Status = Status;
	  }
	  public Integer getStatus() {
		return Status;
	  }
	  public void setExamineUserId(Integer ExamineUserId) {
		this.ExamineUserId = ExamineUserId;
	  }
	  public Integer getExamineUserId() {
		return ExamineUserId;
	  }
	  public void setExamineDate(Date ExamineDate) {
		this.ExamineDate = ExamineDate;
	  }
	  public Date getExamineDate() {
		return ExamineDate;
	  }
	  public void setExamineRemark(String ExamineRemark) {
		this.ExamineRemark = ExamineRemark;
	  }
	  public String getExamineRemark() {
		return ExamineRemark;
	  }

	  public String getIdCardPicPos() {
		return IdCardPicPos;
	}

	  public void setIdCardPicPos(String idCardPicPos) {
		IdCardPicPos = idCardPicPos;
	}

	  public String getIdCardPicNeg() {
		return IdCardPicNeg;
	}

	  public void setIdCardPicNeg(String idCardPicNeg) {
		IdCardPicNeg = idCardPicNeg;
	}

	@Override
      public String toString() {
         return "Specia{" +
                "Id='" + Id + '\'' +
                "CorpId='" + CorpId + '\'' +
                "PlaceId='" + PlaceId + '\'' +
                "OpenTypeId='" + OpenTypeId + '\'' +
                "UseDate='" + UseDate + '\'' +
                "StartHour='" + StartHour + '\'' +
                "EndHour='" + EndHour + '\'' +
                "RecuitFiles='" + RecuitFiles + '\'' +
                "Status='" + Status + '\'' +
                "ExamineUserId='" + ExamineUserId + '\'' +
                "ExamineDate='" + ExamineDate + '\'' +
                "ExamineRemark='" + ExamineRemark + '\'' +
                '}';
      }
}
