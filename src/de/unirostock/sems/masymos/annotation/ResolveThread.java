package de.unirostock.sems.masymos.annotation;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.util.regex.Pattern;
import java.util.regex.Matcher;


import de.unirostock.sems.masymos.annotation.AnnotationResolverUtil;





/**
*
* Copyright 2016 Ron Henkel (GPL v3)
* @author ronhenkel
*/
public class ResolveThread extends Thread {
	
	final Logger logger = Logger.getLogger(ResolveThread.class);

	private String uri;
	private long number;
	
	public ResolveThread(String uri, long number) {
		super(uri);
		this.uri = uri;
		this.number = number;
	}

	@Override
	public void run() {
		String res = "";
		
		// type determination
		String patternURI = "http://identifiers.org/(.*)/(.*)";
		String patternURN = "urn:miriam:(pubmed|biomodels.sbo):(.*)";
		
		Pattern URI = Pattern.compile(patternURI);
		Pattern URN = Pattern.compile(patternURN);
		
		Matcher matcherURI = URI.matcher(uri);
		Matcher matcherURN = URN.matcher(uri);
		
		if(matcherURN.find()) { 
			
			if(matcherURN.group(1).equals("pubmed")) {
				res = "https://eutils.ncbi.nlm.nih.gov/entrez/eutils/efetch.fcgi?db=pubmed&id="+matcherURN.group(2)+"&rettype=fasta&retmode=xml";
			}else {
				res = "https://www.ebi.ac.uk/ols/api/ontologies/sbo/terms?short_form="+matcherURN.group(2).replace(':', '_');
			}
		}else if(matcherURI.find()){
			
			if(matcherURI.group(1).equals("go")||matcherURI.group(1).equals("obo.go")){
				res = "https://www.ebi.ac.uk/ols/api/ontologies/go/terms?short_form="+matcherURI.group(2).replace(':', '_');
			}else if(matcherURI.group(1).equals("chebi")) {
				res = "https://www.ebi.ac.uk/ols/api/ontologies/chebi/terms?short_form="+matcherURI.group(2).replace(':', '_');
			}else if(matcherURI.group(1).equals("bto")) {
				res = "https://www.ebi.ac.uk/ols/api/ontologies/bto/terms?short_form="+matcherURI.group(2).replace(':', '_');
			}else if(matcherURI.group(1).equals("uniprot")) {
				res = "https://www.uniprot.org/uniprot/"+matcherURI.group(2)+".xml";
			}else if(matcherURI.group(1).equals("pubmed")) {
				res = "https://eutils.ncbi.nlm.nih.gov/entrez/eutils/efetch.fcgi?db=pubmed&id="+matcherURI.group(2)+"&rettype=fasta&retmode=xml";
			}else if(matcherURI.group(1).equals("interpro")) {
				res = "https://www.ebi.ac.uk/interpro/api/entry/interpro/"+matcherURI.group(2)+"/?format=json";
			}else if(matcherURI.group(1).equals("reactome")) {
				res = "https://reactome.org/ContentService/data/query/"+matcherURI.group(2);
			}else if(matcherURI.group(1).equals("sgd")) {
				res = "https://www.yeastgenome.org/backend/locus/"+matcherURI.group(2);
			}else {
				res = uri;
			}
		}else{
			res = uri;
		}
		
		if (! uri.equals(res)) {
			logger.info("Request #" + number + " Identifier.org URL " + uri + " mapped to " + res);
		}
		
		//TODO this oldURI is a hack until Identifiers.org provides a proper interface
		AnnotationResolverUtil.instance().addToUrlThreadPool(uri, res);
		logger.info("Resolution request #" + number +" finished");

	}

}
