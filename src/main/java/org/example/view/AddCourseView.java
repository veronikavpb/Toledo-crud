package org.example.view;

import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;
import org.example.enity.Course;
import org.example.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;

@Route("/add")
@RolesAllowed("ADMIN")
@PageTitle("Add")
public class AddCourseView extends BaseView {
    private TextField textName;
    private TextField textAuthor;
    private TextArea textContent;

    private CourseService courseService;

    @Autowired
    public AddCourseView(CourseService courseService) {
        super();
        this.courseService = courseService;

        FormLayout formLayout = new FormLayout();

        textName = new TextField("Name:", "Enter name of course");
        textName.setRequired(true);
        textName.setWidthFull();
        textAuthor = new TextField("Author:", "Enter author of course");
        textAuthor.setRequired(true);
        textAuthor.setWidthFull();
        textContent = new TextArea("Content:", "Enter course content");
        textContent.setRequired(true);
        textContent.setWidthFull();
        textContent.setHeight(400, Unit.PIXELS);
        Button buttonAdd = new Button("Add", event -> saveCourse());
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
        Course course = new Course();
        course.setName(textName.getValue());
        course.setAuthor(textAuthor.getValue());
        course.setContent(textContent.getValue());
        courseService.saveCourse(course);
        ViewUtils.showSuccessMessage("Course successfully added");
        textName.clear();
        textAuthor.clear();
        textContent.clear();
    }
}
