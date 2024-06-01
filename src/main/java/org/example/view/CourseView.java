package org.example.view;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;
import org.example.enity.Course;
import org.example.repo.CourseRepo;

import java.util.NoSuchElementException;

@Route("/course")
@RolesAllowed({"STUDENT", "ADMIN"})
@PageTitle("Course")
public class CourseView extends BaseView implements HasUrlParameter<Long> {
    private CourseRepo courseRepo;

    public CourseView(CourseRepo courseRepo) {
        super();
        this.courseRepo = courseRepo;
    }

    @Override
    public void setParameter(BeforeEvent beforeEvent, Long courseId) {
        Course course = courseRepo.findById(courseId).orElseThrow(() -> new NoSuchElementException("Course not found"));

        add(
                new H1("Course info"),
                createField("Name: ", course.getName()),
                createField("Author: ", course.getAuthor()),
                createField("Content: ", course.getContent())
        );

    }

    private Div createField(String key, String value) {
        return new Div(
            new Span(key),
            new Span(value)
        );
    }
}
