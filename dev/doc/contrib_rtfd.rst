***********
ReadTheDocs
***********

The MaSyMoS GitHub projects are in an organisation → so the documentation is added manually to https://readthedocs.org.

Steps to reproduce procedure
############################

#. Create RTD-Project, add project Link manually.

#. get webhook- URL from ``Admin → Integrations → GitHub incoming webhook``

    - url stating with ``https://readthedocs.org/api/v2/webhook/``

#. Add new webhook in GitHub ``project → settings → webhock → add``

    - payload → *add url here*
    - type: application/json
    - secret: blank
    - ssl true
    - events → individual
        - Branch or tag creation
        - Branch or tag deletion
        - Pull requests
        - Pushes
    - active true

#. check for 200 on RTD

Configuration
#############

- Add Maintainers (Admin → Maintainers)
- Disable paid advertising (Admin → Advertising)
