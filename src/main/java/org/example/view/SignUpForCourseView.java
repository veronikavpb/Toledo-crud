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
import org.springframework.beans.factory.annotation.Autowired;

@Route("/sign-up")
@RolesAllowed("ADMIN")
@PageTitle("Sign Up")
public class SignUpForCourseView extends BaseView {
    private Select<User> selectUser;
    private Select<Course> selectCourse;

    private UserService userService;
    private CourseService courseService;

    @Autowired
    public SignUpForCourseView(UserService userService, CourseService courseService) {
        super();
        this.userService = userService;
        this.courseService = courseService;

        add(new H1("Sign Up Student for Course"));

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

        Button buttonSignUp = new Button("Sign Up", e -> signUp());
        buttonSignUp.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        add(
            selectUser,
            selectCourse,
            buttonSignUp
        );
    }

    private void signUp() {
        if (selectUser.getValue() == null) {
            ViewUtils.showErrorMessage("Select a user");
            return;
        }
        if (selectCourse.getValue() == null) {
            ViewUtils.showErrorMessage("Select a course");
            return;
        }
        boolean result = courseService.registerUserToCourse(selectUser.getValue(), selectCourse.getValue());
        if (result) {
            ViewUtils.showSuccessMessage("User successfully signed up for course");
            selectUser.clear();
            selectCourse.clear();
            return;
        }
        ViewUtils.showErrorMessage("User already has this course");
    }
}
