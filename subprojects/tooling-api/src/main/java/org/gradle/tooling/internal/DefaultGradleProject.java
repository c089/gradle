/*
 * Copyright 2011 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.gradle.tooling.internal;

import org.gradle.tooling.internal.protocol.ProjectVersion3;
import org.gradle.tooling.model.DomainObjectSet;
import org.gradle.tooling.model.GradleProject;
import org.gradle.tooling.model.GradleTask;
import org.gradle.tooling.model.internal.ImmutableDomainObjectSet;

import java.io.File;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * @author: Szczepan Faber, created at: 7/27/11
 */
public class DefaultGradleProject implements ProjectVersion3, GradleProject, Serializable {

    private String name;
    private String description;
    private String id;
    private GradleProject parent;
    private List<GradleProject> children = new LinkedList<GradleProject>();
    private List<GradleTask> tasks = new LinkedList<GradleTask>();

    public String getName() {
        return name;
    }

    public DefaultGradleProject setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public DefaultGradleProject setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getId() {
        return id;
    }

    public DefaultGradleProject setId(String id) {
        this.id = id;
        return this;
    }

    public GradleProject getParent() {
        return parent;
    }

    public DefaultGradleProject setParent(GradleProject parent) {
        this.parent = parent;
        return this;
    }

    public DomainObjectSet<GradleProject> getChildren() {
        return new ImmutableDomainObjectSet<GradleProject>(children);
    }

    public DefaultGradleProject setChildren(List<GradleProject> children) {
        this.children = children;
        return this;
    }

    public DomainObjectSet<GradleTask> getTasks() {
        return new ImmutableDomainObjectSet<GradleTask>(tasks);
    }

    public DefaultGradleProject setTasks(List<GradleTask> tasks) {
        this.tasks = tasks;
        return this;
    }

    public String getPath() {
        return id;
    }

    public DefaultGradleProject setPath(String path) {
        this.id = path;
        return this;
    }

    public File getProjectDirectory() {
        throw new RuntimeException("ProjectVersion3 methods are deprecated.");
    }
}