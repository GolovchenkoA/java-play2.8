package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.inject.Inject;
import com.typesafe.config.Config;
import interceptors.annotations.Catch;
import models.Customer;
import models.CustomerBuilder;
import play.Logger;
import play.libs.Json;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Function;

import static java.lang.Thread.sleep;

@Catch
public class CustomerController extends Controller {

    @Inject
    HttpExecutionContext ec;

    final Logger.ALogger log = Logger.of("application");

    private static final CustomerBuilder builder = new CustomerBuilder();
    private static final AtomicLong idGen = new AtomicLong(1);
    private static final Map<Long, Customer> customers = new ConcurrentHashMap<>();

    private  static final Function<? super Customer, Result> getResponseCode = customer -> customer !=  null ? created(): badRequest();
    private static final Random r = new Random();

    private final Config config;

    @Inject
    public CustomerController(Config config) {
        this.config = config;
    }

    public Result params(String param1, String param2) {

        return ok(String.format("param1: %s param2: %s", param1, param2));
    }

    public CompletionStage<Result> exists(long id) {

        return CompletableFuture.supplyAsync(() -> {
            return customers.get(id) != null
                    ? ok("exists") : ok("not exists");
        }, ec.current());
    }

    public CompletionStage<Result> create(String name) {

        return CompletableFuture.supplyAsync(() -> {
            Customer customer = builder.setId(idGen.getAndIncrement())
                            .setName(name)
                            .build();

            customers.put(customer.getId(), customer);
            Logger.info("created1");
            log.info("Customer created2: " + customer.getId());
            log.debug("Customer created2 (debug): " + customer.getId());

            return getResponseCode.apply(customer);
        }, ec.current());

    }

    public Result get(long id) throws ExecutionException, InterruptedException {

        CompletableFuture<Result> future = CompletableFuture.supplyAsync(() -> {

            Customer customer = customers.get(id);



            if (r.nextInt(3) % 2 == 0) {
                try {
                    log.info("wait for id: " + id);
                    sleep(500);
                    log.info("wait for id finished: " + id);

                    //if (customer != null) {
                        customer.setTookTime("50");
                   // }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            if (customer == null) {
                return notFound();
            }

            JsonNode customerNode = Json.toJson(customer);
            return ok(customerNode);

        }, ec.current());

        return future.get();
    }
}
