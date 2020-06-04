package de.unirostock.sems.masymos.analyzer;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.core.KeywordAnalyzer;
import org.apache.lucene.analysis.core.KeywordTokenizerFactory;
import org.apache.lucene.analysis.core.LowerCaseFilterFactory;
import org.apache.lucene.analysis.custom.CustomAnalyzer;
import org.apache.log4j.Logger;

/**
*
* Copyright 2016 Ron Henkel (GPL v3)
* @author ronhenkel
*/
public class AnalyzerHandler{
	
	static final Logger logger = Logger.getLogger(AnalyzerHandler.class);

	private static final Analyzer lowerCaseKeywordAnalyzer =  createLowerCaseKeywordAnalyzer();
	private static final Analyzer modelIndexAnalyzer = new ModelIndexAnalyzer();
	private static final Analyzer annotationIndexAnalyzer = new AnnotationIndexAnalyzer();
	private static final Analyzer constituentIndexAnalyzer = new ConstituentIndexAnalyzer();
	private static final Analyzer personIndexAnalyzer = new PersonIndexAnalyzer();
	private static final Analyzer publicationIndexAnalyzer = new PublicationIndexAnalyzer();
	private static final Analyzer sedmlIndexAnalyzer = new SedmlndexAnalyzer();
	private static final List<Analyzer> availableAnalyzers = Arrays.asList(lowerCaseKeywordAnalyzer, modelIndexAnalyzer, annotationIndexAnalyzer, constituentIndexAnalyzer, personIndexAnalyzer, publicationIndexAnalyzer, sedmlIndexAnalyzer);
		
	public static List<Analyzer> getAvailableanalyzers() {
		return availableAnalyzers;
	}

	private static final Analyzer createLowerCaseKeywordAnalyzer() {	
		Boolean createdCustom = false;
		Analyzer custom = null;
		try {
			custom = CustomAnalyzer.builder()
								.withTokenizer(KeywordTokenizerFactory.class)
								.addTokenFilter(LowerCaseFilterFactory.class)
								.build();
			createdCustom = true;
		} catch (IOException e) {
			logger.error(e.getMessage());
			createdCustom = false;
		} finally {
			if (!createdCustom) custom = new KeywordAnalyzer();
		}		
		return  custom;		
	}

	public static Analyzer getLowercasekeywordanalyzer() {
		return lowerCaseKeywordAnalyzer;
	}

	public static Analyzer getModelindexanalyzer() {
		return modelIndexAnalyzer;
	}

	public static Analyzer getAnnotationindexanalyzer() {
		return annotationIndexAnalyzer;
	}

	public static Analyzer getConstituentindexanalyzer() {
		return constituentIndexAnalyzer;
	}

	public static Analyzer getPersonindexanalyzer() {
		return personIndexAnalyzer;
	}

	public static Analyzer getPublicationindexanalyzer() {
		return publicationIndexAnalyzer;
	}

	public static Analyzer getSedmlindexanalyzer() {
		return sedmlIndexAnalyzer;
	}

	


}
