
package com.compilecrew.attendance.controller;

import com.compilecrew.attendance.entity.Student;
import com.compilecrew.attendance.repository.StudentRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/students")
@CrossOrigin(origins = "*")
public class StudentController {

    private final StudentRepository studentRepository;
    private final PasswordEncoder passwordEncoder;

    public StudentController(
            StudentRepository studentRepository,
            PasswordEncoder passwordEncoder) {
        this.studentRepository = studentRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerStudent(
            @RequestBody Map<String, String> data) {

        String password = data.get("password");
        String confirmPassword = data.get("confirmPassword");

        if (password == null || confirmPassword == null
                || !password.equals(confirmPassword)) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message", "Passwords do not match"));
        }

        String rollNumber = data.get("rollNumber");
        String email = data.get("email");

        if (rollNumber == null || email == null
                || rollNumber.isBlank() || email.isBlank()) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message", "Roll number and email are required"));
        }

        if (studentRepository.existsByRollNumber(rollNumber)) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message", "Roll number already registered"));
        }

        if (studentRepository.existsByEmail(email)) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message", "Email already registered"));
        }

        Student student = new Student();
        student.setName(data.get("name"));
        student.setRollNumber(rollNumber);
        student.setEmail(email);
        student.setDepartment(data.get("department"));
        student.setSection(data.get("section"));
        student.setPassword(passwordEncoder.encode(password));

        Student savedStudent = studentRepository.save(student);

        return ResponseEntity.ok(Map.of(
                "message", "Student registered successfully",
                "id", savedStudent.getId()
        ));
    }
    @PostMapping("/login")
public ResponseEntity<?> loginStudent(
        @RequestBody Map<String, String> data) {

    String rollNumber = data.get("rollNumber");
    String password = data.get("password");

    if (rollNumber == null || password == null
            || rollNumber.isBlank() || password.isBlank()) {
        return ResponseEntity.badRequest()
                .body(Map.of("message", "Please enter roll number and password"));
    }

    Student student = studentRepository.findByRollNumber(rollNumber);

    if (student == null ||
            !passwordEncoder.matches(password, student.getPassword())) {
        return ResponseEntity.status(401)
                .body(Map.of("message", "Invalid roll number or password"));
    }

    return ResponseEntity.ok(Map.of(
            "message", "Login successful",
            "id", student.getId(),
            "name", student.getName(),
            "rollNumber", student.getRollNumber(),
            "email", student.getEmail(),
            "department", student.getDepartment(),
            "section", student.getSection()
    ));
}
}
