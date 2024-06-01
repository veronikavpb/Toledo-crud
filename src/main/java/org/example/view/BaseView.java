package org.example.view;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.RouterLink;
import org.example.enity.Role;
import org.example.enity.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public abstract class BaseView extends VerticalLayout {

    public BaseView() {
        add(createNavBar());
    }

    private AppLayout createNavBar() {
        AppLayout appLayout = new AppLayout();
        Tabs tabs;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
         if (authentication.getPrincipal() instanceof User user){
            boolean isAdmin = user.getRoles().contains(Role.ROLE_ADMIN);
            if (isAdmin) {
                tabs = new Tabs(
                        new Tab(new RouterLink("Courses", MainView.class)),
                        new Tab(new RouterLink("Add Course", AddCourseView.class)),
                        new Tab(new RouterLink("Sign Up", SignUpForCourseView.class)),
                        new Tab(new RouterLink("Put Grade", PutGradeView.class)),
                        new Tab(new Button("Log Out", e -> logout())));
            } else {
                tabs = new Tabs(
                        new Tab(new RouterLink("Courses", MainView.class)),
                        new Tab(new Button("Log Out", e -> logout())));
            }
        } else {
            tabs = new Tabs(
                    new Tab(new RouterLink("Log In", LoginView.class)));
        }
        tabs.setOrientation(Tabs.Orientation.HORIZONTAL);
        appLayout.addToNavbar(tabs);
        return appLayout;
    }

    private void logout() {
        UI.getCurrent().getSession().close();
        UI.getCurrent().getPage().setLocation("/login");
    }
}
