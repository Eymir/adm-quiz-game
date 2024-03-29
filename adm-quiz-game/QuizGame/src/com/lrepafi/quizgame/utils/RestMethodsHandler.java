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
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lrepafi.quizgame.entities.HighScoreList;
import com.lrepafi.quizgame.entities.Question;

import android.util.Log;

public class RestMethodsHandler {

	private String basePath="http://dakkon.disca.upv.es:8080/WebServiceTrivial/rest/";

	public RestMethodsHandler(String basePath) {
		this.basePath=basePath;
	}

	/*
	 * This methods receive a response and returns the string
	 */
	private String getResponse(HttpResponse response) {

		if (response == null) return "";

		HttpEntity entity = response.getEntity(); 
		String responseString = "";
		if (entity != null) { 
			InputStream stream = null;
			try {
				stream = entity.getContent();
			} catch (IllegalStateException e) {

				e.printStackTrace();
			} catch (IOException e) {

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

				e1.printStackTrace();
			} 
			try {
				stream.close();
			} catch (IOException e) {

				e.printStackTrace();
			} 
			responseString = sb.toString(); 
		}
		return responseString;
	}

	public void invokeFriendInvitation(String email, String guest) {
		HttpClient client = new DefaultHttpClient();
		HttpPost request = new HttpPost(basePath+"friends"); 
		List<BasicNameValuePair> pairs = new ArrayList<BasicNameValuePair>(); 
		pairs.add(new BasicNameValuePair("email", email));
		pairs.add(new BasicNameValuePair("friend_address", guest));


		try {
			request.setEntity(new UrlEncodedFormEntity(pairs));
		} catch (UnsupportedEncodingException e) {

			Log.d("RESTERROR","Error in encoding entity");
		} 


		try {
			client.execute(request);
			Log.d("RESTOK", "All is ok");
		} catch (ClientProtocolException e) {

			e.printStackTrace();
			Log.d("RESTERROR", "RPC Execution error");
		} catch (IOException e) {

			e.printStackTrace();
			Log.d("RESTERROR", "RPC Execution error");
		} 

	}

	public HighScoreList invokeGetScores(String email) {
		HttpClient client = new DefaultHttpClient();
		List<BasicNameValuePair> pairs = new ArrayList<BasicNameValuePair>(); 
		pairs.add(new BasicNameValuePair("email", email));
		HttpGet request = new HttpGet(basePath+"highscores?" + URLEncodedUtils.format(pairs, "utf-8"));

		HttpResponse response=null;


		try {
			response = client.execute(request);
		} catch (ClientProtocolException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		} 

		String responseString = this.getResponse(response);

		GsonBuilder builder = new GsonBuilder(); 
		Gson gson = builder.create(); 
		JSONObject json=null;
		try {
			json = new JSONObject(responseString);
		} catch (JSONException e) {

			e.printStackTrace();
		} 

		HighScoreList ret = new HighScoreList();
		try {
			ret = gson.fromJson(json.toString(), HighScoreList.class);
		}
		catch (Exception e) {
			;
		}
		return ret;

	}

	/*
	 * This method invokes a new game and receives the nr of question of the game
	 */
	public int invokeNewGame(String email) {
		HttpClient client = new DefaultHttpClient();
		HttpPost request = new HttpPost(basePath+"questions"); 
		List<BasicNameValuePair> pairs = new ArrayList<BasicNameValuePair>(); 
		pairs.add(new BasicNameValuePair("email", email));



		try {
			request.setEntity(new UrlEncodedFormEntity(pairs));
		} catch (UnsupportedEncodingException e) {

			e.printStackTrace();
		} 


		HttpResponse response=null;
		try {
			response = client.execute(request);
		} catch (ClientProtocolException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		} 

		String risp = sanitizeNumberString(this.getResponse(response));
		int ret = 0;

		try {
			ret = Integer.parseInt(risp);
		}
		catch (Exception e) {
			ret = 0;
		}


		return ret;



	}

	private String sanitizeNumberString(String str) {
		StringBuffer stringBuffer = new StringBuffer();
		for(int i=0;i<str.length();i++) {
			if ((str.charAt(i) >= '0') && (str.charAt(i) <= '9')) stringBuffer.append(str.charAt(i));
			else break;
		}

		return stringBuffer.toString();
	}

	public Question invokeGetQuestion(String email, int question) {

		HttpClient client = new DefaultHttpClient();
		List<BasicNameValuePair> pairs = new ArrayList<BasicNameValuePair>(); 
		pairs.add(new BasicNameValuePair("email", email));
		pairs.add(new BasicNameValuePair("question", String.valueOf(question)));
		HttpGet request = new HttpGet(basePath+"questions?" + URLEncodedUtils.format(pairs, "utf-8"));

		HttpResponse response=null;

		try {
			response = client.execute(request);
		} catch (ClientProtocolException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		} 

		String responseString = this.getResponse(response);
		GsonBuilder builder = new GsonBuilder(); 
		Gson gson = builder.create(); 
		JSONObject json=null;
		try {
			json = new JSONObject(responseString);
		} catch (JSONException e) {

			e.printStackTrace();
		} 

		Question q = null;
		try {

			q = gson.fromJson(json.toString(), Question.class);

		}

		catch (Exception e) {
			;
		}

		return q;
	}

	public void invokeEndGame(String email) {
		HttpClient client = new DefaultHttpClient();
		List<BasicNameValuePair> pairs = new ArrayList<BasicNameValuePair>(); 
		pairs.add(new BasicNameValuePair("email", email));
		HttpDelete request = new HttpDelete(basePath+"questions?" + URLEncodedUtils.format(pairs, "utf-8"));

		try {
			client.execute(request);
		} catch (ClientProtocolException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		} 

	}

	public void invokePutScore(String user, String email, int score) {

		HttpClient client = new DefaultHttpClient();
		List<BasicNameValuePair> pairs = new ArrayList<BasicNameValuePair>(); 
		pairs.add(new BasicNameValuePair("email",email));
		pairs.add(new BasicNameValuePair("username", user));
		pairs.add(new BasicNameValuePair("score", String.valueOf(score)));
		HttpPut request = new HttpPut(basePath+"highscores?" + URLEncodedUtils.format(pairs, "utf-8"));


		try {
			client.execute(request);
		} catch (ClientProtocolException e) {

			Log.d("RESTERROR", "RPC Execution error");
		} catch (IOException e) {

			Log.d("RESTERROR", "RPC Execution error");
		} 

	}
}
