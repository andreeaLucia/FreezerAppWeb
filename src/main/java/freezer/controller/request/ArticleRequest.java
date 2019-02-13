package freezer.controller.request;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ArticleRequest {
	@JsonFormat(pattern="dd-MM-YYYY HH:mm:ss")
	private Date data;
	private String article;
	private String token;
	
	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public void setArticle(String article) {
		this.article = article;
	}

	public String getArticle() {
		return article;
	}

	public void setArticol(String article) {
		this.article = article;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
	
	
}
