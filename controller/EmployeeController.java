package com.tinku.SpringBootApp.controller;

import com.tinku.SpringBootApp.exception.ResourceNotFoundException;
import com.tinku.SpringBootApp.model.Employee;
import com.tinku.SpringBootApp.repository.EmployeeRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/")
public class EmployeeController {
    @Autowired
    private EmployeeRepository employeeRepository;

    //get employees
    @GetMapping("/employees")
    public List<Employee> getAllEmployee(){
        return this.employeeRepository.findAll();
    }

    //get employee by id
    @GetMapping("/employees/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable(value = "id") Long employeeId)
            throws ResourceNotFoundException {
        Employee employee=employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " +employeeId));
        return ResponseEntity.ok().body(employee);
    }

    //save employee
    @PostMapping("/employees")
    public Employee creatEmployee(@RequestBody Employee employee){
        return this.employeeRepository.save(employee);
    }
    //update employee
    @PutMapping("employees/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable(value = "id") Long employeeId,
                                                   @Valid @RequestBody Employee employeeDetials) throws ResourceNotFoundException {
        Employee employee=employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " +employeeId));
        employee.setEmail(employeeDetials.getEmail());
        employee.setFirstName(employeeDetials.getFirstName());
        employee.setLastName(employeeDetials.getLastName());
        return ResponseEntity.ok(this.employeeRepository.save(employee));
    }
    //delete employee
    @DeleteMapping("employees/{id}")
    public Map<String, Boolean> deleteEmployee(@PathVariable(value = "id") Long employeeId) throws ResourceNotFoundException {
        Employee employee=employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " +employeeId));
        this.employeeRepository.delete(employee);
        Map<String, Boolean> response =new HashMap<>();
        response.put("deleted",Boolean.TRUE);
        return response;

    }
}
