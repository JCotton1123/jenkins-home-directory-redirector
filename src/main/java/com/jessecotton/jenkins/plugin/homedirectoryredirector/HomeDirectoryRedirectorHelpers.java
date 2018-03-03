package com.jessecotton.jenkins.plugin.homedirectoryredirector;

import hudson.Extension;
import hudson.FilePath;
import hudson.Plugin;
import hudson.model.*;
import jenkins.model.Jenkins;

public class HomeDirectoryRedirectorHelpers {

    public static boolean isUnixRun(Run run) {

        Executor executor = run.getExecutor();
        Computer computer = executor.getOwner();

        return computer.isUnix();
    }

    public static String getWorkspaceForRun(Run run) {

        Executor executor = run.getExecutor();
        Computer computer = executor.getOwner();

        FilePath workspace = executor.getCurrentWorkspace();
        if (workspace != null) {
            return workspace.toString();
        }

        Node node = computer.getNode();
        if (node != null) {
            Job<?,?> j = run.getParent();
            if (j instanceof TopLevelItem) {
                FilePath p = node.getWorkspaceFor((TopLevelItem) j);
                if (p != null) {
                    return p.toString();
                }
            }
        }

        throw new RuntimeException("Couldn't determine workspace for run");
    }
}
