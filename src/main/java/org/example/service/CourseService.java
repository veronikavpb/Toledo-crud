package org.example.service;

import jakarta.transaction.Transactional;
import org.example.CourseDto;
import org.example.enity.Course;
import org.example.enity.CourseOfStudent;
import org.example.enity.User;
import org.example.repo.CourseOfStudentRepo;
import org.example.repo.CourseRepo;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@Transactional
public class CourseService {
    private final Type courseDtoListType = new TypeToken<List<CourseDto>>() {}.getType();
    private CourseRepo courseRepo;
    private CourseOfStudentRepo courseOfStudentRepo;
    private ModelMapper modelMapper;

    @Autowired
    public CourseService(CourseRepo courseRepo, CourseOfStudentRepo courseOfStudentRepo, ModelMapper modelMapper) {
        this.courseRepo = courseRepo;
        this.courseOfStudentRepo = courseOfStudentRepo;
        this.modelMapper = modelMapper;
    }

    public List<Course> getAll() {
        return courseRepo.findAll();
    }

    public List<CourseDto> getAllByStudentId(Long studentId) {
        List<CourseDto> courseDtos = courseOfStudentRepo.findByUserId(studentId).stream()
                .map(cos -> {
                    CourseDto courseDto = modelMapper.map(cos.getCourse(), CourseDto.class);
                    courseDto.setResult(cos.getResult());
                    return courseDto;
                })
                .collect(Collectors.toList());
        return courseDtos;
    }

    public Course getById(Long id) {
        return courseRepo.findById(id).orElseThrow(() -> new NoSuchElementException("Course not found"));
    }

    public CourseDto saveCourse(Course course) {
        Course newCourse = courseRepo.save(course);
        return modelMapper.map(newCourse, CourseDto.class);
    }

    public boolean registerUserToCourse(User user, Course course) {
        if (!courseOfStudentRepo.existsByUserIdAndCourseId(user.getId(), course.getId())) {
            CourseOfStudent courseOfStudent = new CourseOfStudent();
            courseOfStudent.setUser(user);
            courseOfStudent.setCourse(course);
            courseOfStudentRepo.save(courseOfStudent);
            return true;
        }
        return false;
    }

    public boolean putAGrade(User user, Course course, Integer result) {
        CourseOfStudent cos = courseOfStudentRepo.findByUserIdAndCourseId(user.getId(), course.getId());
        if (cos != null) {
            cos.setResult(result);
            courseOfStudentRepo.save(cos);
            return true;
        }
        return false;
    }

    public void remove(Long id) {
        courseRepo.deleteById(id);
    }
}
