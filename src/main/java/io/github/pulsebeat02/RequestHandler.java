package io.github.pulsebeat02;

import spark.Request;
import spark.Response;
import spark.Route;

public record RequestHandler(RestServer server) implements Route {

  @Override
  public Object handle(final Request request, final Response response) throws Exception {

    final String ip = request.ip();
    final int port = request.port();
    final String host = request.host();

    System.out.printf("Serving request from %s:%d on %s%n", ip, port, host);

    final CommandExecution executor = server.getExecutor();
    final String url = request.queryParams("url");
    final String output = executor.getResult(url);

    response.status(200);
    response.type("application/json");
    response.body(output);

    return output;
  }
}
