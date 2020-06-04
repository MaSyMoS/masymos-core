package de.unirostock.sems.masymos.configuration;

/**
*
* Copyright 2016 Ron Henkel (GPL v3)
* @author ronhenkel
*/
public class Property {
	
	public class ModelType{
		/*
		 * Model Type Properties
		 */
		public static final  String SBML = "SBML";
		public static final  String CELLML = "CELLML";
		public static final  String XML = "XML";
		public static final  String SEDML = "SEDML";
		public static final  String BIOPAX = "BIOPAX";
		
	
	}
	
	public class General{
		/*
		 * General Properties
		 */
		public static final  String ID = "ID";
		public static final  String NAME = "NAME";
		public static final  String CREATED = "CREATED";
		public static final  String MODIFIED = "MODIFIED";
		public static final  String CREATOR = "CREATOR";
		public static final  String ENCODER = "ENCODER";
		public static final  String SUBMITTER = "SUBMITTER";
		public static final  String AUTHOR = "AUTHOR";
		public static final  String EMAIL = "EMAIL";
		public static final  String URI = "URI";
		public static final  String FILENAME = "FILENAME";
		public static final  String FILEID = "FILEID";
		public static final  String META = "META";
		public static final  String XMLDOC = "XMLDOC";
		public static final  String VERSIONID = "VERSIONID";
		public static final  String RESOURCETEXT = "RESOURCETEXT";
		public static final  String NONRDF = "NONRDF";
		public static final  String IS_INDEXED = "ISINDEXED";
		public static final  String UID = "UID";
		
	
	}
	
	public class XML{
	/*
	 * Properties to store XML in DB
	 */
		public static final  String VALUE = "VALUE";
		public static final  String ELEMENT = "ELEMENT";
		public static final  String ATTRIBUTE_NAMES = "ATTRIBUTENAMES";
		public static final  String ATTRIBUTE_VALUES = "ATTRIBUTEVALUES";
	}
	
	public class SBML{
	/*
	 * Properties to store SBML in DB
	 */	
		public static final  String VERSION = "VERSION";
		public static final  String LEVEL = "LEVEL";
		//public static final  String NAME = "NAME";
		public static final  String COMPARTMENT = "COMPARTMENT";
		public static final  String REACTION = "REACTION";
		public static final  String SPECIES = "SPECIES";
		public static final  String PARAMETER = "PARAMETER";
		public static final  String RULE = "RULE";
		public static final  String FUNCTION = "FUNCTION";
		public static final  String EVENT = "EVENT";
	}
	
	public class CellML{
	/*
	 * Properties to store CellML in DB
	 */	
		public static final  String VERSION = "VERSION";
		//public static final  String NAME = "NAME";
		public static final  String COMPONENT = "COMPONENT";
		//public static final  String GROUP = "GROUP";
		public static final  String VARIABLE = "VARIABLE";
		//public static final  String CONNECTION = "CONNECTION";	
		public static final  String REVERSIBLE = "REVERSIBLE";
		public static final  String REACTIONDIRECTION = "REACTIONDIRECTION";
		public static final  String ISPRIVATECONNECTION = "ISPRIVATECONNECTION";
	}
	
	

	public class Publication{
	/*
	 * Properties to store Publication in DB
	 */
		public static final  String AUTHOR = "AUTHOR";
		public static final  String TITLE = "TITLE";
		public static final  String ABSTRACT = "ABSTRACT";
		public static final  String JOURNAL = "JOURNAL";
		public static final  String YEAR = "YEAR";
		public static final  String AFFILIATION = "AFFILIATION";
		public static final  String SYNOPSIS = "SYNOPSIS";
		public static final  String ID = "PUBID";
		
	}	
	
	public class Person{
	/*
	 * Properties to store Publication in DB
	 */
		public static final  String FAMILYNAME = "FAMILYNAME";
		public static final  String GIVENNAME = "GIVENNAME";
		public static final  String EMAIL = "EMAIL";
		public static final  String ORGANIZATION = "ORGANIZATION";		
	}	
	
	public class SEDML{
		/*
		 * Properties to store SBML in DB
		 */	
			public static final  String VERSION = "VERSION";
			public static final  String LEVEL = "LEVEL";
			public static final  String NAME = "NAME";
			public static final  String MODELSOURCE = "MODELSOURCE";
			public static final  String MODELCHANGED = "MODELCHANGED";
			public static final  String OUTPUT_TYPE = "OUTPUTTYPE";
			public static final  String XDATA = "XDATA";
			public static final  String YDATA = "YDATA";
			public static final  String ZDATA = "ZDATA";
			public static final  String DATALABEL = "DATALABEL";
			public static final  String SIM_KISAO = "SIMKISAO";
			public static final  String SIM_TYPE = "SIMTYPE";
			public static final  String MATH = "MATH";
			public static final  String TARGET = "TARGET";
			
	}
	
	public class BioPax{
	/*
	 * Properties to store SBML in DB
	 */	
		public static final  String LEVEL = "LEVEL";
		public static final  String RDFID = "RDFID";
	}
	
	public class GroupCalc{
		public static final  String P = "Probability";
		public static final  String IC = "InformationContent";
		public static final  String COUNTofCONCEPTS = "COUNTofCONCEPTS";
		
		public static final  String Trissl = "TrisslScore";

		public static final  String ef = "entityfrequency";
		public static final  String EF = "aggregatedEntityFrequency";
		public static final  String EP = "EntityProbability";

		public static final  String df = "documentfrequency";
		public static final  String DF = "aggregatedDocumentFrequency";
		public static final  String DP = "DocumentProbability";

		
	}
	
	public class  Ontology {
		public static final String NODETYPE = "NODETYPE";
		public static final  String OntologyLongID = "OntologyLongID"; //old
		public static final  String TermID = "id";
		public static final  String isLeaf = "isLeaf";
		public static final  String depth = "depth";

		
	}
}
