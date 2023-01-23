/**
 * MIT License
 * <p>
 * Copyright (c) 2023 Brandon Li
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package io.github.pulsebeat02;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Pattern;

public final class CommandExecution {

    public static final String YOUTUBE_DL_COMMAND;
    public static final Pattern CHECK_ATTACK;

    static {
        YOUTUBE_DL_COMMAND = "youtube-dl --skip-download --dump-json %s";
        CHECK_ATTACK = Pattern.compile("(;)|(&&)");
    }

    private final Runtime runtime;

    public CommandExecution() {
        this.runtime = Runtime.getRuntime();
    }

    public String getResult(final String input) throws IOException {

        if (!validateInput(input)) {
            return "Invalid URL: %s".formatted(input);
        }

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

    public boolean validateInput(final String input) {
        return !CHECK_ATTACK.matcher(input).find();
    }
}
