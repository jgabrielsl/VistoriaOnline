package br.com.vistoriaOnline.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;

@Configuration
@EnableAsync
public class EmailServiceImp {

	@Autowired
	private JavaMailSender emailSender;

	@Async
	public void sendSimpleMessage(String to, String subject, String text) {
		try {
			System.out.println(to+" - "+subject+" - "+text);
			SimpleMailMessage message = new SimpleMailMessage();
			message.setFrom("contato@vistoria.online");
			message.setTo(to);
			message.setSubject(subject);
			message.setText(text);
			emailSender.send(message);
			System.out.println("Email enviado!");
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println("Erro ao enviar email!");
		}
	}
}
