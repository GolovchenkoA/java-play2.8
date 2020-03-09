package interceptors;

import controllers.Statistics;
import play.mvc.Action;
import play.mvc.Http;
import play.mvc.Result;
import utils.ExceptionNotificator;

import java.util.concurrent.CompletionStage;

public class CatchAction extends Action.Simple {


    @Override
    public CompletionStage<Result> call(Http.Request req) {

        Statistics.incrementCall(req.uri());

        try {
            ExceptionNotificator.notify(req.uri());
            return delegate.call(req);
        } catch (Throwable e) {
            ExceptionNotificator.notify(e);
            throw new RuntimeException(e);
        }
    }


}
