package org.example.repo;

import org.example.enity.Course;
import org.example.enity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepo extends JpaRepository<Course, Long> {
}
