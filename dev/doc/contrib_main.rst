************
Contributing
************

.. contents:: 
    :depth: 4

.. toctree::

Documentation
#############

If you like to contribute to this project, please try to follow the contribution specifications!

The Documentation is in ReStructuredText.
All Files are in the ``masymos-core`` project in Folder ``/dev/doc``.

.. _consist: 

Consistency
===========

To try keep the code and the documentation consistent, please stay with the following conventions.

General
-------
- please use exactly 4 spaces to indent anything
- don't use TABs (``\t``)
- referencing headings in the same document
    - create ref-links ``.. _my_ref_link:``
    - reference by ``:ref:`my_ref_link```
    - example-link to :ref:`consist`
- check the Output after you call ``make html`` - are there any warnings, errors,…?

Titles and Headers
------------------

- each page has a title describing the topic
    - this title is over- and underlines with ``*``
- Header 1 is underlined with ``#``
- Header 2 is underlined with ``=``
- Header 3 is underlined with ``-``
- Header 4 is underlined with ``~``
- Header 5 is underlined with ``^``

Helpful Links:
==============

- `An introduction to Sphinx and Read the Docs for Technical Writers <https://www.ericholscher.com/blog/2016/jul/1/sphinx-and-rtd-for-writers/>`__
- `reStructuredText Primer Documentation <https://www.sphinx-doc.org/en/master/usage/restructuredtext/basics.html>`__
- `ReStructuredText-CheatSheet <https://thomas-cokelaer.info/tutorials/sphinx/rest_syntax.html>`__












Contribution to MaSyMoS
#######################

General guidelines
==================

Project Version numbering
-------------------------

- the projects version is stored inside the ``pom.xml`` in `project.version`
- version schema: ``major.minor.bugfix[-STAGE]``
    - usually functional changes are *minor*, except, the project owner decides, that there is a major release
    - the version will ONLY be edited, if the test or master branch releases the new change (there is no need to edit the version in your development environment)
    - when increasing the *minor*, set *bugfix* to zero
    - when increasing the *major*, set *minor* and *bugfix* to zero
    - for the ``-STAGE`` you usually use ``-TEST`` for all test versions
    - for the productive version, the stage is omitted

Using the CHANGELOG.md
----------------------

- every project has it's own ``CHANGELOG.md``-file
    - everyone can see every change between version X and Y
    - see an example changelog for `GitLab here <https://gitlab.com/gitlab-org/gitlab/blob/master/CHANGELOG.md>`__
- `for general information about Changelogs, please read this <https://keepachangelog.com>`__
- How to use the Changelog?
    - when you added/changed/removed something (in your fork or branch) add that information to the ``CHANGELOG.md`` under the heading ``## [Unreleased]``
    - please always add the link to your issue for anything you add there
        - i.e. add ``MaSyMoS/masymos-core#42``
    - when your fork/branch is then merged into the master, your changes will then get the right heading with the pom-version-number and the date

Working with the projects
=========================

- the projects in https://github.com/MaSyMoS are our master; don't push your changes there
    - instead Fork the repository an work on your copy
- the *master* branch is the productive release branch
- there are maybe other important branches like `test`
- changes are includes via pull-requests

Working with git
----------------

- all release versions get a tag containing the version
- for a clean history we can make use of the git-feature ``rebase``

Commit and Push
~~~~~~~~~~~~~~~

- comment your commits!
    - to keep track of all changes → provide a small text for *each commit*
        - the length of the first line of your commit can be up to 72 Bytes long
        - if you need more comments separate them with two line breaks
        - see `here <https://gist.github.com/robertpainsi/b632364184e70900af4ab688decf6f53>`__ and `here <https://chris.beams.io/posts/git-commit/>`__
    - do not use prefixes like ``dev:``, ``fix:``; better use verbs like ``added``, ``changed``, ``fixes``,…
    - link your commits to the related issues, i.e. if you worked on Issue Nr. 23 your comment could be something like
        - `MaSyMoS/masymos-core#42 removed bug in Auth.java`

Content of your Commit
^^^^^^^^^^^^^^^^^^^^^^

- never push functional commits to the *master* branch! (i.e. Bugfix, Features, internal changes) → use branches or forks!
- never combine functional and non-functional changes in one commit
    - example: changing the formatting of all files AND a bugfix → nobody will ever find your changes for that bugfix
- usually it's good to have exactly one commit per specific change

Merge
~~~~~

- never mix a merge with any commit (functional or non-functional)
    - if you're merging two branches, the resulting commit has two parents → therefore a diff will be very difficult
    - so for a merge ONLY resolve conflicts, never add/edit/delete something

Working with GitHub
===================

Working with Issues, Labels, Milestones
---------------------------------------

- for every task create an Issue in the project you're working
    - if your task affects MaSyMoS on a Meta-Level or more the one part, use the ``masymos-core`` project
- the first comment in an issue contains a *current overview about the status* of this issue
    - use markdown and checkboxes inside this comment to mark important things and todos
    - as headlines you can use
        - **Overview** - general description
        - **ToDo** - all single steps to take to resolve this issue → use checkboxes here!
        - **History** - this can be important on bigger issues that will stay open for a longer period of time
        - **Questions** - write down all questions and answers for this issue here
- Issues are not Documentation
    - the questions, answers and decisions must be transferred to the documentation *before* closing the related issue
- issues can be grouped by *Labels*
- i.e. `prio_high`, `type_bugfix`, `cat_quality`,…
- *Milestones* can be used to create time lockable packages of issues

General Flow for code changes
-----------------------------

1.  Create Issue.
1.  Generate Branch from Issue.
1.  Stay in that branch for your changes.
1.  Create Pull-Request for your branch.
1.  Review of the Pull-Request by another Developer.
1.  Merge of the Pull-Request into the test branch.
1.  After running all tests, the changes can be merged into the master branch.

Working with your IDE
=====================

- feel free to use `eclipse <https://eclipse.org>`__

.. Important:: check your IDE!

    - set default Encoding and line delimiter (most important on Windows!)
        - *Window → Preferences → General → Workspace*
            - Text file encoding: ``UTF-8``
            - New text file line delimiter: ``UNIX`` (``\n``)
    - use spaces for tabs, tab-width: 4 spaces
        - *Window → Preferences → General → Text Editors*
            - Display tab width: ``4``
            - [x] Insert spaces for tabs

Working with Java
=================

- new code should also bring the needed JUnit-Tests
    - got for a test coverage of 60% or more
- do not build cycles, never! (A uses B uses C uses A)
- pay attention to the metrics, check with…
    - `SonarLint <https://marketplace.eclipse.org/content/sonarlint>`__
    - `FindBugs <https://marketplace.eclipse.org/content/findbugs-eclipse-plugin>`__ or `SpotBugs <https://spotbugs.github.io/>`__
    - `Project Usus <https://marketplace.eclipse.org/content/project-usus>`__
    - …

Java programming
----------------

- in log4j2 use placeholders, i.e. ``LOGGER.debug("this is my error with param {}", param, e)``
- use ``TODO`` and ``FIXME`` in comments describing Todos and Fixmes o.O
    - i.e. ``//TODO exception XYZ thrown, needs to be catched``
