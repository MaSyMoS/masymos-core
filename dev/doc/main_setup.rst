.. _main_setup:

*****
Setup
*****

.. include:: rst_directives.rst

.. _`Neo4J WebServer`: https://neo4j.com/download/other-releases/

MaSyMoS can be used as a standalone or as a plugin for the `Neo4J WebServer`_.

If you want to run MaSyMoS from it's source locally on your computer, please follow the steps described in :ref:`contrib_start` to setup an development environment.

Setup Standalone
################

To create a standalone you need ``masymos-core`` and ``masymos-cli``.

To create it locally, clone the repositories, get the dependencies via Maven and build the jar.

.. TODO provide docker image here

Find the instructions on how to use the command line interface in the :ref:`use_cli`.

Setup Server
############

If you want to use MaSyMoS as a search server for models you will have to set up the server version. To do so, start by downloading `Neo4J WebServer`_.
Please check the Neo4J-vesion in pom.xml (:c_xml:`<groupId>org.neo4j</groupId>`) before downloading the server. You might want to make sure to compile MaSyMoS with the same version as the downloaded server. Follow the Neo4J setup instructions.

To create a Server-MaSyMoS you need ``masymos-core`` and ``masymos-morre``. Then follow these steps:

#. Use Maven to compile ``masymos-core`` (``mvn install``) without dependencies and do the same with ``masymos-morre`` (``mvn package``). You must use the same Java version used for Neo4J.

#. Copy the JAR for ``masymos-more`` into ``$Neo4jRoot/plugins``.

#. Open ``$Neo4JRoot/conf/neo4j.conf`` and set:

    .. csv-table:: Arguments
        :header: "Key", "Value", "Description"
        :widths: 10, 10, 80
        
        ``dbms.active_database``, … , "points the path of your database"
        ``dbms.allow_format_migration``, true, "if you are using a server with a higher version then the one the DB was created with"
        ``dbms.security.auth_enabled``, false, "or configure the authentication"
        ``dbms.unmanaged_extension_classes``, ``de.unirostock.morre.server.plugin=/morre``, "to allow MaSyMoS plugin to be loaded"

    - you might also want to uncomment ``dbms.connector.http.address=0.0.0.0:7474`` to accept non-localhost connections

#. You will also need some additional libraries for the server, easiest way is to create ``masymos-core`` and ``masymos-morre`` without dependencies and let Maven pack all necessary libraries in a directory next to the generated JAR.

    - ℹ packing all the libraries inside the morre-package (fat jar) is not a good idea, even having the libs inside the core package will not work, because neo4j may has other versions running.
    - To get all the libraries, please follow these steps

        #. look up the ``target/lib``-directory after the build in ``masymos-core`` and ``masymos-morre``. (There is maven goal ``copy-dependencies`` for this in the pom, running automatically.)

            - copy all the libraries into ``$Neo4jRoot/lib/ext`` (you may need to create it).

        #. Avoid unpredictable behaviour based on java-library complications

            #. Delete all libraries from ``lib/ext``, that are in ``lib`` i.e. with bash in ``lib/ext``: :c_bash:`for f in ./../*; do rm -v "./$(basename $f)"; done`.
            
            #. Delete the following files from ``lib/ext``:

                - ``*.pom``
                - ``neo4j-*.jar``
                - ``server-*.jar``

    - The result in ``$Neo4JRoot/lib/ext`` may look like this (list is maybe outdated)

    .. raw:: html

       <details>
       <summary><a>click here to show the list</a></summary>

    .. code-block:: text

        activation-1.1.jar
        animal-sniffer-annotations-1.14.jar
        axis-1.4.jar
        axis-jaxrpc-1.4.jar
        axis-saaj-1.4.jar
        axis-wsdl4j-1.5.1.jar
        BFLog-1.3.7.jar
        BFUtils-0.5.7.jar
        biojava-ontology-4.0.0.jar
        BiVeS-CellML-1.8.0.jar
        BiVeS-Core-1.9.1.jar
        caffeine-2.6.1.jar
        collections-generic-4.01.jar
        commons-cli-1.3.jar
        commons-codec-1.11.jar
        commons-csv-1.0.jar
        commons-discovery-0.2.jar
        commons-lang3-3.9.jar
        commons-logging-1.2.jar
        commons-rdf-api-0.5.0.jar
        dom4j-1.6.1.jar
        error_prone_annotations-2.0.18.jar
        fluent-hc-4.5.5.jar
        gson-2.8.6.jar
        guava-22.0.jar
        hamcrest-core-1.3.jar
        hppcrt-0.7.5.jar
        httpclient-4.5.10.jar
        httpclient-cache-4.5.2.jar
        httpclient-osgi-4.5.5.jar
        httpcore-4.4.12.jar
        httpcore-nio-4.4.9.jar
        httpcore-osgi-4.4.9.jar
        httpmime-4.5.5.jar
        j2objc-annotations-1.1.jar
        jackson-annotations-2.9.10.jar
        jackson-core-2.9.10.jar
        jackson-databind-2.9.10.jar
        javax.inject-1.jar
        javax.ws.rs-api-2.0.jar
        jaxen-1.1.1.jar
        jcl-over-slf4j-1.7.22.jar
        jCOMODI-1.0.2.jar
        jdom2-2.0.5.jar
        jdom-1.0.jar
        jdom-1.1.3.jar
        jdom-contrib-1.1.3.jar
        jena-arq-3.0.0.jar
        jena-base-3.0.0.jar
        jena-core-3.0.0.jar
        jena-iri-3.0.0.jar
        jena-shaded-guava-3.0.0.jar
        jena-tdb-3.0.0.jar
        jfact-5.0.3.jar
        jigsaw-2.2.6.jar
        jlibsedml-2.2.3.jar
        jmathml-2.2.1.jar
        jms-1.1.jar
        jmxri-1.2.1.jar
        jmxtools-1.2.1.jar
        joda-time-2.9.7.jar
        jsbml-1.5.jar
        jsbml-arrays-1.5.jar
        jsbml-comp-1.5.jar
        jsbml-core-1.5.jar
        jsbml-distrib-1.5.jar
        jsbml-dyn-1.5.jar
        jsbml-fbc-1.5.jar
        jsbml-groups-1.5.jar
        jsbml-layout-1.5.jar
        jsbml-multi-1.5.jar
        jsbml-qual-1.5.jar
        jsbml-render-1.5.jar
        jsbml-req-1.5.jar
        jsbml-spatial-1.5.jar
        jsbml-tidy-1.5.jar
        jsonld-java-0.12.0.jar
        json-simple-1.1.1.jar
        jsoup-1.12.1.jar
        jsr305-3.0.2.jar
        jtidy-r938.jar
        jul-to-slf4j-1.7.25.jar
        junit-4.12.jar
        libthrift-0.9.2.jar
        log4j-1.2.15.jar
        log4j-1.2-api-2.3.jar
        log4j-api-2.8.2.jar
        log4j-core-2.8.2.jar
        log4j-slf4j-impl-2.1.jar
        mail-1.4.jar
        masymos-core-1.0.1.jar
        metainf-services-1.8.jar
        miriam-lib-1.1.6.jar
        netty-all-4.1.32.Final.jar
        owlapi-api-5.1.12.jar
        owlapi-apibinding-5.1.12.jar
        owlapi-compatibility-5.1.12.jar
        owlapi-distribution-5.1.12.jar
        owlapi-impl-5.1.12.jar
        owlapi-oboformat-5.1.12.jar
        owlapi-parsers-5.1.12.jar
        owlapi-rio-5.1.12.jar
        owlapi-tools-5.1.12.jar
        paxtools-core-5.1.0.jar
        rdf4j-model-2.3.2.jar
        rdf4j-rio-api-2.3.2.jar
        rdf4j-rio-binary-2.3.2.jar
        rdf4j-rio-datatypes-2.3.2.jar
        rdf4j-rio-jsonld-2.3.2.jar
        rdf4j-rio-languages-2.3.2.jar
        rdf4j-rio-n3-2.3.2.jar
        rdf4j-rio-nquads-2.3.2.jar
        rdf4j-rio-ntriples-2.3.2.jar
        rdf4j-rio-rdfjson-2.3.2.jar
        rdf4j-rio-rdfxml-2.3.2.jar
        rdf4j-rio-trig-2.3.2.jar
        rdf4j-rio-trix-2.3.2.jar
        rdf4j-rio-turtle-2.3.2.jar
        rdf4j-util-2.3.2.jar
        RoaringBitmap-0.6.32.jar
        slf4j-api-1.7.13.jar
        slf4j-log4j12-1.7.12.jar
        stax2-api-3.1.4.jar
        staxmate-2.3.0.jar
        trove4j-3.0.3.jar
        woodstox-core-5.0.1.jar
        xalan-2.7.0.jar
        xercesImpl-2.8.0.jar
        xml-apis-1.3.03.jar
        xmlpull-1.1.3.1.jar
        xmlutils-0.6.13.jar
        xom-1.3.2.jar
        xpp3_min-1.1.4c.jar
        xstream-1.4.9.jar
        xz-1.6.jar
        zstd-proxy-3.5.13.jar

    .. raw:: html

       </details>

#. Finally, we need to add ``$Neo4JRoot/lib/ext`` to the servers classpath.

    - Linux
        - Open ``$Neo4JRoot/bin/neo4j`` and add ``:${NEO4J_LIB}/ext/*`` to CLASSPATH variable

        .. code-block:: bash

            build_classpath() {
                CLASSPATH="${NEO4J_PLUGINS}:${NEO4J_CONF}:${NEO4J_LIB}/*:${NEO4J_LIB}/ext/*:${NEO4J_PLUGINS}/*"
                # …

    - Windows
        - Open ``$Neo4JRoot/bin/Neo4j-Management/Get-Java.ps1`` and add ``;$($Neo4jServer.Home)/lib/ext/*`` to the Java command line:

        .. code-block:: ps1

            # Build the Java command line
            $ClassPath="$($Neo4jServer.Home)/lib/*;$($Neo4jServer.Home)/plugins/*;$($Neo4jServer.Home)/lib/ext/*"

- Well done, you can now try to start MaSyMoS with :c_bash:`./bin/neo4j console`!
- Read about how to work with the server on :ref:`use_morre`.
