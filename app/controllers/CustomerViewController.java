package controllers;

import com.google.inject.Inject;
import com.typesafe.config.Config;
import interceptors.annotations.Catch;
import models.Customer;

import play.data.Form;
import play.data.FormFactory;
import play.i18n.MessagesApi;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import store.CustomerStore;
import views.html.details;

import javax.inject.Singleton;

import java.util.Collections;

import static play.libs.Scala.asScala;

@Singleton
@Catch
public class CustomerViewController extends Controller {

    @Inject
    private HttpExecutionContext ec;

    private final Config config;
    private final CustomerStore customerStore;
    private final Form<Customer> customerForm;
    private MessagesApi messagesApi;

    @Inject
    public CustomerViewController(CustomerStore customerStore,  Config config, FormFactory formFactory, MessagesApi messagesApi) {
        this.customerForm =  formFactory.form(Customer.class);
        this.customerStore = customerStore;
        this.config = config;
        this.messagesApi = messagesApi;
    }


    public Result all(Http.Request request) {
        return ok(views.html.all.render(asScala(customerStore.getAll()), customerForm, request, messagesApi.preferred(request)));
    }

    // todo: uncomment
//    public Result details(String id) {
//        final Customer customer = customerStore.get(Long.parseLong(id));
//        if (customer == null) {
//            return notFound(String.format("Customer %s does not exist.", id));
//        }
//        Form<Customer> filledForm = customerForm.fill(customer);
//        return ok(views.html.details.render(filledForm));
//    }

    public Result createCustomer(Http.Request request) {

        Form<Customer> boundForm = customerForm.bindFromRequest(request, "name", "spend");

        //todo: uncomment
//        if (customerForm.hasErrors()) {
//            return badRequest(customerStore.getAll(), views.html.all.render(boundForm), request, messagesApi.preferred(request)).
//                    flashing("error", "Please correct the form below.");
//        }

        customerStore.updateOrCreate(boundForm.get());

        return redirect(routes.CustomerViewController.all()).flashing("success",
                String.format("Successfully added customer %s", boundForm.get()));
    }

    public Result newCustomer(Http.Request request) {
        return ok(views.html.all.render(asScala(customerStore.getAll()), customerForm, request, messagesApi.preferred(request)));
    }
}
