package controllers;

import play.mvc.Result;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import static play.mvc.Results.ok;

public class Statistics {

    private static final Map<String, AtomicInteger> counters = new ConcurrentHashMap<>();

    public static void incrementCall(String uri) {
        counters.computeIfAbsent(uri, (key) -> new AtomicInteger(0)).incrementAndGet();
    }

    public Result info() {
        return ok(views.html.statistics.render(counters));
    }
}
