package org.example.view;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;
import org.example.CourseDto;
import org.example.enity.Course;
import org.example.enity.Role;
import org.example.enity.User;
import org.example.service.CourseService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

import static com.flowingcode.vaadin.addons.fontawesome.FontAwesome.Regular.*;


@Route("/")
@RolesAllowed({"ADMIN", "STUDENT"})
@PageTitle("Courses")
public class MainView extends BaseView {
    private CourseService courseService;
    private long userId;

    public MainView(CourseService courseService) {
        super();
        this.courseService = courseService;

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        userId = ((User) authentication.getPrincipal()).getId();
        boolean isAdmin = ((User) authentication.getPrincipal()).getRoles().contains(Role.ROLE_ADMIN);

        add(
                isAdmin
                        ? makeAdminGrid()
                        : makeStudentGrid()
        );
    }

    private Grid<CourseDto> makeStudentGrid() {
        List<CourseDto> courses = courseService.getAllByStudentId(userId);
        Grid<CourseDto> grid = new Grid<>(courses);
        grid.addComponentColumn(course -> new Text(String.valueOf(courses.indexOf(course) + 1))).setHeader("Id").setFlexGrow(0);
        grid.addColumn(CourseDto::getName).setHeader("Name");
        grid.addColumn(CourseDto::getAuthor).setHeader("Author");
        grid.addColumn(courseDto -> courseDto.getResult() != null ? courseDto.getResult() : "n/a").setHeader("Result");
        grid.addComponentColumn(course -> new Button(EYE.create(), event -> {
            getUI().ifPresent(ui -> ui.navigate(CourseView.class, course.getId()));
        })).setHeader("View").setFlexGrow(0);
        return grid;
    }

    private Grid<Course> makeAdminGrid() {
        List<Course> courses = courseService.getAll();
        Grid<Course> grid = new Grid<>(courses);
        grid.addComponentColumn(course -> new Text(String.valueOf(courses.indexOf(course) + 1))).setHeader("Id").setFlexGrow(0);
        grid.addColumn(Course::getName).setHeader("Name");
        grid.addColumn(Course::getAuthor).setHeader("Author");
        grid.addComponentColumn(course -> new Button(EYE.create(), event -> {
            getUI().ifPresent(ui -> ui.navigate(CourseView.class, course.getId()));
        })).setHeader("View").setFlexGrow(0);
        grid.addComponentColumn(course -> new Button(PEN_TO_SQUARE.create(), event -> {
            getUI().ifPresent(ui -> ui.navigate(EditCourseView.class, course.getId()));
        })).setHeader("Edit").setFlexGrow(0);
        grid.addComponentColumn(course -> new Button(TRASH_CAN.create(), event -> {
            courseService.remove(course.getId());
            courses.remove(course);
            grid.getDataProvider().refreshAll();
        })).setHeader("Delete").setFlexGrow(0);
        return grid;
    }
}
