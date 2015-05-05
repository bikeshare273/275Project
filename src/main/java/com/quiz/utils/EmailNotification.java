package com.quiz.utils;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailNotification {

	static final String host = "smtp.gmail.com";
	static final String username = "bikeshare273";
	static final String password = "sithu@13"; 
	
public void sendEmailonSignUp(String userEmailAddress, String userName)  
	{
		String subject = "Welcome to Quiz Me, your intelligent quiz platform !";
		
			String msgBody = "Hello "+ userEmailAddress +",\n\nYour account has been successfully created." +
				"\n\nThank you for choosing QuizMe !" + "\n\nEnjoy quizzing !!\n\n" + 
				"Team Spartan !!! ";

			emailGenerator(userEmailAddress, userName, subject, msgBody);
					

	}
	
	public void sendEmailOnQuizSharing(String userEmailAddress, String userName, String SharingUser, String quizname) 
	{

		String subject = "Your friend [ " + SharingUser + " ]" + " has shared a quiz with you !";
		String msgBody = "Hello "+ userEmailAddress + ",\n\nA quiz has been shared with you [Quiz Ref :"+ quizname +"] by " + SharingUser +"."+
				"\n\nPlease login to your QuizMe account for attempting the quiz."+ 
				"\n\nThanks for choosing QuizMe !!" + 
				"\n\n" + 
				"Team QuizMe !!! ";

		emailGenerator(userEmailAddress, userName, subject, msgBody);
		
	}
	

	public void emailGenerator(String userEmailAddress, String userName, String subject, String msgBody)

	{
		Properties props = new Properties();
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", host);

		props.put("mail.smtp.port", "587");
		props.put("mail.smtp.auth", "true");
		props.put("mail.debug", "true");


		Session session = Session.getInstance(props,
				new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});
		
		try {
			Message msg = new MimeMessage(session);
			try {
				msg.setFrom(new InternetAddress("bikeshare273@gmail.com", "QuizMe"));
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				msg.addRecipient(Message.RecipientType.TO,
						new InternetAddress(userEmailAddress, "Hello " + userName ));
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			msg.setSubject(subject);			
			msg.setText(msgBody);
			Transport.send(msg);

		} catch (AddressException e) {
			System.out.println(e.getMessage());
		} catch (MessagingException e) {
			System.out.println(e.getMessage());
		}
		
		
		
	}


}