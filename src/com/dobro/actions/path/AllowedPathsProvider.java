package com.dobro.actions.path;

import com.dobro.models.*;

import java.util.HashSet;

public class AllowedPathsProvider {
    private final HashSet <Class<? extends Entity>> allowedPaths = new HashSet<>();

    public AllowedPathsProvider() {
        createAllowingPaths();
    }

    public void createAllowingPaths() {
        for (AllowedPath allowedPath: AllowedPath.values()) {
            allowedPaths.add(allowedPath.getClazz());
        }
    }

    public HashSet <Class<? extends Entity>> getAllowedPaths() {
        return allowedPaths;
    }

    public void addAllowedPath(Class<? extends Entity> allowedPath) {
        this.allowedPaths.add(allowedPath);
    }
}
