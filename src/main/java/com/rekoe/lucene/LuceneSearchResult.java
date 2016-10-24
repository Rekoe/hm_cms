package com.rekoe.lucene;

public class LuceneSearchResult {

	private long id;
	private String result;
	
	public LuceneSearchResult() {
		super();
	}
	public LuceneSearchResult(long id, String result) {
		super();
		this.id = id;
		this.result = result;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	
}
