public class Article {
	private String url;
	private String title;
	private String content;
	private int like;
	private int dislike;
	private int subscribe;
	private int comment;
	private int total;
	private String date;
	private String channel_url;
	private String channel;
	
	public Article setArticle(String url, String title, String content, int like, int dislike, int subscribe, int comment, int total, String date, String channel_url, String channel) {
		this.url = url;
		this.title = title;
		this.content = content;
		this.like = like;
		this.dislike = dislike;
		this.subscribe = subscribe;
		this.comment = comment;
		this.total = total;
		this.date = date;
		this.channel_url = channel_url;
		this.channel = channel;
		return this;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getLike() {
		return like;
	}

	public void setLike(int like) {
		this.like = like;
	}

	public int getDislike() {
		return dislike;
	}

	public void setDislike(int dislike) {
		this.dislike = dislike;
	}

	public int getSubscribe() {
		return subscribe;
	}

	public void setSubscribe(int subscribe) {
		this.subscribe = subscribe;
	}

	public int getComment() {
		return comment;
	}

	public void setComment(int comment) {
		this.comment = comment;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getChannel_url() {
		return channel_url;
	}

	public void setChannel_url(String channel_url) {
		this.channel_url = channel_url;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}
	
	
}