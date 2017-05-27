package com.ssm.act.api.model;

import com.ssm.common.model.AbstractModel;

import java.io.Serializable;
import java.util.Date;

public class Leave extends AbstractModel implements Serializable {
    private Long id;

    private Integer days;

    private String content;

    private Date applyTime;

    private String remark;

    private String applicant;

    private Integer status;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getApplicant() {
        return applicant;
    }

    public void setApplicant(String applicant) {
        this.applicant = applicant;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", days=").append(days);
        sb.append(", content=").append(content);
        sb.append(", applyTime=").append(applyTime);
        sb.append(", remark=").append(remark);
        sb.append(", applicant=").append(applicant);
        sb.append(", status=").append(status);
        sb.append("]");
        return sb.toString();
    }
}