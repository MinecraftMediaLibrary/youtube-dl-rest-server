package io.github.pulsebeat02;

import spark.Spark;

public final class RestServer {

  private final CommandExecution executor;

  public RestServer() {
    this.executor = new CommandExecution();
    this.run();
  }

  public void run() {
    Spark.get("/info", new RequestHandler(this));
    while (true) {}
  }

  public CommandExecution getExecutor() {
    return executor;
  }

  public static void main(String[] args) {
    System.out.println("API Link: http://localhost:4567/info?url=URL_HERE");
    new RestServer();
  }
}
