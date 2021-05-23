### How to use this spring-boot project

- Install packages with `mvn package`
- Run `mvn spring-boot:run` for starting the application (or use your IDE)

Application (with the embedded H2 database) is ready to be used ! You can access the url below for testing it :

- Swagger UI : http://localhost:8080/swagger-ui.html
- H2 UI : http://localhost:8080/h2-console

> Don't forget to set the `JDBC URL` value as `jdbc:h2:mem:testdb` for H2 UI.

### Instructions

- download the zip file of this project
- create a repository in your own github named 'java-challenge'
- clone your repository in a folder on your machine
- extract the zip file in this folder
- commit and push

- Enhance the code in any ways you can see, you are free! Some possibilities:
  - Add tests
  - Change syntax
  - Protect controller end points
  - Add caching logic for database calls
  - Improve doc and comments
  - Fix any bug you might find
- Edit readme.md and add any comments. It can be about what you did, what you would have done if you had more time, etc.
- Send us the link of your repository.

#### Restrictions

- use java 8

#### What we will look for

- Readability of your code
- Documentation
- Comments in your code
- Appropriate usage of spring boot
- Appropriate usage of packages
- Is the application running as expected
- No performance issues

#### Your experience in Java

- I'm new to Java and I spent some time setting up Maven for development. Overall the API server has a similar structure as C# and dot-net, which I have used before.

## Updates

- fixed logic in PUT request such that it updates selected user with provided info only
- fixed getEmployee implementation such that PUT request can work when the requested user ID does not exist (PUT shall become POST when requested user does not exist)
- added text return to client
- added API end-points documentation

## API End-points Documentation

### GET (list)
End-point: `/api/v1/employees`

Returns a list of all employees.

Sample output:

```
[
    {
        "id": 1,
        "name": "Jeff",
        "salary": 100000,
        "department": "IT"
    },
    {
        "id": 2,
        "name": null,
        "salary": 120000,
        "department": "HR"
    }
]
```

### GET (single)

End-point: `/api/v1/employees/{employeeID}`

Get information of one employee by employee ID. Returns nothing if the employee ID does not exist.

Sample output:

```
{
    "id": 1,
    "name": "Jeff",
    "salary": 100000,
    "department": "IT"
}
```

### POST 

End-point: `/api/v1/employees?name={name}&salary={salary}&department={department}`

Add one employee by providing information in name, salary and department. Name and department should be provided in string; salary should be provided in number.

All parameters are not required, if not provided, the column will be filled with `null`. User ID will be auto genegated.

### DELETE 

End-point: `/api/v1/employees/{employeeID}`

Delete an employee by employee ID. Return warning if the provided employee ID does not exist.

### PUT 

End-point: `/api/v1/employees/{employeeID}`

Update employee information by given employee ID and details. Details should be provided in request body in the following format:

```
{
  "name":{new name}, // optional
  "department":{new department}, // optional
  "salary":{new salary} // optional
}
```

A sample body would be:

```
{
    "department":"HR",
    "salary":120000
}
```

Only column with information provided will be updated. If the given user ID does not exist, a new user with the given details will be added.
