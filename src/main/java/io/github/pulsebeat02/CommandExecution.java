package io.github.pulsebeat02;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public final class CommandExecution {

  public static final String YOUTUBE_DL_COMMAND;

  static {
    YOUTUBE_DL_COMMAND = "youtube-dl --skip-download --dump-json %s";
  }

  private final Runtime runtime;

  public CommandExecution() {
    this.runtime = Runtime.getRuntime();
  }

  public String getResult(final String input) throws IOException {

    final String cmd = YOUTUBE_DL_COMMAND.formatted(input);
    final Process process = runtime.exec(cmd);

    final StringBuilder builder = new StringBuilder();
    final BufferedReader stdInput =
        new BufferedReader(new InputStreamReader(process.getInputStream()));
    String s;
    while ((s = stdInput.readLine()) != null) {
      builder.append(s);
    }

    return builder.toString();
  }
}
