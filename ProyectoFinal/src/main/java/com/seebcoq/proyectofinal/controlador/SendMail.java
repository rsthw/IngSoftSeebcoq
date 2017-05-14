package com.seebcoq.proyectofinal.controlador;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class SendMail {

  final static String USERNAME = "seebCoq@gmail.com";
  final static String PASSWORD = "CambienEsto";

  public void envioCorreo(String asunto,String mensaje,String destino){
    Properties props = new Properties();
    props.put("mail.smtp.starttls.enable", "true");
    props.put("mail.smtp.auth", "true");
    props.put("mail.smtp.host", "smtp.gmail.com");
    props.put("mail.smtp.port", "587");

    Session session;
      session = Session.getInstance(props,
              new javax.mail.Authenticator() {
                  @Override
                  protected PasswordAuthentication getPasswordAuthentication() {
                      return new PasswordAuthentication(USERNAME, PASSWORD);
                  }
              });

    try {

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(USERNAME));
        message.setRecipients(Message.RecipientType.TO,
            InternetAddress.parse(destino));
        message.setSubject(asunto);
        message.setText(mensaje);

        Transport.send(message);

        System.out.println("Done");

    } catch (MessagingException e) {
        throw new RuntimeException(e);
    }
  }

}
