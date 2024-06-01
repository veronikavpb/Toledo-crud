package org.example.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.example.enity.Role;
import org.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

@Route("/register")
@AnonymousAllowed
@PageTitle("Registration")
public class RegisterView extends BaseView {

    @Autowired
    public RegisterView(UserService userService) {
        super();

        TextField textFullName = new TextField("FullName: ", "Your full name");
        textFullName.setRequired(true);

        TextField textLogin = new TextField("Login: ", "Your login");
        textLogin.setRequired(true);

        PasswordField textPassword = new PasswordField("Password: ", "Your password");
        textPassword.setRequired(true);

        PasswordField textPassword2 = new PasswordField("Confirm password: ", "Retype password");
        textPassword2.setRequired(true);

        Button btnRegister = new Button("Register", event -> {
            if (textFullName.getValue().isBlank()
                    || textLogin.getValue().isBlank()
                    || textPassword.getValue().isBlank()
                    || textPassword2.getValue().isBlank()) {
                ViewUtils.showErrorMessage("All fields are required");
                return;
            }
            if (!textPassword.getValue().equals(textPassword2.getValue())) {
                ViewUtils.showErrorMessage("Password and Confirm password should be the same");
                return;
            }
            if (userService.addNewUser(textFullName.getValue(), textLogin.getValue(), textPassword.getValue(), Role.ROLE_STUDENT.name())) {
                ViewUtils.showSuccessMessage("User successfully add");
            } else {
                ViewUtils.showErrorMessage("User with this email already eists");
            }
        });
        btnRegister.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        Button btnBackToLogin = new Button("Back to Sign in", event -> getUI().ifPresent(ui -> ui.navigate("/login")));

        FormLayout formLayout = new FormLayout();
        formLayout.add(new H3("Registration"),
                textFullName,
                textLogin,
                textPassword,
                textPassword2,
                new HorizontalLayout(btnRegister, btnBackToLogin)
        );
        formLayout.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 1));

        setAlignSelf(Alignment.CENTER, formLayout);
        setJustifyContentMode(JustifyContentMode.START);
        add(formLayout);
    }
}
