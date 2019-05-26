package com.alliance.louisa.louisa2.search.builder;

import java.util.Arrays;
import java.util.List;

import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.InnerHitBuilder;
import org.elasticsearch.index.query.NestedQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import com.alliance.louisa.louisa2.constants.ElasticFields;

import io.searchbox.core.Search;
import io.searchbox.core.Search.Builder;

/**
 * This class is used to construct elastic search query dynamically
 * for given index and DocType
 * @author z022839
 *
 */
public class ElasticSearchBuilder {

	private String index;
	private String docType;
	
	public ElasticSearchBuilder(String index,String docType){
		this.index=index;
		this.docType=docType;
	}
	
	public ElasticSearchBuilder(String index){
		this.index=index;
	}
	
	public Builder getCountrySplit(String plantCode, String modelCode, List<Integer> list,
			Integer week, Integer year) {
		SearchSourceBuilder searchSourceBuilder = constructCountryDtoInnerHits(plantCode, modelCode, list,
				week, year);
		String query = searchSourceBuilder.toString();
		Search.Builder searchBuilder = new Search.Builder(query).addIndex(this.index);
		addDocType(searchBuilder);
		
		return searchBuilder;
	}
	
	private void addDocType(Search.Builder searchBuilder) {
		if(null != docType) {
			searchBuilder.addType(docType);
		}
	}
	
	/**
	 * Construct SearchSourceBuilder based on filter by plant model and inner hits by weak split by 
	 * source false {@code FetchSourceContext(false)}
	 * @param plantCode
	 * @param modelCode
	 * @param list
	 * @param week
	 * @param year
	 * @return
	 */
	public SearchSourceBuilder constructCountryDtoInnerHits(String plantCode, String modelCode,
			List<Integer> list, Integer week, Integer year) {
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
		queryBuilder.must(QueryBuilders.termsQuery("plant" + ElasticFields.KEYWORD.getCode(), plantCode));
		queryBuilder.must(QueryBuilders.termsQuery("model" + ElasticFields.KEYWORD.getCode(), modelCode));

		BoolQueryBuilder yearWeekCountryBoolQueryBuilder = constructBoolQueryBuilderForWeekYearCountry(list,
				week, year);

		NestedQueryBuilder nestedWeekSplitQueryBuilders = QueryBuilders.nestedQuery("weeklySplits",
				yearWeekCountryBoolQueryBuilder, ScoreMode.Avg);
		nestedWeekSplitQueryBuilders.innerHit(new InnerHitBuilder()
				.setFetchSourceContext(new org.elasticsearch.search.fetch.subphase.FetchSourceContext(false)));

		queryBuilder.must(nestedWeekSplitQueryBuilders);

		searchSourceBuilder.query(queryBuilder).fetchSource(false);

		return searchSourceBuilder;
	}
	
	/**
	 * construct query builder for for filter by week and year and nested query with
	 * countries and add inner Hits on countries
	 * @param list
	 * @param week
	 * @param year
	 * @return
	 */
	public BoolQueryBuilder constructBoolQueryBuilderForWeekYearCountry(List<Integer> list, Integer week,
			Integer year) {
		BoolQueryBuilder weekYearBoolQueryBuilder = QueryBuilders.boolQuery();

		weekYearBoolQueryBuilder.must(QueryBuilders.termsQuery("weeklySplits.year", Arrays.asList(year)));
		weekYearBoolQueryBuilder.must(QueryBuilders.termsQuery("weeklySplits.week", Arrays.asList(week)));

		BoolQueryBuilder countryBoolQuery = QueryBuilders.boolQuery();

		countryBoolQuery.must(QueryBuilders
				.termsQuery("weeklySplits.countries.countryCode" + ElasticFields.KEYWORD.getCode(), list));

		NestedQueryBuilder nestedQueryBuilders = QueryBuilders.nestedQuery("weeklySplits.countries", countryBoolQuery,
				ScoreMode.Avg);
		nestedQueryBuilders.innerHit(new InnerHitBuilder()
				.setFetchSourceContext(new org.elasticsearch.search.fetch.subphase.FetchSourceContext(true)));

		weekYearBoolQueryBuilder.must(nestedQueryBuilders);
		return weekYearBoolQueryBuilder;
	}

	public SearchSourceBuilder constructCountryDto(List<String> ids) {
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
		queryBuilder.filter(QueryBuilders.termsQuery("_id", ids));
		searchSourceBuilder.query(queryBuilder);

		return searchSourceBuilder;
	}
	
	public Builder getShortTermCountrySplit(List<String> ids) {
		SearchSourceBuilder searchSourceBuilder = constructCountryDto(ids);
		String query = searchSourceBuilder.toString();
		Search.Builder searchBuilder = new Search.Builder(query).addIndex(this.index);
		addDocType(searchBuilder);
		
		return searchBuilder;
	}
	
}
