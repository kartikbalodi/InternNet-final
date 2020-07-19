package model;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class ProfileObject {
	private int userID;
	private String firstName;
	private String lastName;
	private String imageLink;
	private String location;
	
	public ProfileObject() {
	}
	
	public ProfileObject(int userID, String firstName, String lastName, String imageLink, String location) {
		this.userID = userID;
		this.firstName = firstName;
		this.lastName = lastName;
		this.imageLink = imageLink;
		this.location = location;
	}
	
	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getImageLink() {
		return imageLink;
	}
	public void setImageLink(String imageLink) {
		this.imageLink = imageLink;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	
	public JSONObject toJSONObject() {
		JSONObject obj = new JSONObject();
		try {
			obj.put("userID", userID);
			obj.put("firstName", firstName);
			obj.put("lastName", lastName);
			obj.put("imageLink", imageLink);
			obj.put("location", location);
	
		} catch (Exception e) {
			e.printStackTrace();
		}
		return obj;
	}

}
