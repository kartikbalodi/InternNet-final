package model;

import java.sql.Timestamp;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class FriendsObject {
	private int friendOne;
	private int friendTwo;
	private Timestamp timestamp;
	
	public int getFriendOne() {
		return friendOne;
	}
	public void setFriendOne(int friendOne) {
		this.friendOne = friendOne;
	}
	public int getFriendTwo() {
		return friendTwo;
	}
	public void setFriendTwo(int friendTwo) {
		this.friendTwo = friendTwo;
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
			obj.put("friendOne", friendOne);
			obj.put("friendTwo", friendTwo);
			obj.put("timestamp", timestamp);
	
		} catch (Exception e) {
			e.printStackTrace();
		}
		return obj;
	}

}
