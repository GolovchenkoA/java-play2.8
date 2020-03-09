package controllers;

import play.mvc.Controller;
import play.mvc.Result;

public class BaseAPIController extends Controller {

    public static final String VERSION = "1";

    public Result get1() {

        return ok("BaseAPIController ok" );
    }

    public Result get(String ver , String param) {

        if (!VERSION.equals(ver)) {
            return badRequest("Unknown api version");
        }



        return ok("request param: " + param);
    }
}
