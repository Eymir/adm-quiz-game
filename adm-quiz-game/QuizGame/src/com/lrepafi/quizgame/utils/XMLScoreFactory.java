package com.lrepafi.quizgame.utils;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import org.xmlpull.v1.XmlSerializer;
import android.util.Xml;
import com.lrepafi.quizgame.entities.LocalScore;

public class XMLScoreFactory {





	public void save(FileOutputStream fos, ArrayList<LocalScore> scores) {
		XmlSerializer serialiser = Xml.newSerializer();
		StringWriter writer = new StringWriter(); 

		try {


			serialiser.setOutput(writer); 
			serialiser.startDocument(null, null); 
			serialiser.startTag(null, Globals.XML_SCORE_LIST_TAG);

			for(int i=0;i<scores.size();i++) {
				serialiser.startTag(null, Globals.XML_SCORE_TAG);
				serialiser.attribute(null, Globals.XML_SCORE_USERNAME, scores.get(i).getUsername());
				serialiser.attribute(null, Globals.XML_SCORE_SCORE, String.valueOf(scores.get(i).getScore()));
				serialiser.attribute(null, Globals.XML_SCORE_RANKING, String.valueOf(i+1));
				serialiser.endTag(null, Globals.XML_SCORE_TAG);
			}

			serialiser.endTag(null, Globals.XML_SCORE_LIST_TAG);
			serialiser.endDocument(); 

			fos.write(writer.toString().getBytes()); 
			fos.flush(); 
			fos.close(); 


		}
		catch (Exception e) {
			;
		}

	}

	public ArrayList<LocalScore> load(FileInputStream inputStream) {

		ArrayList<LocalScore> scores = new ArrayList<LocalScore>(); 

		XmlPullParser parser = null;
		try {
			parser = XmlPullParserFactory.newInstance().newPullParser();
		} catch (XmlPullParserException e) {

			e.printStackTrace();
		} 
		try {
			parser.setInput(inputStream, null);
		} catch (XmlPullParserException e) {

			e.printStackTrace();
		} 
		int eventType = XmlPullParser.START_DOCUMENT; 

		while (eventType != XmlPullParser.END_DOCUMENT) { 
			if (eventType == XmlPullParser.START_TAG) { 
				String uname = parser.getAttributeValue(null, Globals.XML_SCORE_USERNAME); 
				String score = parser.getAttributeValue(null, Globals.XML_SCORE_SCORE); 

				try {
					LocalScore tmp = new LocalScore(uname, Integer.parseInt(score));
					scores.add(tmp);
				}
				catch (Exception e) {
					;
				}

			} 
			try {
				eventType = parser.next();
			} catch (XmlPullParserException e) {

				e.printStackTrace();
			} catch (IOException e) {

				e.printStackTrace();
			} 
		} 
		try {
			inputStream.close();
		} catch (IOException e) {

			e.printStackTrace();
		} 
		return scores;
	}


}
