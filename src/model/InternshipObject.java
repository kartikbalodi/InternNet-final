package model;

import java.util.Date;
import java.util.GregorianCalendar;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class InternshipObject {
	private String company;
	private String position;
	private String location;
	private String appStatus;
	private Date appDeadline;
	private Date dateApplied;

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getAppStatus() {
		return appStatus;
	}

	public void setAppStatus(String appStatus) {
		this.appStatus = appStatus;
    }
    public Date getAppDeadline() {
        return appDeadline;
    }
    public void setAppDeadline(Date date){
        this.appDeadline = date;
    }
    public Date getDateApplied() {
        return dateApplied;
    }
    public void setDateApplied(Date date){
        this.dateApplied = date;
    }

	public JSONObject toJSONObject() {
		JSONObject obj = new JSONObject();
		try {
			obj.put("company", company);
			obj.put("position", position);
			obj.put("location", location);
			obj.put("appStatus", appStatus);
			obj.put("appDeadline", appDeadline);
			obj.put("dateApplied", dateApplied);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return obj;
	}

}
