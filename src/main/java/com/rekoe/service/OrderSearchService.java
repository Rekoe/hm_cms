package com.rekoe.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.util.Version;
import org.nutz.dao.Dao;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Files;
import org.nutz.lang.Lang;
import org.nutz.log.Log;
import org.nutz.log.Logs;

import com.rekoe.domain.HMCuisine;
import com.rekoe.domain.HMTradingStore;
import com.rekoe.lucene.LuceneSearchResult;

@IocBean(create = "init", depose = "close")
public class OrderSearchService {

	@Inject
	protected Dao dao;

	private static Log log = Logs.get();

	@Inject("java:$conf.get('tradingStore.lucene.dir')")
	private String indexDir;

	protected com.rekoe.lucene.LuceneIndex luceneIndex;

	public void rebuild() throws IOException {
		Sql sql = Sqls.queryString("select * from hm_trading_store");
		sql.setEntity(dao.getEntity(HMTradingStore.class));
		sql.setCallback(Sqls.callback.entities());
		dao.execute(sql);
		luceneIndex.writer.deleteAll();
		List<HMTradingStore> tradingStores = sql.getList(HMTradingStore.class);
		for (HMTradingStore tradingStore : tradingStores) {
			tradingStore = dao.fetchLinks(tradingStore, null);
			if (tradingStore.getRestaurantInfo() == null) {
				continue;
			}
			_add(tradingStore);
		}
		luceneIndex.writer.commit();
	}

	protected void _add(HMTradingStore tradingStore) {
		Document document;
		document = new Document();
		FieldType fieldType = new FieldType();
		fieldType.setIndexed(true);// 索引
		fieldType.setStored(true);// 存储
		fieldType.setStoreTermVectors(true);
		fieldType.setTokenized(true);
		fieldType.setStoreTermVectorPositions(true);// 存储位置
		fieldType.setStoreTermVectorOffsets(true);// 存储偏移量
		Field field = new Field("id", tradingStore.getId() + "", fieldType);
		document.add(field);

		// 加入标题
		fieldType = new FieldType();
		fieldType.setIndexed(true);// 索引
		fieldType.setStored(true);// 存储
		fieldType.setStoreTermVectors(true);
		fieldType.setTokenized(true);
		fieldType.setStoreTermVectorPositions(true);// 存储位置
		fieldType.setStoreTermVectorOffsets(true);// 存储偏移量
		field = new Field("name", tradingStore.getName(), fieldType);
		document.add(field);

		// 加入文章内容
		fieldType = new FieldType();
		fieldType.setIndexed(true);// 索引
		fieldType.setStored(false);// 存储
		fieldType.setStoreTermVectors(true);
		fieldType.setTokenized(true);
		fieldType.setStoreTermVectorPositions(true);// 存储位置
		fieldType.setStoreTermVectorOffsets(true);// 存储偏移量
		field = new Field("restaurant", tradingStore.getRestaurantInfo().getName(), fieldType);
		document.add(field);

		StringBuilder sb = new StringBuilder();
		if (!Lang.isEmpty(tradingStore.getCuisines())) {
			for (HMCuisine cuisine : tradingStore.getCuisines()) {
				sb.append(cuisine.getName());
			}
		}

		fieldType = new FieldType();
		fieldType.setIndexed(true);// 索引
		fieldType.setStored(false);// 存储
		fieldType.setStoreTermVectors(true);
		fieldType.setTokenized(true);
		fieldType.setStoreTermVectorPositions(true);// 存储位置
		fieldType.setStoreTermVectorOffsets(true);// 存储偏移量

		field = new Field("cuisine", sb.toString(), fieldType);
		document.add(field);

		try {
			luceneIndex.writer.addDocument(document);
		} catch (IOException e) {
			log.debug("add to index fail : id = " + tradingStore.getId());
		} catch (Error e) {
			log.debug("add to index fail : id = " + tradingStore.getId());
		}
	}

	Map<String, Float> boosts = new HashMap<>();
	{
		boosts.put("cuisine", 5.0f);
		boosts.put("restaurant", 3.0f);
		boosts.put("name", 2.0f);
	}
	String[] fields = boosts.keySet().toArray(new String[boosts.size()]);

	public List<com.rekoe.lucene.LuceneSearchResult> search(String keyword, int size) throws IOException, ParseException {
		IndexReader reader = luceneIndex.reader();
		try {
			IndexSearcher searcher = new IndexSearcher(reader);
			Analyzer analyzer = luceneIndex.analyzer();
			MultiFieldQueryParser parser = new MultiFieldQueryParser(Version.LUCENE_4_9, fields, analyzer, boosts);
			// 将关键字包装成Query对象
			Query query = parser.parse(keyword);
			TopDocs results = searcher.search(query, size);
			List<LuceneSearchResult> searchResults = new ArrayList<LuceneSearchResult>();
			for (ScoreDoc sd : results.scoreDocs) {
				String id = searcher.doc(sd.doc).get("id");
				searchResults.add(new LuceneSearchResult(id, searcher.doc(sd.doc).get("name")));
			}
			return searchResults;
		} finally {
			reader.close();
		}
	}

	public void init() throws IOException {
		Files.createDirIfNoExists(indexDir);
		luceneIndex = new com.rekoe.lucene.LuceneIndex(indexDir, OpenMode.CREATE_OR_APPEND);
	}

	public void close() throws IOException {
		if (luceneIndex != null)
			luceneIndex.close();
	}
}
