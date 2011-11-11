package com.lrepafi.quizgame.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

public class RestMethods {

	public static void invokeFriendInvitation(String email, String guest) {
		HttpClient client = new DefaultHttpClient();
		HttpPost request = new HttpPost("http://dakkon.disca.upv.es:8080/WebServiceTrivial/rest/friends"); 
		List<BasicNameValuePair> pairs = new ArrayList<BasicNameValuePair>(); 
		pairs.add(new BasicNameValuePair("email", email));
		pairs.add(new BasicNameValuePair("friend_address", guest));
		
		
		try {
			request.setEntity(new UrlEncodedFormEntity(pairs));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		
		try {
			HttpResponse response = client.execute(request);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
	}
	
	public static void invokeGetScores(String email) {
		HttpClient client = new DefaultHttpClient();
		List<BasicNameValuePair> pairs = new ArrayList<BasicNameValuePair>(); 
		pairs.add(new BasicNameValuePair("email", email));
		HttpGet request = new HttpGet("http://dakkon.disca.upv.es:8080/WebServiceTrivial/rest/highscores?" + URLEncodedUtils.format(pairs, "utf-8"));
		
		HttpResponse response=null;
		
		try {
			response = client.execute(request);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		HttpEntity entity = response.getEntity(); 
		if (entity != null) { 
		  InputStream stream = null;
		try {
			stream = entity.getContent();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		  BufferedReader reader = new BufferedReader( 
		                                new InputStreamReader(stream)); 
		  StringBuilder sb = new StringBuilder(); 
		  String line = null; 
		  try {
			while ((line = reader.readLine()) != null) { 
			    sb.append(line + "\n"); 
			  }
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 
		  try {
			stream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		  String responseString = sb.toString(); 
		} 

		
		//TODO implementar json y exception handling(now sucks :))
		//        :(_8^(|)
		
		
	}
	
	public static void invokeNewGame(String email) {
		HttpClient client = new DefaultHttpClient();
		HttpPost request = new HttpPost("http://dakkon.disca.upv.es:8080/WebServiceTrivial/rest/questions"); 
		List<BasicNameValuePair> pairs = new ArrayList<BasicNameValuePair>(); 
		pairs.add(new BasicNameValuePair("email", email));

		
		
		try {
			request.setEntity(new UrlEncodedFormEntity(pairs));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		
		try {
			HttpResponse response = client.execute(request);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		
		//TODO devolverà nr de cuestiones disponibles en texto plano
		//hacer lo mismo que en invokeGetScores por leer el buffer
		//y un Integer.getInt or smth 
	}
	
	public static void invokeGetQuestion(String email, int question) {
		
		HttpClient client = new DefaultHttpClient();
		List<BasicNameValuePair> pairs = new ArrayList<BasicNameValuePair>(); 
		pairs.add(new BasicNameValuePair("email", email));
		pairs.add(new BasicNameValuePair("question", String.valueOf(question)));
		HttpGet request = new HttpGet("http://dakkon.disca.upv.es:8080/WebServiceTrivial/rest/questions?" + URLEncodedUtils.format(pairs, "utf-8"));
		
		HttpResponse response=null;
		
		try {
			response = client.execute(request);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		//TODO implementar json y exception handling(now sucks :))
	}
	
	public static void invokeEndGame(String email) {
		HttpClient client = new DefaultHttpClient();
		List<BasicNameValuePair> pairs = new ArrayList<BasicNameValuePair>(); 
		pairs.add(new BasicNameValuePair("email", email));
		HttpDelete request = new HttpDelete("http://dakkon.disca.upv.es:8080/WebServiceTrivial/rest/questions?" + URLEncodedUtils.format(pairs, "utf-8"));
		
		HttpResponse response=null;
		
		try {
			response = client.execute(request);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
	}
	
	public static void invokePutScore(String user, String email, int score) {
		
		HttpClient client = new DefaultHttpClient();
		List<BasicNameValuePair> pairs = new ArrayList<BasicNameValuePair>(); 
		pairs.add(new BasicNameValuePair("email",email));
		pairs.add(new BasicNameValuePair("username", user));
		pairs.add(new BasicNameValuePair("score", String.valueOf(score)));
		HttpPut request = new HttpPut("http://dakkon.disca.upv.es:8080/WebServiceTrivial/rest/highscores?" + URLEncodedUtils.format(pairs, "utf-8"));
		
		HttpResponse response=null;
		
		try {
			response = client.execute(request);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
	}
}