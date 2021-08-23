package com.dinh.help;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.dinh.entity.Account;
import com.dinh.service.AccountService;
import com.dinh.service.AuthorityService;

import net.bytebuddy.utility.RandomString;

@Service
public class RegisterService {
   
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private AccountService accountService;
    @Autowired
    AuthorityService authService;
    List<MimeMessage> list = new ArrayList<>();
     
    public void register(Account account, String siteURL) throws UnsupportedEncodingException, MessagingException{
        String verifyCode = RandomString.make(64);
        account.setVerifycode(verifyCode);
        accountService.save(account);
        authService.save(account);
        sendVerificationEmail(account, siteURL); 
    }
    
    private void sendVerificationEmail(Account account, String siteURL) throws UnsupportedEncodingException, MessagingException{
    	String toAddress = account.getEmail();
    	String fromAddress = "dinhtppc00576@fpt.edu.vn";
    	String senderName = "TPD Shop";
    	String subject = "Kích hoạt tài khoản!";
    	String content = "Chào "+account.getFullname()+",<br>"
                + "Vui lòng click đường link dưới để kích hoạt tài khoản:<br>"
                + "<h3><a href=\"[[URL]]\" target=\"_self\">NHẤP ĐỂ KÍCH HOẠT</a></h3>"
                + "Thank you,<br>"
                + "TPD Shop.";
    	MimeMessage message = mailSender.createMimeMessage();
    	MimeMessageHelper helper = new MimeMessageHelper(message);
    	helper.setFrom(fromAddress, senderName);
    	helper.setTo(toAddress);
    	helper.setSubject(subject);
    	String verifyURL = siteURL + "/verify?code=" + account.getVerifycode();
    	content = content.replace("[[URL]]", verifyURL);
    	helper.setText(content, true);
    	queue(message);
    }
    
    public boolean verify(String verifycode) {
    	Account account = accountService.findByVerifyCode(verifycode);
    	if(account==null || account.isActivated()) {
    		return false;
    	} else {
    		account.setVerifycode(null);
    		account.setActivated(true);
    		accountService.save(account);
    		return true;
    	}
    }
    
    public void sendMail(Account account) throws UnsupportedEncodingException, MessagingException {
    	String toAddress = account.getEmail();
    	String fromAddress = "dinhtppc00576@fpt.edu.vn";
    	String senderName = "TPD Shop";
    	String subject = "Cấp mật khẩu mới!";
    	String content = "Chào "+account.getFullname()+",<br>"
                + "Mật khẩu tài khoản của bạn là : [[password]]<br>"
                + "TPD Shop.";
    	MimeMessage message = mailSender.createMimeMessage();
    	MimeMessageHelper helper = new MimeMessageHelper(message);
    	helper.setFrom(fromAddress, senderName);
    	helper.setTo(toAddress);
    	helper.setSubject(subject);
    	content = content.replace("[[password]]", account.getPassword());
    	helper.setText(content, true);
    	queue(message);
    }
    
    public void queue(MimeMessage message) {
		list.add(message);
	}
    
    @Scheduled(fixedDelay = 5000)
	public void run() {
		while (!list.isEmpty()) {
			MimeMessage message = list.remove(0);
			try {
				mailSender.send(message);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
    
}
