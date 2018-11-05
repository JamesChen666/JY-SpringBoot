package com.boot.model.survey;

import org.beetl.sql.core.annotatoin.Table;

import java.io.Serializable;
import java.util.Date;

/**
 * 具体的一次调查
 */
@Table(name="sv_survey_answer")
public class SurveyAnswer implements Serializable {

	private static final long serialVersionUID = 8185532376145530080L;
	private Long id;
	//问卷ID
	private Long surveyId;
	//回答者ID
	private Long userId;
	//回答时间
	private Date bgAnDate;
	//回答时间
	private Date endAnDate=new Date();
	//用时
	private Float totalTime;
	//回答者IP
	private String ipAddr;
	//回答者是详细地址
	private String addr;
	//回答者城市 
	private String city;
	//回答者MAC
	private String pcMac;
	//回答的题数
	private Integer quNum;
	
	/** 回复的此条数据统计信息 **/
	
	/** 数据--完成情况    是否全部题都回答  **/
	//是否完成  1完成
	private Integer isComplete;
	//回答的题数
	private Integer completeNum;
	//回答的题项目数 ---- 表示有些题下面会有多重回答
	private Integer completeItemNum;
	
	/** 数据--有效情况   根据设计问卷时指定的必填项 **/
	//是否是有效数据  1有效
	private Integer isEffective;
	
	/** 数据--审核情况  **/
	//审核状态  0未处理 1通过 2不通过
	private Integer handleState=0;
	
	/** 不同来源数据 **/
	//数据来源  0网调  1录入数据 2移动数据 3导入数据
	private Integer dataSource=0;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getSurveyId() {
		return surveyId;
	}

	public void setSurveyId(Long surveyId) {
		this.surveyId = surveyId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Date getBgAnDate() {
		return bgAnDate;
	}
	public void setBgAnDate(Date bgAnDate) {
		this.bgAnDate = bgAnDate;
	}
	public Date getEndAnDate() {
		return endAnDate;
	}
	public void setEndAnDate(Date endAnDate) {
		this.endAnDate = endAnDate;
	}
	public String getIpAddr() {
		return ipAddr;
	}
	public void setIpAddr(String ipAddr) {
		this.ipAddr = ipAddr;
	}
	public Integer getQuNum() {
		return quNum;
	}
	public void setQuNum(Integer quNum) {
		this.quNum = quNum;
	}
	public Float getTotalTime() {
		return totalTime;
	}
	public void setTotalTime(Float totalTime) {
		this.totalTime = totalTime;
	}
	public String getPcMac() {
		return pcMac;
	}
	public void setPcMac(String pcMac) {
		this.pcMac = pcMac;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public Integer getIsComplete() {
		return isComplete;
	}
	public void setIsComplete(Integer isComplete) {
		this.isComplete = isComplete;
	}
	public Integer getCompleteNum() {
		return completeNum;
	}
	public void setCompleteNum(Integer completeNum) {
		this.completeNum = completeNum;
	}
	public Integer getIsEffective() {
		return isEffective;
	}
	public void setIsEffective(Integer isEffective) {
		this.isEffective = isEffective;
	}
	public Integer getHandleState() {
		return handleState;
	}
	public void setHandleState(Integer handleState) {
		this.handleState = handleState;
	}
	public Integer getDataSource() {
		return dataSource;
	}
	public void setDataSource(Integer dataSource) {
		this.dataSource = dataSource;
	}
	public Integer getCompleteItemNum() {
		return completeItemNum;
	}
	public void setCompleteItemNum(Integer completeItemNum) {
		this.completeItemNum = completeItemNum;
	}

	private SurveyDirectory surveyDirectory;
	public SurveyDirectory getSurveyDirectory() {
		return surveyDirectory;
	}
	public void setSurveyDirectory(SurveyDirectory surveyDirectory) {
		this.surveyDirectory = surveyDirectory;
	}
	
	
	
	
}
