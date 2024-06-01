package org.example.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@Route("/login")
@AnonymousAllowed
@PageTitle("Login")
public class LoginView extends BaseView {
    public LoginView() {
        super();
        setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        setAlignItems(FlexComponent.Alignment.CENTER);

        LoginForm loginForm = new LoginForm();
        loginForm.setAction("login");

        Button btnRegister = new Button("Sign up", event -> getUI().ifPresent(ui -> ui.navigate("/register")));

        add(new H1("Login"), loginForm, btnRegister);
    }
}
