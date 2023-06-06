package org.trivial.path;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PathHelper {

    public static void appendToFile(String path, String s) {
        try (var fw = new FileWriter(path, true);
                var bw = new BufferedWriter(fw);
                var out = new PrintWriter(bw)) {
            out.println(s);
        } catch (IOException e) {
            Logger.getLogger(PathHelper.class.getName())
                    .log(Level.SEVERE, e.getMessage());
        }
    }

    public static String envPath() {
        return System.getenv("Path").trim();
    }

    public static String envPathForMsys2() {
        var s = envPath().replace("C:\\", "/c/")
                .replace(";", ":")
                .replace("\\", "/")
                .replace("(", "\\(")
                .replace(")", "\\)")
                .replace(" ", "\\ ");
        if (s.endsWith(":")) {
            s = s.substring(0, s.length() - 1);
        }
        return s;
    }
    
    public static String envPathForMsys2ExportDirective() {
        return "export PATH=$PATH:" + envPathForMsys2();
    }

    public static void main(String[] args) {
        var home = System.getProperty("user.home");
        var name = System.getProperty("user.name");
        appendToFile(
                home + "\\scoop\\apps\\msys2\\current\\home\\" + name + "\\.bashrc",
                "\n\n" + envPathForMsys2ExportDirective());
    }

}
