package com.weaver.inte.business.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.weaver.inte.activity.model.WorkflowReq;

@Table(name = "leave_apply")
public class LeaveApply extends WorkflowReq{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "leave_type")
	private Integer leaveType;
	@Column(name = "start_date")
	private String startDate;
	@Column(name = "end_date")
	private String endDate;
	@Column(name = "remark")
	private String remark;
	@Column(name = "status")
	private Integer status;
	@Column(name = "formrecid")
	private Long formrecid;
	@Column(name = "create_id")
	private Long createId;
	@Column(name = "modify_id")
	private Long modifyId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getLeaveType() {
		return leaveType;
	}

	public void setLeaveType(Integer leaveType) {
		this.leaveType = leaveType;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Long getFormrecid() {
		return formrecid;
	}

	public void setFormrecid(Long formrecid) {
		this.formrecid = formrecid;
	}

	public Long getCreateId() {
		return createId;
	}

	public void setCreateId(Long createId) {
		this.createId = createId;
	}

	public Long getModifyId() {
		return modifyId;
	}

	public void setModifyId(Long modifyId) {
		this.modifyId = modifyId;
	}

}
