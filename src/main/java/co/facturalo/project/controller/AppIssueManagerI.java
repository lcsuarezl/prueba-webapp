package co.facturalo.project.controller;

import java.util.Date;

import co.facturalo.project.core.InterfaceBean;
import co.facturalo.project.model.AppIssue;

public interface AppIssueManagerI extends InterfaceBean<AppIssue, Long> {

	public Date getStartDate();

	public void setStartDate(Date startDate);

	public Date getEndDate();

	public void setEndDate(Date endDate);

}
