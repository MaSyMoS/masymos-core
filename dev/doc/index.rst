======================
MaSyMoS documentation!
======================

Welcome to the MaSyMoS-Documentation!

"**Ma**\ nagement **Sy**\ stem for **Mo**\ dels and **S**\ imulations" is a Neo4J based database for SBML and CellML Models as well as simulation descriptions in SED-ML format. In addition, MaSyMoS contains bio-ontologies that are most frequently used for the annotation of models, i.e., GO, ChEBI, SBO, KiSAO.

All data is represented as a Graph and can be queried using Cypher. A description of the underlying database is available from our latest publication in [Oxford DATABASE](http://www.ncbi.nlm.nih.gov/pubmed/25754863).

Source Code, Feedback and Contribution
######################################

- 🔬 Find the `MaSyMoS project on GitHub <https://github.com/MaSyMoS>`__.
- 📝 Read open issues or `create new ones for feature requests and bugs <https://github.com/MaSyMoS/masymos-core/issues>`__.
- 🔧 Contribute to the project after reading the :ref:`contrib_main`.

Modules
=======

- `MaSeMiWa - MaSyMoS SEEK middleware <http://masemiwa.rtfd.io/>`__

Table of Content
################

.. toctree::
    :maxdepth: 2
    :name: maintoc
    :includehidden:

.. toctree::
    :caption: Overview and Installation
    :maxdepth: 2

    main_overview
    main_setup
    docs_docker

.. toctree::
    :caption: How to use MaSyMoS
    :maxdepth: 2

    use_cli
    use_morre
    use_troubleshooting

.. toctree::
    :caption: Contribution and Development
    :maxdepth: 2

    contrib_main
    contrib_start
    contrib_rtfd

License
#######

.. literalinclude:: ../../LICENSE
    :language: text


.. Indices and tables
.. ##################
.. 
.. * :ref:`genindex`
.. * :ref:`modindex`
.. * :ref:`search`
