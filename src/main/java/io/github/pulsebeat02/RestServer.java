package io.github.pulsebeat02;

import spark.Spark;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public final class RestServer {

  private final CommandExecution executor;

  public RestServer() throws IOException {
    this.executor = new CommandExecution();
    this.run();
  }

  public void run() throws IOException {
    Spark.get("/info", new RequestHandler(this));
    this.handleShutdown();
  }

  public void handleShutdown() throws IOException {
    try (final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
      while (!reader.readLine().equals("stop")) {}
      System.out.println("Stopping Server...");
      Spark.stop();
    }
  }

  public CommandExecution getExecutor() {
    return this.executor;
  }

  public static void main(final String[] args) throws IOException {
    System.out.println("Server Link: http://localhost:4567/info?url=URL_HERE");
    new RestServer();
  }
}
