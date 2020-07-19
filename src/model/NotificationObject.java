package model;

import java.sql.Timestamp;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class NotificationObject {
	private int sendingUserID;
    private int receivingUserID;
    private int type;
    private String imageLink;
	private Timestamp timestamp;
	
	public int getSendingUserID() {
		return sendingUserID;
	}
	public void setSendingUserID(int sendingUserID) {
		this.sendingUserID = sendingUserID;
	}
	public int getReceivingUserID() {
		return receivingUserID;
	}
	public void setReceivingUserID(int receivingUserID) {
		this.receivingUserID = receivingUserID;
    }
    public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
    }
    public String getImageLink() {
		return imageLink;
	}
	public void setImageLink(String imageLink) {
		this.imageLink = imageLink;
	}
	public Timestamp getTimestamp() {
		return timestamp;
	}
	public void setImageLink(Timestamp timestamp) {
		this.timestamp = timestamp;
	}
	
	public JSONObject toJSONObject() {
		JSONObject obj = new JSONObject();
		try {
			obj.put("sendingUserID", sendingUserID);
            obj.put("receivingUserID", receivingUserID);
            obj.put("type", type);
            obj.put("imageLink", imageLink);
			obj.put("timestamp", timestamp);
	
		} catch (Exception e) {
			e.printStackTrace();
		}
		return obj;
	}

}
