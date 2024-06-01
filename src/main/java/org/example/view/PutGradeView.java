package org.example.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;
import org.example.enity.Course;
import org.example.enity.User;
import org.example.service.CourseService;
import org.example.service.UserService;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Route("/put-grade")
@RolesAllowed("ADMIN")
@PageTitle("Put Grade")
public class PutGradeView extends BaseView {
    private Select<User> selectUser;
    private Select<Course> selectCourse;
    private Select<Integer> selectResult;

    private UserService userService;
    private CourseService courseService;

    public PutGradeView(UserService userService, CourseService courseService) {
        super();
        this.userService = userService;
        this.courseService = courseService;

        add(new H1("Put a Student's Grade"));

        selectUser = new Select<>();
        selectUser.setLabel("Select student:");
        selectUser.setItems(userService.getAllStudents());
        selectUser.setEmptySelectionAllowed(true);
        selectUser.setEmptySelectionCaption("Select student");
        selectUser.setItemLabelGenerator(user -> user != null ? user.getFullname() : "");

        selectCourse = new Select<>();
        selectCourse.setLabel("Select course:");
        selectCourse.setItems(courseService.getAll());
        selectCourse.setEmptySelectionAllowed(true);
        selectCourse.setEmptySelectionCaption("Select course");
        selectCourse.setItemLabelGenerator(user -> user != null ? user.getName() : "");

        selectResult = new Select<>();
        selectResult.setLabel("Select result:");
        selectResult.setItems(IntStream.rangeClosed(1, 20).boxed().collect(Collectors.toList()));
        selectResult.setEmptySelectionAllowed(true);
        selectResult.setEmptySelectionCaption("Select course");
        selectResult.setItemLabelGenerator(user -> user != null ? user.toString() : "");

        Button buttonSignUp = new Button("Sign Up", e -> putResult());
        buttonSignUp.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        add(
                selectUser,
                selectCourse,
                selectResult,
                buttonSignUp
        );
    }

    private void putResult() {
        if (selectUser.getValue() == null) {
            ViewUtils.showErrorMessage("Select a user");
            return;
        }
        if (selectCourse.getValue() == null) {
            ViewUtils.showErrorMessage("Select a course");
            return;
        }
        if (selectResult.getValue() == null) {
            ViewUtils.showErrorMessage("Select a result");
            return;
        }
        boolean result = courseService.putAGrade(selectUser.getValue(), selectCourse.getValue(), selectResult.getValue());
        if (result) {
            ViewUtils.showSuccessMessage("Grade set successfully");
            selectUser.clear();
            selectCourse.clear();
            selectResult.clear();
            return;
        }
        ViewUtils.showErrorMessage("Grade dose not set");
    }
}
