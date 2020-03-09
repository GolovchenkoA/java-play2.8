package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.inject.Inject;
import interceptors.annotations.Catch;
import models.Customer;
import models.CustomerBuilder;
import play.Logger;
import play.libs.Json;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.Controller;
import play.mvc.Result;
import store.CustomerStore;

import javax.inject.Singleton;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Function;

@Singleton
@Catch
public class CustomerController extends Controller {

    final Logger.ALogger log = Logger.of("application");

    private static final CustomerBuilder builder = new CustomerBuilder();
    private static final Function<? super Customer, Result> getResponseCode = customer -> customer !=  null ? created(): badRequest();

    private final HttpExecutionContext ec;
    private final CustomerStore customerStore;

    @Inject
    public CustomerController(CustomerStore customerStore, HttpExecutionContext ec) {
        this.customerStore = customerStore;
        this.ec = ec;
    }

    public Result index() {
        return ok(views.html.index.render());
    }

    public Result params(String param1, String param2) {

        return ok(String.format("param1: %s param2: %s", param1, param2));
    }

    public CompletionStage<Result> exists(long id) {

        return CompletableFuture.supplyAsync(() -> {
            return customerStore.get(id) != null
                    ? ok("exists") : ok("not exists");
        }, ec.current());
    }

    public CompletionStage<Result> create(String name) {

        return CompletableFuture.supplyAsync(() -> {
            Customer customer = builder.setName(name).build();

            customerStore.updateOrCreate(customer);
            Logger.info("created1");
            log.info("Customer created2: " + customer.getId());
            log.debug("Customer created2 (debug): " + customer.getId());

            return getResponseCode.apply(customer);
        }, ec.current());

    }

    public Result get(long id) throws ExecutionException, InterruptedException {

        CompletableFuture<Result> future = CompletableFuture.supplyAsync(() -> {

            Customer customer = customerStore.get(id);

            if (customer == null) {
                return notFound();
            }

            JsonNode customerNode = Json.toJson(customer);
            return ok(customerNode);

        }, ec.current());

        return future.get();
    }
}
