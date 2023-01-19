/**
 * MIT License
 *
 * Copyright (c) 2023 Brandon Li
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
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
