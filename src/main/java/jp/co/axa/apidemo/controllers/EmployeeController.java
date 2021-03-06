package jp.co.axa.apidemo.controllers;

import jp.co.axa.apidemo.entities.Employee;
import jp.co.axa.apidemo.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    public void setEmployeeService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/employees")
    public List<Employee> getEmployees() {
        List<Employee> employees = employeeService.retrieveEmployees();
        return employees;
    }

    @GetMapping("/employees/{employeeId}")
    public Employee getEmployee(@PathVariable(name="employeeId")Long employeeId) {
        return employeeService.getEmployee(employeeId);
    }

    @PostMapping("/employees")
    public String saveEmployee(Employee employee){
        employeeService.saveEmployee(employee);
        System.out.println("Employee Saved Successfully");
        return "Employee Saved Successfully";
    }

    @DeleteMapping("/employees/{employeeId}")
    public String deleteEmployee(@PathVariable(name="employeeId")Long employeeId){
        if (employeeService.getEmployee(employeeId) != null) {
            employeeService.deleteEmployee(employeeId); 
            System.out.println("Employee Deleted Successfully");
            return "Employee Deleted Successfully";
        } 
        // return error message if requested id does not exist
        else {
            System.out.println("Requested User Does Not Exist");
            return "Requested User Does Not Exist";
        }
    }

    @PutMapping("/employees/{employeeId}")
    public String updateEmployee(@RequestBody Employee employee, @PathVariable(name="employeeId")Long employeeId){
        
        // create an Employee object to map the updated data
        Employee emp = employeeService.getEmployee(employeeId);
        if (emp != null) {
            if (employee.getName() != null) emp.setName(employee.getName());
            if (employee.getSalary() != null) emp.setSalary(employee.getSalary());
            if (employee.getDepartment() != null) emp.setDepartment(employee.getDepartment());
            employeeService.updateEmployee(emp);
            System.out.println("Employee Updated Successfully");
            return "Employee Updated Successfully";
        } 
        // save a new employee if requested id does not exist
        else {
            employeeService.saveEmployee(employee);
            System.out.println("Employee Saved Successfully");
            return "Employee Saved Successfully";
        }

    }

}
