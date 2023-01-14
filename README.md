## Workshop 13
- App is a form to collect user information and write onto file.
- Each user is given a generated 8 character hex string as id.
- Name of file written is id.
- Can retrieve information of id through endpoint `/contact/<id>` which will display all details saved in that `<id>` file.
- Endpoint `/contact/list` will list out hyperlinks of each contact that was saved


### Run spring boot app

- For all OS
- `dataDir` is required
- `/opt/tmp/data` is the directory to save the data file.

```mvn spring-boot:run -Dspring-boot.run.arguments=--dataDir=/opt/tmp/data```