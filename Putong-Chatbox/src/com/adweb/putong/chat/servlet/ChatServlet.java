package com.adweb.putong.chat.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import javax.servlet.AsyncContext;
import javax.servlet.AsyncEvent;
import javax.servlet.AsyncListener;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;

import com.adweb.putong.chat.util.JSONParser;
import com.adweb.putong.impl.controllers.json.JsonRecord;
import com.google.gson.Gson;


@WebServlet(urlPatterns = "/chat", asyncSupported = true)
public class ChatServlet extends HttpServlet {

	private static final long serialVersionUID = -277914015930424042L;
	
	private static final String RECORD_ENTER = "record/enter";
	private static final String RECORD_LEAVE = "record/leave";
	private static final String BLOCKSITE_CHECK = "block/site/check";
	private static final String USER_AUTH = "user/auth";
	
	private String server_url;

	//private int status;
	//private String contentType;

	private Map<String, AsyncContext> asyncContexts = new ConcurrentHashMap<String, AsyncContext>();
	private BlockingQueue<String> messages = new LinkedBlockingQueue<String>();
	
	private Thread notifier = new Thread(new Runnable() {
		public void run() {
			while (true) {
				try {
					// Waits until a message arrives
					String message = messages.take();

					// Sends the message to all the AsyncContext's response
					for (AsyncContext asyncContext : asyncContexts.values()) {
						try {
							sendMessage(asyncContext.getResponse().getWriter(), message);
						} catch (Exception e) {
							asyncContexts.values().remove(asyncContext);
						}
					}
				} catch (InterruptedException e) {
					break;
				}
			}
		}
	});
	
	//private Map<String, Integer> members = new ConcurrentHashMap<String, Integer>();
	private Map<String, ConcurrentHashMap<String, Integer>> members = new ConcurrentHashMap<String, ConcurrentHashMap<String, Integer>>();
	private Map<String, Long> visit =  new ConcurrentHashMap<String, Long>();
	private Map<String, String> names =  new ConcurrentHashMap<String, String>();
	
	/**
	 * hbManager is the thread that maintains the users which are online, 
	 */
	private Thread hbManager = new Thread(new Runnable(){
		public void run() {
			while (true) {
				try {
					synchronized(this){// Waits until a message arrives
					
						synchronized(members){
							for(Entry<String, ConcurrentHashMap<String, Integer>> entry : members.entrySet()){
								String url = entry.getKey();
								ConcurrentHashMap<String, Integer> room = entry.getValue();
								for(Entry<String, Integer> member: room.entrySet()){
									System.out.println(entry.getKey()+":"+entry.getValue());
									if(member.getValue() < 0){
										try {
											//String leaveUrl = url;
											room.remove(member.getKey());
											if(room.size() == 0){
												members.remove(url);
											}
											
											notifyQuit(member.getKey(), url);
											
											visit.remove(member.getKey()+"+"+url);
											names.remove(entry.getKey());
											System.out.println(entry.getKey()+" removed");
										} catch (IOException e1) {
											e1.printStackTrace();
										}
									}else{
										room.put(member.getKey(), member.getValue()-1);
									}	
								}					
							}
						}
						this.wait(5 * 1000);
					}					
				} catch (InterruptedException e) {
					break;
				}
			}
		}

		private void notifyQuit(String key, String url) throws IOException{
			
			System.out.println(url+key+"leave");
			recordURLLeft(key, url);
			
			if(key == null) return;
			
			Map<String, String> data = new LinkedHashMap<String, String>();
			data.put("type", "log");
			data.put("username", key);
			data.put("message", "leave");
			data.put("url", url);

			try {
				messages.put(JSONParser.generateJSONString(data));
			} catch (InterruptedException e) {
				throw new IOException(e);
			}
			
			updateChatroom(url);
		}
	});

	private void sendMessage(PrintWriter writer, String message) throws IOException {
		// default message format is message-size ; message-data ;
		writer.print(message.length());
		writer.print(";");
		writer.print(message);
		writer.print(";");
		writer.flush();
	}

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		server_url = this.getServletContext().getInitParameter(
				"PUTONG_SERVICE_URL");
		notifier.start();
		hbManager.start();
	}

	// GET method is used to establish a stream connection
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// Content-Type header
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/plain");
		response.setCharacterEncoding("utf-8");

		// Access-Control-Allow-Origin header
		response.setHeader("Access-Control-Allow-Origin", "*");

		PrintWriter writer = response.getWriter();

		// Id
		final String id = UUID.randomUUID().toString();
		writer.print(id);
		writer.print(';');

		// Padding
		for (int i = 0; i < 1024; i++) {
			writer.print(' ');
		}
		writer.print(';');
		writer.flush();

		final AsyncContext ac = request.startAsync();
		ac.addListener(new AsyncListener() {
			public void onComplete(AsyncEvent event) throws IOException {
				asyncContexts.remove(id);
			}

			public void onTimeout(AsyncEvent event) throws IOException {
				asyncContexts.remove(id);
			}

			public void onError(AsyncEvent event) throws IOException {
				asyncContexts.remove(id);
			}

			public void onStartAsync(AsyncEvent event) throws IOException {

			}
		});
		ac.setTimeout(1 * 1000);
		asyncContexts.put(id, ac);
	}

	// POST method is used to communicate with the server
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");

		AsyncContext ac = asyncContexts.get(request.getParameter("metadata.id"));
		if (ac == null) {
			return;
		}

		// close-request
		if ("close".equals(request.getParameter("metadata.type"))) {
			ac.complete();
			return;
		}

		// send-request
		Map<String, String> data = new LinkedHashMap<String, String>();
		
		//request.setCharacterEncoding("utf-8");
		
		String eventType = request.getParameter("type");
		String userName = request.getParameter("username");
		String password = request.getParameter("password");
		String message = request.getParameter("message");
		String title = request.getParameter("title");
		String url = request.getParameter("url");
		String time = request.getParameter("time");
		
		//System.out.println(title);
		
		if(eventType != null){
			eventType = new String(eventType.getBytes("utf-8"),"utf-8");
		}
		
		if(userName != null){
			userName = new String(userName.getBytes("utf-8"),"utf-8");
		}
		
		if(password != null){
			password = new String(password.getBytes("utf-8"),"utf-8");
		}
		
		if(message != null) {
			message = new String(message.getBytes("utf-8"),"utf-8");
		}
		
		if(title != null){
			title = new String(title.getBytes("utf-8"),"utf-8");
		}
		
		if(url != null){
			url = new String(url.getBytes("utf-8"),"utf-8");
		}
		
		if(time != null) {
			time = new String(time.getBytes("utf-8"),"utf-8");
		}
		
		//System.out.println(title);
		
		//System.out.println(eventType+","+message);
		
		if(eventType.equals("knock")){
			
			System.out.println("knock");
			
			data.put("type", "reply");
			data.put("url", url);
			data.put("title", title);
			data.put("time", new Date().toString());
			
			boolean siteVaild = checkSite(url);
			if(siteVaild){
				data.put("message", "yes");	
			}else{
				data.put("message", "no");
			}
			
			try {
				messages.put(JSONParser.generateJSONString(data));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}else if(eventType.equals("auth")){		
			
			boolean userAuthed = auth(userName, password);
			if(userAuthed){
				data.put("message", "success");
			}else{
				System.out.println("failed");
				data.put("message", "fail");
			}
			
			data.put("type", "auth");
			data.put("url", url);
			data.put("title", title);
			data.put("time", new Date().toString());
			data.put("username", userName);
			
			//System.out.println("auth "+userName+","+password);
			
			try {
				messages.put(JSONParser.generateJSONString(data));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}else{
			if (eventType.equals("log") && userName != null) {
				synchronized(members){
					//System.out.println(userName+"2");
					if(members.get(url) == null) {
						members.put(url, new ConcurrentHashMap<String, Integer>());
						members.get(url).put(userName, 2);
					} else {
						members.get(url).put(userName, 2);
					}
				}
				if(message.equals("enter")){
					
					if(members.get(url) == null || members.get(url).get(userName) == null){
						System.out.println(userName+"enter");
						Long id = recordURLVisited(userName, title, url);
						visit.put(userName+"+"+url, id);
						
						// form a new message			
						data.put("type", eventType);
						data.put("username", userName);
						data.put("message", message);	
						data.put("url", url);
						data.put("title", title);
						data.put("time", time);
						
						try {
							System.out.println(JSONParser.generateJSONString(data));
							messages.put(JSONParser.generateJSONString(data));
						} catch (InterruptedException e) {
							throw new IOException(e);
						}
						
						updateChatroom(url);
					}					
					
				}
			}else if(!eventType.equals("log")){
					// form a new message			
				data.put("type", eventType);
				data.put("username", userName);
				data.put("message", message);	
				data.put("url", url);
				data.put("title", title);
				data.put("time", time);
				data.put("name", names.get(userName));
				
				try {
					//System.out.println(JSONParser.generateJSONString(data));
					messages.put(JSONParser.generateJSONString(data));
				} catch (InterruptedException e) {
					throw new IOException(e);
				}
			}
			
		}	
		
	}
	
	class SimpleResult{
		Integer code;
	}
	
	class RecordResult extends SimpleResult{
		JsonRecord json;
	}

	private Long recordURLVisited(String userName, String title, String url) {
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("username", userName);
		params.put("title", title);
		params.put("url", url);
		
		try {
			String responseJson = getResponseJson(server_url + RECORD_ENTER, params);
			System.out.println(responseJson);
			RecordResult response = new Gson().fromJson(responseJson, RecordResult.class);
			//System.out.println(response.code);
			//System.out.println(response.json);
			names.put(userName, response.json.getUser().getNickname());
			return response.json.getId();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (KeyManagementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		
		return new Long(-1);
	}
	
	
	private boolean recordURLLeft(String userName, String url){
		Long id = visit.get(userName+"+"+url);
		System.out.println(id);
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("username", userName);
		params.put("record", id.toString());
		
		try {
			String responseJson = getResponseJson(server_url + RECORD_LEAVE, params);
			System.out.println(responseJson);
			SimpleResult response = new Gson().fromJson(responseJson, SimpleResult.class);
			return (response.code == 200);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (KeyManagementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}
	
	private void updateChatroom(String url) throws IOException {
		StringBuilder sb = new StringBuilder("[");
		Enumeration<String> nameList = members.get(url).keys();
		int count = 0;
		while(nameList.hasMoreElements()){
			String name = nameList.nextElement();
			String nick = names.get(name);
			System.out.println(name+","+names.get(name));
			if(count != 0) sb.append(",");
			sb.append("{username:'"+name+"', name:'"+nick+"'}");
			count++;
		}
		sb.append("]");
		System.out.println(sb.toString());
		
		Map<String, String> updateData = new LinkedHashMap<String, String>();
		updateData.put("type", "update");
		updateData.put("url", url);
		updateData.put("message", sb.toString());
		
		try {
			messages.put(JSONParser.generateJSONString(updateData));
		} catch (InterruptedException e) {
			throw new IOException(e);
		}
	}
	
	private boolean checkSite(String url) {
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("url", url);
		
		try {
			String responseJson = getResponseJson(server_url + BLOCKSITE_CHECK, params);
			System.out.println(responseJson);
			SimpleResult response = new Gson().fromJson(responseJson, SimpleResult.class);
			return (response.code == 200);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (KeyManagementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		
		return false;
	}
	
	private boolean auth(String username, String password){
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("username", username);
		params.put("password", password);
		
		try {
			String responseJson = getResponseJson(server_url + USER_AUTH, params);
			System.out.println(responseJson);
			SimpleResult response = new Gson().fromJson(responseJson, SimpleResult.class);
			//System.out.println(response.code);
			return (response.code == 200);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (KeyManagementException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}	
		
		return false;
	}

	private String getResponseJson(String url, Map<String,String> params) throws IOException, KeyManagementException, NoSuchAlgorithmException {
		
		
		HttpClient httpClient = HttpsClient.getInstance();
		HttpPost httpPost = new HttpPost(url);
		
		
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		for(Entry<String,String> param: params.entrySet()){
			System.out.println(param.getKey()+":"+param.getValue());
			nameValuePairs.add(new BasicNameValuePair(param.getKey(), param.getValue()));  
		}
		
		httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "utf-8"));

		HttpResponse response = httpClient.execute(httpPost);
		//this.status = response.getStatusLine().getStatusCode();
		HttpEntity responseEntity = response.getEntity();
		//this.contentType = responseEntity.getContentType().getValue();

		System.out.println("URL " + url);

		InputStream inStream = responseEntity.getContent();
		InputStreamReader insReader = new InputStreamReader(inStream);
		BufferedReader bufReader = new BufferedReader(insReader);

		StringBuilder sb = new StringBuilder();
		String line;
		while ((line = bufReader.readLine()) != null)
			sb.append(line + "\n");
		return sb.toString();
	}

	@Override
	public void destroy() {
		messages.clear();
		asyncContexts.clear();
		notifier.interrupt();
		hbManager.interrupt();
	}

}