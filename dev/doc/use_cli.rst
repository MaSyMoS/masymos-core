.. _use_cli:

***
CLI
***

.. contents:: 
    :local:

MainExtractor
#############

- entry point: ``de.unirostock.sems.masymos.main.MainExtractor``

.. csv-table:: Arguments
    :header: "Argument", "Type", "Required", "Default", "Description"
    :widths: 10, 10, 10, 10, 70

    ``-dbPath``, String, true, /, "pointsthepath of your database, if no DB exists one will be created"
    ``-directory``, String, annoOnly==false, /, "points to the directory containing SBML, CellML, Sed-ML files, also OWL files can be processed"
    ``-type``, String, annoOnly==false, SBML, "specifies the expected type of input ``SBML|CELLML|SEDML|OWL|P2M``

    P2M is working in the intended usage if one wants to index `Path2Models files <https://bmcsystbiol.biomedcentral.com/articles/10.1186/1752-0509-7-116>`__"
    ``-ontology``, String, type==OWL, /, "is expected when ``-type`` is ``OWL``, used to define the ontology namespace"
    ``-quiet``, boolean, false, false, "rewires the stdOut to dev0, faster but no debugging"
    ``-noAnno``, boolean, false, false, "creates only the model structures but does not resolve and index model annotations"
    ``-annoOnly``, boolean, false, false, "creates only the annotation index"
    ``-fileMode``, boolean, false, false, "lst-file, if parameter not given"

- The arguments ``-noAnno`` and ``-annoOnly`` can not be used together, obviously.
- If ``-annoOnly`` is provided all other parameters except for ``-dbPath`` are ignored.
 
Creating the annotation index is time consuming, it spawns several Threads, uses `MiriamWebService <https://www.ebi.ac.uk/miriam/main/mdb?section=ws>`__ to resolve all annotations into URLs and subsequently downloads several hundred webpages for text extraction and index creation.

MainIndex
#########

- entry point: `de.unirostock.sems.masymos.main.MainIndex`

.. csv-table:: Arguments
    :header: "Argument", "Type", "Required", "Default", "Description"
    :widths: 10, 10, 10, 10, 70
    
    ``-dbPath``, String, true, /, "pointsthepath of your database, if no DB exists one will be created"
    ``-deleteIndex``, bool, false, false, "if true, delete all created index structures"

MainAnalyzer
############

- entry point: `de.unirostock.sems.masymos.main.MainAnalyzer`

The MainAnalyzer allows to play around with some customized analysers, for `more information go <https://lucene.apache.org/core/6_4_1/core/org/apache/lucene/analysis/Analyzer.html?is-external=true>`__. This is handy if you want to check how text and queries are processed.

MainQuery
#########

- entry point: `de.unirostock.sems.masymos.main.MainQuery`

.. csv-table:: Arguments
    :header: "Argument", "Type", "Required", "Default", "Description"
    :widths: 10, 10, 10, 10, 70
    
    ``-dbPath``, String, true, /, "points the path of your database, if no DB exists one will be created"
