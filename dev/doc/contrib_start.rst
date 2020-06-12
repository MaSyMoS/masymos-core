.. _contrib_start:

***************
Getting Started
***************

.. contents:: 

Installation of MaSyMoS Development Environment
###############################################

Setup workspace
===============

Setup Folder Structure
----------------------

.. Note:: the ``📂 Project folder`` is represented *in this instruction* by ``[ENTER_HERE_YOUR_PROJECT_ROOT]``

- create one single root ``📂 Project folder`` for *everything* needed for MaSyMoS
- create the folder structure with subfolders as described below:
    - 📂 Project folder
        - 📁 code
            - all repositories
        - 📁 mvn
            - Maven libraries
        - 📁 eclipse
            - a dedicated Eclipse for MaSyMoS
        - 📁 database-engine
            - the Neo4j database engine with webserver and everything
        - 📁 databases
            - the MaSyMoS-generated databases 
        - 📁 raw-data
            - raw data, you're downloading to work with
        - 📁 workspace
            - your Eclipse workspace

Download/Install needed Software
--------------------------------

- `Download the newest "Eclipse IDE Enterprise for Java Developers" <https://www.eclipse.org/downloads/packages/>`__ and extract it to ``📁 eclipse``
- `Download Neo4j v3.5 <https://neo4j.com/download-center/#community>`__ end extract it to ``📁 database-engine``
- install Java 11 JDK (``apt install openjdk-11-jre``)
- create a file ``settings.xml`` in ``📁 mvn`` and paste the following content (please replace ``[ENTER_HERE_YOUR_PROJECT_ROOT]``):

.. code-block:: xml

    <settings xmlns="http://maven.apache.org/SETTINGS/1.0.0"
              xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
              xsi:schemaLocation="http://maven.apache.org/SETTINGS/1.0.0
                                  https://maven.apache.org/xsd/settings-1.0.0.xsd">

      <localRepository>[ENTER_HERE_YOUR_PROJECT_ROOT]/mvn/</localRepository>
    </settings>

Configure Software
==================

Configure Eclipse
-----------------

- start up Eclipse
- set default workspace to ``📁 workspace`` → restart Eclipse
- use ``📁 mvn`` as Maven-Path
    - *Window → Preferences → Maven → User Settings*
      - Global Settings: ``[ENTER_HERE_YOUR_PROJECT_ROOT]/mvn/settings.xml``

.. Attention:: set default Encoding and line delimiter (most important on Windows!)

    - *Window → Preferences → General → Workspace*
        - Text file encoding: ``UTF-8``
        - New text file line delimiter: ``UNIX`` (``\n``)

.. _configure-neo4j:

Configure Neo4j
---------------

- edit file ``📁 database-engine/neo4j/conf/neo4j.conf``
    - ✎ ``dbms.active_database=model-extraction-sbml``
        - the value of this key points to the expected database in this folder: ``📁 database-engine/neo4j-enterprise/data/databases/``
    - ✎ ``dbms.security.auth_enabled=false``
        - disable security restrictions for authentication

Load Repositories and Projects
==============================

Clone Repositories
------------------

- all repositories are stored in ``📁 code``, you need
    - masymos-cli
    - masymos-core
    - masymos-morre
- you can do this with Eclipse as well → Git Perspective

Load Eclipse projects
---------------------

- *Java EE* Perspective
- for all three projects do:
    - *File → Import… → Git → Projects from Git*
        - existing local repository
    - *right-click project → Maven → Update Project…*

Work with your first Database
#############################

Create Database
===============

- get SBML Models from https://www.ebi.ac.uk/biomodels/
    - i.e. ftp://ftp.ebi.ac.uk/pub/databases/biomodels/releases/2017-06-26/
    - extract to ``📁 raw-data``
- create a Java Application runner ``model-extraction-sbml`` for class ``/masymos-cli/src/de/unirostock/sems/masymos/main/MainExtractor.java`` with the following parameters

.. code-block:: text

    -dbPath [ENTER_HERE_YOUR_PROJECT_ROOT]/databases/model-extraction-sbml
    -directory [ENTER_HERE_YOUR_PROJECT_ROOT]/raw-data/sbml/curated
    -noAnno
    -type sbml
    -fileMode

Open the Database in Neo4j
==========================

- copy the folder ``📁 /databases/model-extraction-sbml`` to ``📁 database-engine/neo4j-enterprise/data/databases/model-extraction-sbml``
- the DB path was already set during :ref:`configure-neo4j` to:
    - ✎ ``dbms.active_database=model-extraction-sbml``
- start Neo4j and browse the data
    - go to ``📁 database-engine/neo4j``
    - ``./bin/neo4j console``
    - open in Browser: http://localhost:7474
    - simple query: ``match (m:MODEL)-->(s:SBML_SPECIES) return count(s) as Anzahl, m.NAME order by Anzahl desc``
