package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import database.Database;
import util.Util;

/**
 * Servlet implementation class TransferID
 */
@WebServlet("/TransferID")
public class TransferID extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TransferID() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		int resultCode = Database.connect();
		JSONObject responseObj = new JSONObject(); // json response obj
		if(resultCode == Database.SERVER_ERROR) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			responseObj.put("error", "Internal server error.");
			return;
		}
		
		JSONObject input = Util.readJsonObject(request);
		String username = (String) input.get("username");
		
		int userID = Database.GetUserID(username);
		
		if(userID == -1) {
			responseObj.put("status", "SUCCESS");
			responseObj.put("userID", userID);
		}
		else {
			responseObj.put("status", "FAIL");
		}
		
	}

}
