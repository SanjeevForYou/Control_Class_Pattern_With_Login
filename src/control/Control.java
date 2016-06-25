package control;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import ui.*;
import java.util.*;

import data.Data;
import data.Logins;

public enum Control {
	INSTANCE;
	Start start;
	Stage primaryStage;
	Login login;
	String username;
	Grades grades;
	Remarks remarks;
	private boolean isLoggedIn = false;
	public boolean isLoggedIn() {
		return isLoggedIn;
	}
	public void setLoggedIn(boolean b) {
		isLoggedIn = b;
	}
	
	public void setStart(Start st) {
		this.start = st;
	}
	
	public void setStage(Stage ps) {
		primaryStage=ps;
	}
	public void backToStart(Stage stage) {
		stage.hide();
		start.setMessage("");
		primaryStage.show();
	}
	
	private class LogoutHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent evt) {
			username = null;
			isLoggedIn = false;
			start.setMessage("Logout successful");
		}
	}
	public LogoutHandler getLogoutHandler() {
		return new LogoutHandler();
	}
	
	private class RequestLoginHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent evt) {
			login = new Login();
			primaryStage.hide();
			login.show();
		}
	}
	public RequestLoginHandler getRequestLoginHandler() {
		return new RequestLoginHandler();
	}
	
	private class RequestGradesHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent evt) {
			grades = new Grades();
			String username = "Joe";
			if(Control.this.isLoggedIn)
			{
				username = Control.this.username;
			}
			HashMap<String, String> gr 
				= Data.dataMap.get(username).getGrades();
			StringBuilder sb = new StringBuilder();
			for(String key : gr.keySet()) {
				sb.append(key + " : " + gr.get(key) + "\n");
			}
			grades.setGrades(sb.toString());
			grades.setTitle("Grades for " + username);
			grades.setHeading("Grades for " + username);
			grades.show();
		}
	}
	public RequestGradesHandler getRequestGradesHandler() {
		return new RequestGradesHandler();
	}
	
	private class RequestRemarksHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent evt) {
			remarks = new Remarks();
			String username = "Joe";
			if(Control.this.isLoggedIn)
			{
				username = Control.this.username;
			}
			HashMap<String, String> rem 
				= Data.dataMap.get(username).getTeacherRemarks();
			StringBuilder sb = new StringBuilder();
			for(String key : rem.keySet()) {
				sb.append(key + " : " + rem.get(key) + "\n");
			}
			remarks.setRemarks(sb.toString());
			remarks.setTitle("Teacher Remarks for " + username);
			remarks.setHeading("Teacher Remarks for " + username);
			remarks.show();
		}
	}
	public RequestRemarksHandler getRequestRemarksHandler() {
		return new RequestRemarksHandler();
	}
	
	private class SubmitLoginHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent evt) {
			String username = login.getUserName();
			String password = login.getPassword();
			if(!Logins.foundUserNamePwd(username, password)) {
				login.setMessage("Login failed.");
			} else {
				Control.this.username = username;
				Control.this.isLoggedIn = true;
				login.setMessage("Successful login");
			}
		}
	}
	public SubmitLoginHandler getSubmitLoginHandler() {
		return new SubmitLoginHandler();
	}
	
	
}
