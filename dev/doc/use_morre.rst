.. _use_morre:

*****
MORRE
*****

.. include:: rst_directives.rst

Morre – **Mo**\ del **R**\ anked **R**\ etrieval **E**\ ngine

Morre is an Information Retrieval System to search for computational models and related data. It is based on Neo4J and Lucene. Supported model formats are currently SBML and CellML. You can search for models or related persons, publications and annotations.

For this showcase we use cURL, however feel free to use any tool able to handle HTTP Get and POST requests.

Please make sure, you did everything explained in the :ref:`main_setup` before starting to try something locally.

.. contents:: 
    :local:

Querys
######

The plugin offers the following entry points:

http://yourServer:7474/db/data/ext/Diagnose/graphdb/
====================================================

- ``is_model_manager_alive/`` returns a true if the MaSyMoS model manager was loaded or false if not
- ``list_index_available/`` a list of all available index
- ``cellml_model_query_features/`` a list of all features that can be used with a query for CellML models
- ``sbml_model_query_features/`` a list of all features that can be used with a query for SBML models
- ``annotatio_query_features/`` a list of all features that can be used with a query for extracted annotation
- ``person_query_features/`` a list of all features that can be used with a query for extracted persons
- ``publication_query_features/`` a list of all features that can be used with a query for publications

http://yourServer:7474/morre/query/
===================================

a detailed description is provided for each entry point, all following allow ``topn`` (limiting the results to the top n retrieved results),

- ``simple_cellml_model_query/`` allows the features 'keyword' (provided keywords are queried against all features)  retrieves CellML models
- ``simple_sbml_model_query/`` same as above, retrieves SBML models
- ``simple_sedml_query/`` same as above, retrieves Sed-ML descriptions
- ``cellml_model_query/`` takes a list of features ``ID, NAME, COMPONENT, VARIABLE, CREATOR, AUTHOR`` and a corresponding list of keywords, retrieves CellML models
- ``sbml_model_query/`` same as above, the features are ``ID, NAME, COMPARTMENT, SPECIES, REACTION, CREATOR, AUTHOR``, retrieves SBML models
- ``model_query/`` retrieves models, regardless of type, using the feature 'keyword'
- ``publication_query/`` retrieves publications corresponding to a model, features are ``ABSTRACT, AFFILIATION, JOURNAL, YEAR``
- ``publication_model_query``/ same as above, but retrieves the models connected to a publication
- ``person_query/`` retrieves persons corresponding to a model, features are ``FAMILYNAME, GIVENNAME, EMAIL, ORGANIZATION``
- ``person_model_query/`` same as above, but retrieves the models connected to a person
- ``annotation_query/``  allows the features 'keyword', retrieves all matching annotations
- ``annotation_model_query/`` same as above, but retrieves models connected to an annotation

http://yourServer:7474/morre/model_update_service/
==================================================

- ``add_model/`` adds a model to the database, takes the parameters:
    - ``fileId`` a user defined name
    - ``url`` is an accessible location to load the model
    - ``modelType[SBML|CELLML|SEDML]`` defines the encoding, loading OWL is not provided in server mode
    - this method returns a ``uID`` as result
- ``delete_model/`` removes a model from the database, parameters:
    - ``uID`` generated during ``add_model/``
    - ``fileID`` is optional and used to double-check
- ``create_annotation_index/`` by default no annotation index is generated in server mode when adding a model, parameters:
    - ``dropExistingIndex true|false`` to decide if the annotation index should be deleted or updated

Examples
########

Insert, Delete
==============

.. code-block:: bash

    # add_model
    curl -X POST http://localhost:7474/morre/model_update_service/add_model -H "Content-Type: application/json" -d '{"fileId":"testId", "url":"https://www.ebi.ac.uk/biomodels/model/download/BIOMD0000000196.2?filename=BIOMD0000000196_url.xml","modelType":"SBML"}'
    > {"uID":"1","ok":"true","url":"https://www.ebi.ac.uk/biomodels/model/download/BIOMD0000000196.2?filename\u003dBIOMD0000000196_url.xml","fileId":"testId"}

    # create_annotation_index
    curl -X POST http://localhost:7474/morre/model_update_service/create_annotation_index -H "Content-Type: application/json" -d '{"dropExistingIndex":"true"}'
    > {"ok":"true"}

    # delete_model
    curl -X POST http://localhost:7474/morre/model_update_service/delete_model -H "Content-Type: application/json" -d '{"uID":"1"}'
    > {"uID":"1","nodes deleted":"136","relations deleted":"382","successful":"true"}

Basic Query
===========

For each query type a GET and POST request is possible. The GET request will return a JSON object holding all available features for the particular query. In case of the ``cellml_model_query`` the request is:

    :c_bash:`curl -X GET http://yourServer:7474/morre/query/cellml_model_query/ -H "Content-Type: text/plain"`
        > :c_json:`["ID","NAME","COMPONENT","VARIABLE","CREATOR","AUTHOR"]`

Now we can setup a query using POST and a JSON object:

    :c_bash:`curl -X POST http://yourServer:7474/morre/query/cellml_model_query/ -H "Content-Type: application/json" -d '{"features":["NAME","COMPONENT"], "keywords":["novak","sodium model math channel"]}'`
        As a result a JSON object is returned containing the retrieved models and according scores.

        .. code-block:: json

            [
            {"modelName":"Calzone_Thieffry_Tyson_Novak_2007_version01","score":0.013774771,"modelID":"Calzone_Thieffry_Tyson_Novak_2007_version01","databaseID":3011380124378,"documentURI":"http://models.cellml.org/exposure/1a3f36d015121d5596565fe7d9afb332/calzone_thieffry_tyson_novak_2007.cellml","filename":"calzone_thieffry_tyson_novak_2007.cellml"},
            {"modelName":"irvine_model_1999","score":0.011314708,"modelID":"irvine_model_1999","databaseID":2775129546107,"documentURI":"http://models.cellml.org/exposure/5ba0fed413fc9648336caaea382e038a/irvine_jafri_winslow_1999.cellml","filename":"irvine_jafri_winslow_1999.cellml"},
            {"modelName":"potter_model_2005","score":0.0059871213,"modelID":"potter_model_2005","databaseID":1175814884599,"documentURI":"http://models.cellml.org/exposure/80982c99e643a576d10e7f3e271a2299/potter_greller_cho_nuttall_stroup_suva_tobin_2005_a.cellml","filename":"potter_greller_cho_nuttall_stroup_suva_tobin_2005_a.cellml"},
            "…"
            ]

More Examples
=============

.. code-block:: bash

    curl -X GET http://yourServer:7474/morre/query/cellml_model_query/ -H "Content-Type: text/plain"

    curl -X POST http://yourServer:7474/morre/query/simple_cellml_model_query/ -H "Content-Type: application/json" -d '{"keyword":"novak"}'

    curl -X POST http://yourServer:7474/morre/query/simple_cellml_model_query/ -H "Content-Type: application/json" -d '{"keyword":"novak", "topn":"3"}'

    curl -X POST http://yourServer:7474/morre/query/cellml_model_query/ -H "Content-Type: application/json" -d '{"features":["NAME","ID","COMPONENT"], "keywords":["novak","novak","sodium_current_h_gate"]}'

    curl -X POST http://yourServer:7474/morre/query/cellml_model_query/ -H "Content-Type: application/json" -d '{"features":["NAME","ID","COMPONENT"], "keywords":["novak","novak","sodium_current_h_gate"],"topn":"3"}'

    curl -X POST http://yourServer:7474/morre/query/person_model_query/ -H "Content-Type: application/json" -d '{"features":["FAMILYNAME"], "keywords":["Lloyd"]}'

    curl -X POST http://yourServer:7474/morre/query/publication_model_query/ -H "Content-Type: application/json" -d '{"features":["TITLE"], "keywords":["Mathematical modeling of mechanically modulated rhythm disturbances in homogeneous and heterogeneous myocardium with attenuated activity of na+ -k+ pump"]}'

    curl -X POST http://yourServer:7474/morre/query/person_query/ -H "Content-Type: application/json" -d '{"features":["GIVENNAME"], "keywords":["Sulman"]'

    curl -X POST http://yourServer:7474/morre/query/publication_query/ -H "Content-Type: application/json" -d '{"features":["TITLE"], "keywords":["Mathematical modeling of mechanically modulated rhythm disturbances in homogeneous and heterogeneous myocardium with attenuated activity of na+ -k+ pump"]}'

    curl -X POST http://yourServer:7474/morre/query/annotation_model_query/ -H "Content-Type: application/json" -d '{"keyword":"This study investigates the reverse mode of the Na/glucose cotransporter SGLT1. In giant excised inside-out membrane patches from Xenopus laevis oocytes expressing rabbit SGLT1, application of alpha-methyl D"}'

    curl -X POST http://yourServer:7474/morre/query/annotation_query/ -H "Content-Type: application/json" -d '{"keyword":"This study investigates the reverse mode of the Na/glucose cotransporter SGLT1. In giant excised inside-out membrane patches from Xenopus laevis oocytes expressing rabbit SGLT1, application of alpha-methyl D"}'

    curl -X POST http://yourServer:7474/morre/query/model_query/ -H "Content-Type: application/json" -d '{"keyword":"novak sodium model math channel"}'
