
package com.compilecrew.attendance.repository;

import com.compilecrew.attendance.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {

    boolean existsByRollNumber(String rollNumber);

    boolean existsByEmail(String email);

    Student findByRollNumber(String rollNumber);
}
