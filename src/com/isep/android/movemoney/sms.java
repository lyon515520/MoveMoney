package com.isep.android.movemoney;

import android.app.Fragment;
import android.telephony.SmsManager;


public class sms{


	public void sendSMS(String type, String receiver, String senderNo, String senderName) {
		
		String message = "To " + receiver + ": ";
		String sender = senderName + " (" + senderNo + ")";
		
		if((type=="send_success")||(type=="demande_success")||(type=="invite"))
			message = message+ "You have received a request from: " + sender+", please check your MoveMoney";
		else if((type=="send_fail")||(type=="demande_fail"))
			message = message+ "This is an invitation to MoveMoney from "+ sender +", please sign up with the invitation code: "+senderNo+", and get 50 euros";
		else if(type=="alert_accept")
			message = message+ "Your request to "+sender+" has been accepted, please check in your MoveMoney";
		else if(type=="alert_refuse")
			message = message+ "Your request to "+sender+" has been refused, please check in your MoveMoney";
		else if(type=="alert_cancel")
			message = message+ "The request from "+sender+" has been canceled, please check in your MoveMoney";
		
		else{ 
			message = "MoveMoney: Error";
			receiver = senderNo;
		}
		
		
		//for test, messages are send to one mobile
		receiver = "0667413795";

         SmsManager smsManager = SmsManager.getDefault();
         smsManager.sendTextMessage(receiver, null, message, null, null);

	         
	     
	}

	
}
