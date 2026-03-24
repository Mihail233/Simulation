package com.dobro.path;

import com.dobro.entity.*;

import java.util.HashSet;
import java.util.Set;

public class AllowedPathsProvider {
    private final Set<Class<? extends Entity>> allowedPaths = new HashSet<>();

    public AllowedPathsProvider() {
        createAllowedPaths();
    }

    private void createAllowedPaths() {
        for (AllowedPath allowedPath : AllowedPath.values()) {
            allowedPaths.add(allowedPath.getClazz());
        }
    }

    public Set<Class<? extends Entity>> getAllowedPaths() {
        return allowedPaths;
    }
}
