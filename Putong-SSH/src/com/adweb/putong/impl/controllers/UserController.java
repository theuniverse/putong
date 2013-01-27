package com.adweb.putong.impl.controllers;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.tanesha.recaptcha.ReCaptchaImpl;
import net.tanesha.recaptcha.ReCaptchaResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.adweb.putong.core.beans.IBlockUser;
import com.adweb.putong.core.beans.IUser;
import com.adweb.putong.core.services.IBlockUserService;
import com.adweb.putong.impl.controllers.json.JsonError403;
import com.adweb.putong.util.UtilityTools;

@Controller
public class UserController extends BaseController {

	private static final String NO_SUCH_USER = "No such user";
	
	@Autowired
	IBlockUserService blockUserService;

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String register(@RequestParam("username") String username,
			@RequestParam("password") String password,
			@RequestParam("email") String email, Model model,
			HttpServletRequest request, HttpServletResponse response) {
		// validate json request
		// System.out.println(username + "," + password + "," + email);

		// check required parameters
		if (username == null || password == null || email == null
				|| username.equals("") || password.equals("")
				|| email.equals("")) {
			try {
				String url = String.format("!error-%d?username=%s&email=%s",
						603, username, email);
				response.sendRedirect(url);
				return "error";
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		boolean isCaptchaValid = validateRecaptcha(request);
		// boolean isCaptchaValid = true;
		if (!isCaptchaValid) { // reCaptcha invalid
			try {
				String url = String.format("!error-%d?username=%s&email=%s",
						601, username, email);
				response.sendRedirect(url);
				return "error";
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		// call services
		String hashedPassword = UtilityTools.createHashPassword(password);
		System.out.println(hashedPassword);
		boolean isSucceed = accountService.register(username, hashedPassword, email);
		if (!isSucceed) { // username or email registered
			try {
				String url = String.format("!error-%d?username=%s&email=%s",
						602, username, email);
				response.sendRedirect(url);
				return "error";
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		sendVerMail(username, hashedPassword, email);

		try {
			String url = String.format("register/success");
			response.sendRedirect(url);
			return "rsuccess";
		} catch (IOException e) {
			e.printStackTrace();
		}

		return "rsuccess";

	}

	@RequestMapping(value = "/register/success", method = RequestMethod.GET)
	public String registerSuccess(Model model, HttpServletRequest request,
			HttpServletResponse response) {
		
		model.addAttribute("type", "注册成功");
		return "rsuccess";
	}
	
	@RequestMapping(value = "/register/reactive", method = RequestMethod.POST)
	public String reactive(@RequestParam("username") String username, Model model, HttpServletRequest request,
			HttpServletResponse response) {
		
		IUser user = accountService.userinfo(username, username);
		if(user == null) {
			try {
				String url = String.format("!error-%d?username=%s", 403,
						username);
				response.sendRedirect(url);
				return "error";
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		sendVerMail(user.getUsername(), user.getPassword(), user.getEmail());
		
		model.addAttribute("type", "激活邮件已发送");
		return "rsuccess";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(@RequestParam("username") String username,
			@RequestParam("password") String password, Model model,
			HttpServletRequest request, HttpServletResponse response) {

		if (username.equals("") || password.equals("")) {
			try {
				String url = String.format("!error-%d?username=%s", 701,
						username);
				response.sendRedirect(url);
				return "error";
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		// validate and authenticate
		//System.out.println("[BEFORE AUTH]:" + System.nanoTime());
		boolean result = accountService.login(username, password);
		if (!result) {
			try {
				IUser user = accountService.userinfo(username, username);
				if(user != null) {
					HttpSession session = request.getSession();
					ArrayList<String> ips = null;
					String ip = request.getRemoteHost();
					Date date = new Date();
					if(session.getAttribute("errorIP") == null) {
						ips = new ArrayList<String>();
					} else {
						ips = (ArrayList<String>) session.getAttribute("errorIP");
					}
					
					System.out.println(ip+","+date.getTime());
					ips.add(ip+","+date.getTime());
					
					if(ips.size() == 3) {
						sendNotifyEmail(ips, user.getEmail(), username);
						session.removeAttribute("errorIP");
					}else{
						session.setAttribute("errorIP", ips);
					}				
				}				
				
				String url = String.format("!error-%d?username=%s", 403,
						username);
				response.sendRedirect(url);				
				return "error";
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		boolean activated = accountService.isActivated(username);
		//boolean activated = true;
		if (!activated) {
			//System.out.println("[ACTIVATED FAILED]:" + System.nanoTime());
			try {
				String url = String.format("error/702?username=%s", username);
				response.sendRedirect(url);
				return "error";
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		boolean living = blockUserService.checkUser(username);
		if (!living) {
			//System.out.println(System.nanoTime());
			try {
				String url = String.format("error/703?username=%s", username);
				response.sendRedirect(url);
				return "error";
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		IUser self = accountService.userinfo(username, username);
		HttpSession session = request.getSession();
		session.setAttribute("self", self);
		session.setAttribute("host", request.getRemoteHost());

		String url = String.format("home");
		try {
			response.sendRedirect(url);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "success";
	}
	
	@RequestMapping(value = "/login/{id}/pause/{code}", method = RequestMethod.GET)
	public String pause(@PathVariable String id,
			@PathVariable String code, Model model) {
		System.out.println(id + "," + code);
		
		String realCode = UtilityTools.generateSHA1(id);
		if(realCode.equals(code)){
			System.out.println("true");
			IBlockUser blockUser = blockUserService.put(id, "帐户危险");
			if (blockUser != null) {
				return "psuccess";
			} else {
				model.addAttribute("username", id);
				return "pfailed";
			}
		}
		
		model.addAttribute("username", id);
		return "pfailed";
		
	}
	
	@RequestMapping(value = "/login/{id}/reset/{code}", method = RequestMethod.GET)
	public String reset(@PathVariable String id,
			@PathVariable String code, Model model) {
		System.out.println(id + "," + code);
		
		String realCode = UtilityTools.generateSHA1(id);
		if(realCode.equals(code)){
			String tempPassword = UtilityTools.generateTempPassword(id);
			String hashedPassword = UtilityTools.createHashPassword(tempPassword);
			System.out.println(hashedPassword);
			boolean isSucceed = accountService.update(id, hashedPassword, null, null, null, null, null, null);
			if (isSucceed) {
				IUser user = accountService.userinfo(id, id);
				sendResetEmail(id, user.getEmail(), tempPassword);
				return "resuccess";
			} else {
				model.addAttribute("username", id);
				return "refailed";
			}
		}
		
		model.addAttribute("username", id);
		return "pfailed";
		
	}

	@RequestMapping(value = "/logout", method = RequestMethod.POST)
	public String logout(HttpServletRequest request,
			HttpServletResponse response) {

		request.getSession().removeAttribute("self");

		try {
			String url = String.format("");
			response.sendRedirect(url);
			return "error";
		} catch (IOException e) {
			e.printStackTrace();
		}

		return "error";
	}

	@RequestMapping(value = "/user/auth", method = RequestMethod.POST)
	public String auth(@RequestParam("username") String username,
			@RequestParam("password") String password, Model model) {

		// validate and authenticate
		boolean result = accountService.login(username, password);
		if (!result) {
			model.addAttribute(ERROR_JSON, new JsonError403(NO_SUCH_USER));
			return ERROR_PAGE;
		}

		boolean activated = accountService.isActivated(username);
		//boolean activated = true;
		if (!activated) {
			model.addAttribute(ERROR_JSON, new JsonError403(NO_SUCH_USER));
			return ERROR_PAGE;
		}

		boolean living = blockUserService.checkUser(username);
		if (!living) {
			model.addAttribute(ERROR_JSON, new JsonError403(NO_SUCH_USER));
			return ERROR_PAGE;
		}

		return SUCCEED_PAGE;
	}

	@RequestMapping(value = "/!error-{id}", method = RequestMethod.GET)
	public String gateway(@PathVariable Integer id, Model model) {
		return "index";
	}

	@RequestMapping(value = "/register/{id}/activate/{code}", method = RequestMethod.GET)
	public String activate(@PathVariable String id,
			@PathVariable String code, Model model) {
		System.out.println(id + "," + code);

		boolean result = accountService.activate(id, code);
		if (result) {
			return "asuccess";
		} else {
			model.addAttribute("username", id);
			return "afailed";
		}
	}

	private void sendVerMail(String username, String password, String email) {

		final String mail_username = "199354400";
		final String mail_password = "theuniverseblanc";

		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.qq.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class",
				"javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");

		Session session = Session.getInstance(props,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(mail_username,
								mail_password);
					}
				});

		StringBuilder sb = new StringBuilder();
		sb.append("<a href='http://localhost:8080/new-putong/register/"
				+ username + "/activate/"+ UtilityTools.generateMD5(username + password) +"' target='_blank'>");
		sb.append("点击完成激活</a>");
		/*sb.append("<form action='http://localhost:8080/new-putong/register/"
				+ username + "/activate' method='post' autocomplete='off' >");
		sb.append("<input type='hidden' name='code' value='"
				+ UtilityTools.generateMD5(username + password) + "' />");
		sb.append("<input type='submit' value='点击完成激活' />");
		sb.append("</form>");*/

		try {
			MimeMessage msg = new MimeMessage(session);
			msg.setHeader("Content-Type", "text/html; charset=\"utf-8\"");
			msg.setFrom(new InternetAddress("199354400@qq.com", MimeUtility
					.encodeText("噗通网", "utf-8", null)));
			// msg.set
			msg.setRecipients(Message.RecipientType.TO, email);
			msg.setSubject("[ 噗通注册验证 ] 请完成你的注册");
			msg.setSentDate(new Date());
			msg.setContent(sb.toString(), "text/html; charset=\"utf-8\"");
			Transport.send(msg);
		} catch (MessagingException mex) {
			mex.printStackTrace();
			System.out.println("send failed, exception: " + mex);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	
	private void sendNotifyEmail(ArrayList<String> ips, String email, String username) {
		final String mail_username = "199354400";
		final String mail_password = "theuniverseblanc";

		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.qq.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class",
				"javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");

		Session session = Session.getInstance(props,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(mail_username,
								mail_password);
					}
				});

		StringBuilder sb = new StringBuilder();
		Calendar c = Calendar.getInstance();
		sb.append("<span>亲爱的用户：\n我们注意到你的帐号有连续三次未正确输入密码。下面是三次登录的基本信息：</span>");
		sb.append("<table style='border: 1px solid #cccccc'>");
		sb.append("<tr><td>登录ip</td><td>登录时间</td></tr>");
		for(String log: ips){
			String[] logPart = log.split(",");			
			c.setTimeInMillis(Long.parseLong(logPart[1]));
			sb.append("<tr><td>"+ logPart[0] +"</td><td>"+ parseTime(c.getTime()) +"</td></tr>");
		}
		sb.append("</table>");
		sb.append("<span>若您发现这几次非您本人，请暂时选择封禁帐号：</span>");
		sb.append("<a href='http://localhost:8080/new-putong/login/"
				+ username + "/pause/"+ UtilityTools.generateSHA1(username) +"' target='_blank'>");
		sb.append("点击此处暂时封禁帐号</a><br />");
		sb.append("<span>若的确是因为忘记了密码，请选择生成新的临时密码：</span>");
		sb.append("<a href='http://localhost:8080/new-putong/login/"
				+ username + "/reset/"+ UtilityTools.generateSHA1(username) +"' target='_blank'>");
		sb.append("点击生成临时密码</a>");
		/*sb.append("<form action='http://localhost:8080/new-putong/register/"
				+ username + "/activate' method='post' autocomplete='off' >");
		sb.append("<input type='hidden' name='code' value='"
				+ UtilityTools.generateMD5(username + password) + "' />");
		sb.append("<input type='submit' value='点击完成激活' />");
		sb.append("</form>");*/

		try {
			MimeMessage msg = new MimeMessage(session);
			msg.setHeader("Content-Type", "text/html; charset=\"utf-8\"");
			msg.setFrom(new InternetAddress("199354400@qq.com", MimeUtility
					.encodeText("噗通网", "utf-8", null)));
			// msg.set
			msg.setRecipients(Message.RecipientType.TO, email);
			msg.setSubject("[ 噗通安全提示 ] 你的账号存在登陆异常，请及时处理");
			msg.setSentDate(new Date());
			msg.setContent(sb.toString(), "text/html; charset=\"utf-8\"");
			Transport.send(msg);
		} catch (MessagingException mex) {
			mex.printStackTrace();
			System.out.println("send failed, exception: " + mex);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("deprecation")
	private String parseTime(Date time) {
		if(time == null) return "";
		return (time.getYear()+1900)+"-"+time.getMonth()+"-"+time.getDate()+" "+time.getHours()+":"+time.getMinutes()+":"+time.getSeconds();
	}

	private void sendResetEmail(String username, String email, String tempPassword) {
		final String mail_username = "199354400";
		final String mail_password = "theuniverseblanc";

		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.qq.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class",
				"javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");

		Session session = Session.getInstance(props,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(mail_username,
								mail_password);
					}
				});

		StringBuilder sb = new StringBuilder();
		sb.append("你的临时密码为： "+tempPassword);

		try {
			MimeMessage msg = new MimeMessage(session);
			msg.setHeader("Content-Type", "text/html; charset=\"utf-8\"");
			msg.setFrom(new InternetAddress("199354400@qq.com", MimeUtility
					.encodeText("噗通网", "utf-8", null)));
			// msg.set
			msg.setRecipients(Message.RecipientType.TO, email);
			msg.setSubject("[ 噗通注册验证 ] 已生成你的临时密码");
			msg.setSentDate(new Date());
			msg.setContent(sb.toString(), "text/html; charset=\"utf-8\"");
			Transport.send(msg);
		} catch (MessagingException mex) {
			mex.printStackTrace();
			System.out.println("send failed, exception: " + mex);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	private boolean validateRecaptcha(HttpServletRequest request) {
		String remoteAddr = request.getRemoteAddr();
		ReCaptchaImpl reCaptcha = new ReCaptchaImpl();
		reCaptcha.setPrivateKey("6LefftASAAAAALjCJtYfC0prKGnxPSztALjD3zXf");

		String challenge = request.getParameter("recaptcha_challenge_field");
		String uresponse = request.getParameter("recaptcha_response_field");

		System.out.println(challenge + "," + uresponse);
		ReCaptchaResponse reCaptchaResponse = reCaptcha.checkAnswer(remoteAddr,
				challenge, uresponse);

		return reCaptchaResponse.isValid();
	}

}
