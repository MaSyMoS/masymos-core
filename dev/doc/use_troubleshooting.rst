.. _use_troubleshooting:

***************
Troubleshooting
***************

.. include:: rst_directives.rst

.. contents::
    :local:
    :depth: 1

Windows: Problems with line breaks; bash scripts are not running
################################################################

- Error-Messages
    - something like ``cannot find  env bash\r``
- the MaSyMoS-Repositories use the Unix-Way of writing line breaks and UTF-8 encoding

.. Note:: Differences Unix/Windows line-breaks

    - Unix-Systems use the line-feed (LF, ``\n``) as line-break-character

    - Windows uses the carriage-return AND line-feed (CRLF, ``\r\n``) as line-break-characters (like an old typewriter)

.. Note:: Ways to deal with the differences

    - configure your git that way, that…

        - on checkout, all LF will become CRLF

        - on push, all CRLF will become LF

        - ⚠ this may lead to unexpected behaviour, especially with bash-scripts!

    - check out repositories without changes and only use editors, that preserve the Unix-line-breaks, like Notepad++, Atom, IntelliJ or Eclipse (you may need to configure it in some cases)

Solution
========

- you have to preserve the LF, please do not use CRLF - do not use Windows Notepad…; this means:
    - ``core.autocrlf=input``
    - ``core.safecrlf=true``
- if you are just using git
    - edit the setting ``core.autocrlf=input``, you can set it globally or per repository
        - options
            - ``true`` will change LF to CRLF on checkout and CRLF to LF on push
            - ``false`` will do nothing with the line endings
            - ``input`` will preserver LF on checkout and on push
        - `git documentation on AutoCrLf <https://www.git-scm.com/book/en/v2/Customizing-Git-Git-Configuration#_core_autocrlf>`__
        - check setting for current repository: :c_bash:`git config --list`
            - if the setting is not existing, the default value is used
        - set setting for current repository: :c_bash:`git config --add core.autocrlf input`
    - please check, that ``core.safecrlf=true``, you can set it globally or per repository
        - options
            - ``true`` this setting will check your files before a commit, if the operation is reversible; about on problems
            - ``warn`` this setting will check your files before a commit, if the operation is reversible; warn on problems
            - ``false`` this setting will NOT check your files before a commit; **do not use this**
        - `documentation on SafeCrLf <https://git-scm.com/docs/git-config#Documentation/git-config.txt-coresafecrlf>`__
        - check setting for current repository: :c_bash:`git config --list`
            - if the setting is not existing, the default value is used
        - set setting for current repository: :c_bash:`git config --add core.safecrlf true`
- if you use TortoiseGit
    - During installation on a Windows System the software asks how to handle Unix/Windows line-breaks. Default is "checkout windows style" and "check in Unix style". This causes trouble when running shell scripts on windows.
    - If you want to change the behaviour for a specific repository do the following:
        #. clone/create the desired repository
        #. right click on the directory, TortoiseGit -> Settings
        #. in the upcomming window click on Git (left)
        #. change "Config Source" to local
        #. change "AutoCrLf" to input
        #. change "SafeCrLf" to true
        #. delete all files in the directory except ".git" (this directory may be hidden)
        #. right click on the now empty directory, TortoiseGit -> Revert
        #. files are pulled again, containing only Unix line-breaks
