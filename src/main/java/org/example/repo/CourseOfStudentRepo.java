package org.example.repo;

import org.example.enity.CourseOfStudent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CourseOfStudentRepo extends JpaRepository<CourseOfStudent, Long> {
    boolean existsByUserIdAndCourseId(Long userId, Long courseId);
    CourseOfStudent findByUserIdAndCourseId(Long userId, Long courseId);
    List<CourseOfStudent> findByUserId(Long userId);
}
