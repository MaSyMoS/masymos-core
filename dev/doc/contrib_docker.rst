.. _contrib_docker:

********************
Docker Documentation
********************

.. include:: rst_directives.rst

.. contents:: 

Meta
####

The file ``🏃 cleanup.sh`` in the root directory cleans up all resources, the masymos scripts created on your system (like images, containers, volumes,…).

Jar-Builder
###########

Everything in the folder ``📂 jar-builder`` is only for creating the jar files inside a docker container.

On the first run of the docker container, maven will download all dependencies and saves them to a docker volume. So it may take some time. The docker volume for the maven dependencies is not removed automatically, use ``🏃 cleanup.sh`` for that.

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
        - 🔧 ``user_group_numbers.sh``
            - this file contains the UID and GID, that the files in ``📂 masymos-builds`` and ``📂 masymos-source`` will have after the run
            - if you have problems with editing/deleting the files, you may have to change the settings here
            - get your UID with :c_bash:`id -u`
            - get your GID with :c_bash:`id -g`
    - 🏃 ``maven-build-masymos-jars.sh``
        - ℹ call with parameter ``rebuild`` to force the creation of a new docker image
        - this file automatically checks all dependencies to run the docker container
            #. remove old jar-builds
            #. clone masymos repositories, if they are missing
            #. create maven artifacts volume ``masymos_maven_artifacts``, if missing
            #. build docker image, if missing or if parameter ``rebuild`` is set
            #. run docker container ``masymos-jar-builder`` and remove it afterwards
            #. output non-success docker return codes

Docker Dependencies
===================
- the folder mounted to the output volume should not contain any old builds
- the folder mounted to the input volume must contain the masymos repositories
- a maven artifacts volume (no need to map it to another HOST-directory)
