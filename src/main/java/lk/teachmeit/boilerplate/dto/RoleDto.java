package lk.teachmeit.boilerplate.dto;

import lk.teachmeit.boilerplate.model.Permission;

import java.util.List;

public class RoleDto {
    private String id;
    private String name;
    private String description;
    private List<Permission> permissions;

    public RoleDto() {
    }

    public RoleDto(String id, String name, String description, List<Permission> permissions) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.permissions = permissions;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }
}
