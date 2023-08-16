package TaskManagementSystem.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import TaskManagementSystem.entity.Employee;
import TaskManagementSystem.service.EmployeeService;

@RestController
@RequestMapping("/employee")
@CrossOrigin(origins = "http://localhost:3000")
public class EmployeeController {
	@Autowired
	EmployeeService empServ;

	@GetMapping
	public ResponseEntity<?> getEmps() {
		return ResponseEntity.ok(this.empServ.findAll());
	}

	@PostMapping
	public ResponseEntity<?> addEmp(@Valid @RequestBody Employee theEmp, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			StringBuilder errors = new StringBuilder();
			bindingResult.getFieldErrors().forEach(error -> errors.append(error.getDefaultMessage()).append("; "));
			return ResponseEntity.badRequest().body(errors.toString());
		}
		Employee emp = this.empServ.save(theEmp);
		if (emp != null)
			return ResponseEntity.ok(emp);
		return ResponseEntity.badRequest().body("Email Already Exists");
	}

	@PostMapping("/{id}")
	public ResponseEntity<?> getEmp(@PathVariable int id) {
		Employee emp = this.empServ.findById(id);
		return ResponseEntity.ok(emp);
	}

	@DeleteMapping("/{id}")
	public String deleteEmp(@PathVariable int id) {
		this.empServ.deleteById(id);
		return "Document Deleted";
	}
}
