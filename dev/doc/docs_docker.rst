.. _docs_docker:

********************
Docker Documentation
********************

.. include:: rst_directives.rst

.. contents:: 
    :local:

Meta
####

- The file ``🏃 cleanup.sh`` in the root directory
    - cleans up all docker resources, the masymos scripts created on your system (like images, containers, volumes,…).
    - cloned repositories in ``📂 jar-builder/masymos-source`` or built jars in ``📂 jar-builder/masymos-builds`` are not affected.

Jar-Builder
###########

Everything in the folder ``📂 jar-builder`` is only for creating the jar files inside a docker container.

Usage
=====

Call the ``🏃 jar-builder/maven-build-masymos-jars.sh`` to create the jar files and library folder automatically.
Use the parameter ``rebuild`` to force a rebuild of the image.

If you want a different version then the ``master`` of the repositories, please check them out manually in the folder ``📂 jar-builder/masymos-source``

On the first run of the docker container, maven will download all dependencies and saves them to a docker volume. So it may take some time.

The docker volume for the maven dependencies is not removed automatically, use ``🏃 cleanup.sh`` for that.

Folder and Storage Overview
===========================

- 📂 jar-builder
    - 📂 d_jar-builder
        - contains the Dockerfile and the files, that are copied into the docker image during build process
    - 📂 masymos-builds
        - this is the docker-output-directory
        - find the jar files here
    - 📂 masymos-source
        - this is the docker-input-directory
        - it must contain the source repository of the masymos source code
        - ℹ it's not possible to use symlinks here
        - 🔧 user_group_numbers.sh
            - this file contains the UID and GID, that the files in ``📂 jar-builder/masymos-builds`` and ``📂 jar-builder/masymos-source`` will have after the run
            - if you have problems with editing/deleting the files, you may have to change the settings here
            - get your UID with :c_bash:`id -u`
            - get your GID with :c_bash:`id -g`
    - 🏃 maven-build-masymos-jars.sh
        - ℹ call with parameter ``rebuild`` to force the creation of a new docker image
        - this file automatically checks all dependencies to run the docker container
            #. remove old jar-builds
            #. clone masymos repositories (master), if they are missing
            #. create maven artifacts volume ``masymos_maven_artifacts``, if missing
            #. build docker image ``masymos-jar-builder``, if missing or if parameter ``rebuild`` is set
            #. on Windows-Systems (git-bash or cygwin) the script needs to edit the volume paths for docker - for debugging reasons these paths are written to the output (see ``OSTYPE``)
            #. run docker container ``masymos-jar-builder`` and remove it afterwards
            #. output non-success docker return codes

Docker Dependencies
===================

- the folder mounted to the output volume should not contain any old builds
- the folder mounted to the input volume must contain the masymos repositories
- a maven artifacts volume (no need to map it to another HOST-directory)

Server Integration
##################

- Everything in the folder ``📂 server-integration`` is for running masymos-morre as neo4j plugin in a docker container.
- open Ports: `7474 and 7687 for HTTP and Bolt <https://hub.docker.com/_/neo4j/>`__
- the default database used is named ``morre`` (run-parameter ``--env "NEO4J_dbms_active__database=morre"`` )

.. WARNING:: by default the server is not secured at all

    - Authentication is disabled (run-parameter ``--env "NEO4J_AUTH=none"``)

Usage
=====

Call the ``🏃 server-integration/run-neo4j-server.sh`` after running the *jar-builder*  to setup and start the neo4j server with the masymos morre plugin.
Use the parameter ``rebuild`` to force a rebuild of the image.

An empty docker volume ``masymos_neo4j_database`` is created as new neo4j database. If you want to use an existing database, please see :ref:`docker_using_own_volume`.
The docker volume for the neo4j database is not removed automatically, use ``🏃 cleanup.sh`` for that.

If you like to see the neo4j logs directly after starting the container, use this command:
    :c_bash:`./run-neo4j-server.sh && docker logs -f masymos_neo4j`

.. _docker_using_own_volume:

Using you own volume
--------------------

If you have an existing neo4j database, there are two selutions using it with this docker instance:

#. copy the database into a docker volume without mounting it to the user space
    - the volume is managed by docker
    - ⚠ calling the ``🏃 cleanup.sh`` may removes the volume and all stored data
    - this is the preferred method, as your local data will not be changed
#. mount the local directory in the user space as docker volume
    - the database files will be changed by processing them with the server

.. _docker_copy_database_into_docker_volume:

Copy your database into the docker volume
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

#. find the file ``🏃 server-integration/copy_own_data_into_docker_volume.sh`` and the location of your local database
    - please note, that you need to use the database folder named after your database, not the neo4j-databases-root
    - your database is a named folder inside a folder called ``databases``
    - example: ``/home/ulf/database-engine/neo4j/data/databases/my_db``
#. run the script with :c_bash:`$ ./copy_own_data_into_docker_volume.sh /path/to/neo4j/databases/my_database`
    - you may check the UID/GID (should be YOUR ids)
    - you may check the size of the created volume

Mount a local volume in user space
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

You may edit the ``🏃 server-integration/run-neo4j-server.sh`` runner.

#. find the docker run command
#. you need to point to the root of your neo4j-database folder
    - inside this folder, there must be a directory ``databases``
    - inside the ``databases`` directory, there must be your *database folder*
#. change the volume mount parameter to the localisation of your database, i.e.
    - ``--volume "/opt/neo4j:/data" \``
    - ``--volume "$HOME/neo4j:/data" \``
    - ``--volume "$PWD/../../neo4j:/data" \``
#. you also need to change the database name to your *database folder name* in this line
    - ``--env "NEO4J_dbms_active__database=morre" \``

Folder and Storage Overview
===========================

- 📂 server-integration
    - 📂 d_server
        - contains the Dockerfile and the files, that are copied into the docker image during build process
        - 📂 masymos-builds
            - this folder contains the copied jar-files from the *jar-builder*
    - 🏃 copy_own_data_into_docker_volume.sh
        - script for copying a local database into a docker volume, please see ":ref:`docker_copy_database_into_docker_volume`"
    - 🏃 run-neo4j-server.sh
        - ℹ call with parameter ``rebuild`` to force the creation of a new docker image
        - this file automatically checks all dependencies to run the docker container
            #. abort, if container is still running
            #. create docker database volume ``masymos_neo4j_database``, if missing
            #. build docker image ``masymos_neo4j``, if missing or if parameter ``rebuild`` is set
                - remove old jar copies
                - copy libraries from *jar-builder* to ``📂 server-integration/d_server/masymos-builds``
                - build image
            #. run docker container ``masymos_neo4j`` in detached mode and remove it afterwards
            #. output non-success docker return codes

