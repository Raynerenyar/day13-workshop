## Workshop 13
1. App is a form to collect user information and write onto file.
2. Each user is given a generated 8 character hex string as id.
3. Name of file written is id.
4. Can retrieve information of id through endpoint `/contact/<id>`
5. Endpoint `/contact/list` will list out links of each contact that was saved


- Run spring boot app

For all OS. `dataDir` is required. `/opt/tmp/data` is the directory to save the data file.
```
mvn spring-boot:run -Dspring-boot.run.arguments=--dataDir=/opt/tmp/data
```