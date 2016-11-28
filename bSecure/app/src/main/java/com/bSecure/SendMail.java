package com.bSecure;

import android.os.AsyncTask;
import android.util.Log;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

//Class is extending AsyncTask because this class is going to perform a networking operation
public class SendMail extends AsyncTask<Void,Void,Void> {

    //Declaring Variables
    private Session session;

    //Information to send recipientEmailIds
    private String[] recipientEmailIds;
    private String senderEmailId;
    private String subject;
    private String emailBody;
    private String audioRecordFileName;
    private String password;

    //Class Constructor
    public SendMail(String[] recipientEmailIds, String senderEmailId, String emailPassword , String audioRecordFileName){
        //Initializing variables
        this.recipientEmailIds = recipientEmailIds;
        this.senderEmailId = senderEmailId;
        this.password = emailPassword;
        this.subject = Config.EMAIL_SUBJECT;
        this.emailBody = Config.EMAIL_BODY;
        this.audioRecordFileName = audioRecordFileName;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        //Showing progress dialog while sending recipientEmailIds
        Log.d("bSecure","Sending email, Please wait...");
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        //Dismissing the progress dialog
        Log.d("bSecure","Attached audio file: "+audioRecordFileName);
        Log.d("bSecure","Email Sent");
    }

    @Override
    protected Void doInBackground(Void... params) {
        //Creating properties
        Properties props = new Properties();

        //Configuring properties for gmail
        //If you are not using gmail you may need to change the values
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        //Creating a new session
        session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                    //Authenticating the password
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(senderEmailId, password);
                    }
                });

        try {
            //Creating MimeMessage object
            MimeMessage mm = new MimeMessage(session);

            //Setting sender address
            mm.setFrom(new InternetAddress(senderEmailId));
            //Adding receiver
            InternetAddress[] addressTo = new InternetAddress[recipientEmailIds.length];
            for (int i = 0; i < recipientEmailIds.length; i++) {
                addressTo[i] = new InternetAddress(recipientEmailIds[i]);
            }
            mm.addRecipients(Message.RecipientType.TO, addressTo);
            //Adding subject
            mm.setSubject(subject);
            //Adding emailBody
            mm.setText(emailBody);
            //set filename
            mm.setFileName(audioRecordFileName);
            //Sending recipientEmailIds
            Transport.send(mm);

        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
