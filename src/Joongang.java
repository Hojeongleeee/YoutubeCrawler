import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.HttpClientBuilder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
public class Joongang {

	String crawlURL = "";
	Document doc;
	DBManager db = null;
	
	//HTTP��û�� ���� �⤱��������
	private String tempUrl; //������Url
	private HttpGet http;
	private HttpClient httpClient;
	private HttpResponse response;
	private HttpEntity entity;

	private BufferedReader br;
	private StringBuffer sb;

	String result = ""; //doc = Jsoup.parse(result);	
	
	public Joongang(DBManager db) {
		// TODO Auto-generated constructor stub
		this.db = db;
	}

	@SuppressWarnings("null")
	public Article doParse (String crawlURL) {
		Article art;
		
		//������������ URL�� Dom��ü
			this.doc = getDOM(crawlURL);
			System.out.println(crawlURL);
			Element list = doc.body();
			
			//�� ���������� ��� 10��
			int n = 0;
			
			//data
			String url;
			String title;
			String content;
			int like;
			int dislike;
			int subscribe;
			int comment;
			int total;
			String date;
			String channel_url;
			String channel;
			int likeStart=doc.toString().indexOf("�� �ܿ� �����")+9;
			int likeEnd=doc.toString().indexOf("���� �� �������� ������");
			int dislikeStart=doc.toString().lastIndexOf("�� �ܿ� �����")+9;
			int dislikeEnd=doc.toString().lastIndexOf("���� �� �������� �Ⱦ���");
						
			//select css query
			/**
			 * list.select("strong").get(n).select("a").text().toString();
			 * list.select(".byline").select("em").get(1).text().substring(0, 10).replace(".", "");
			 * list.select(".lead").get(n).text().replace("<span class=\"lead\">", "").replaceAll("</span>", "");
			 */
			String str = "";
			
			url = crawlURL;
			title = list.select("h1").get(1).children().get(0).text().toString();
			content = list.select("#eow-description").text().toString();
			like = Integer.parseInt(doc.toString().substring(likeStart,likeEnd).replace(",", ""));
			dislike = Integer.parseInt(doc.toString().substring(dislikeStart,dislikeEnd).replace(",", ""));
			subscribe = 0; 
			//!!!!
			comment = 0;//Integer.parseInt(list.select(".comment-section-header-renderer").text().replace(",", ""));
			total = Integer.parseInt(list.select(".watch-view-count").text().toString().replace("��ȸ�� ","").replace("ȸ","").replace(",", ""));
			date = list.select(".watch-time-text").text().toString().replace(". ","-").replace("�Խ���: ","").replace(".","");
			channel_url = "http://www.youtube.com"+list.select(".yt-user-info").select("a").attr("href").toString();
			channel = list.select("div .yt-user-info").select("a").text();
			art = new Article();
			
			//article��ü�� ���� setting
			art.setUrl(url);
			art.setTitle(title);
			art.setContent(content);
			art.setLike(like);
			art.setDislike(dislike);
			art.setSubscribe(subscribe);
			art.setComment(comment);
			art.setTotal(total);
			art.setDate(date);
			art.setChannel(channel);
			art.setChannel_url(channel_url);
						
		//DB���� �� �α�
		int items=1;
		System.out.println("url: "+art.getUrl());
		System.out.println("title: "+art.getTitle());
		System.out.println("content: "+art.getContent());
		System.out.println("like: "+art.getLike());
		System.out.println("dislike: "+art.getDislike());
		System.out.println("subsc: "+art.getSubscribe());
		System.out.println("comment: "+art.getComment());
		System.out.println("total: "+art.getTotal());
		System.out.println("date: "+art.getDate());
		System.out.println("chann: "+art.getChannel());
		System.out.println("channurl: "+art.getChannel_url());
		
		Launch.count++;
		//DB����
		if (Launch.enableDB){
			db.runSQL(art);
		}
		
		return art;
	}
	
	//http ����
	public Document getDOM(String crawlURL) {
		Document doc = null;
		
		//http��û �� doc�� parse��� ����
		try{
			// Http ��û�ؼ� doc�� �������
			http = new HttpGet(crawlURL); //tempUrl ����
			httpClient = HttpClientBuilder.create().build();
			response = httpClient.execute(http);
			entity = response.getEntity();
			ContentType content = ContentType.getOrDefault(entity);
			Charset charset = content.getCharset();
			charset = content.getCharset();
			br = new BufferedReader(new InputStreamReader(entity.getContent(), "UTF-8"));
			StringBuffer sb = new StringBuffer();
			String line = "";
			while ((line = br.readLine()) != null) {
				sb.append(line + "\n");
			}
			result = sb.toString();
			doc = Jsoup.parse(result); //doc�� tempUrl�� DOM����
		} catch (IOException e) {
			e.printStackTrace();
		}
		return doc;
	}
}
