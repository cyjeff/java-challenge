### How I used this spring-boot project

- Installed packages with `mvn package`
- Ran `mvn spring-boot:run` for starting the application
- Ran test with `mvn test`

### My experience in Java

- I'm new to Java and I spent some time setting up Maven for development. Overall the API server has a similar structure as C# and dot-net, which I have once used before. The test suite is complete new to me and I did struggled to make everything work as expected.

## My Updates on the Project (Commit history)

- fixed logic in PUT request such that it updates selected user with provided info only
- fixed getEmployee implementation such that PUT request can work when the requested user ID does not exist (PUT shall become POST when requested user does not exist)
- added text message return to client
- added API end-points documentation
- added unit testing for all end-points

## TODO

I struggled a lot with the unit testing tool and did not have a very robust design currently. If I had more time I would try to make each test independant by initiaiting a new mockMvc and do data seeding everytime.

Also, tests should execute on not only the controller, but also the repository as well. I would like to try using Bean to create a mock repository and test on both controller and repository.

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
