package org.example.view;

import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;
import org.example.enity.Course;
import org.example.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;

@Route("/edit")
@RolesAllowed("ADMIN")
@PageTitle("Edit")
public class EditCourseView extends BaseView implements HasUrlParameter<Long> {
    private TextField textName;
    private TextField textAuthor;
    private TextArea textContent;

    private CourseService courseService;
    private Course course;

    @Autowired
    public EditCourseView(CourseService courseService) {
        super();
        this.courseService = courseService;
    }

    @Override
    public void setParameter(BeforeEvent beforeEvent, Long courseId) {
        course = courseService.getById(courseId);

        FormLayout formLayout = new FormLayout();

        textName = new TextField("Name:", course.getName(), "Enter name of course");
        textName.setRequired(true);
        textName.setWidthFull();
        textAuthor = new TextField("Author:", course.getAuthor(), "Enter author of course");
        textAuthor.setRequired(true);
        textAuthor.setWidthFull();
        textContent = new TextArea("Content:", course.getContent(), "Enter course content");
        textContent.setRequired(true);
        textContent.setWidthFull();
        textContent.setHeight(400, Unit.PIXELS);
        Button buttonAdd = new Button("Save", event -> saveCourse());
        buttonAdd.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        formLayout.add(
                textName,
                textAuthor,
                textContent,
                buttonAdd
        );

        formLayout.setColspan(textName, 2);
        formLayout.setColspan(textAuthor, 2);
        formLayout.setColspan(textContent, 2);
        formLayout.setColspan(buttonAdd, 2);

        add(formLayout);
    }

    private void saveCourse() {
        Course editCourse = new Course();
        editCourse.setId(course.getId());
        editCourse.setName(textName.getValue());
        editCourse.setAuthor(textAuthor.getValue());
        editCourse.setContent(textContent.getValue());
        courseService.saveCourse(editCourse);
        ViewUtils.showSuccessMessage("Course successfully saved");
        textName.clear();
        textAuthor.clear();
        textContent.clear();
    }
}
