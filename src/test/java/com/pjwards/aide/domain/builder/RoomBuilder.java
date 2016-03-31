package com.pjwards.aide.domain.builder;

import com.pjwards.aide.domain.Room;
import org.springframework.test.util.ReflectionTestUtils;

public class RoomBuilder {

    private Room model;

    public RoomBuilder() {
        model = new Room();
    }

    public RoomBuilder id(Long id) {
        ReflectionTestUtils.setField(model, "id", id);
        return this;
    }

    public RoomBuilder name(String name) {
        model.update(name, model.getLocation(), model.getDescription());
        return this;
    }

    public RoomBuilder location(String location) {
        model.update(model.getName(), location, model.getDescription());
        return this;
    }

    public RoomBuilder description(String description) {
        model.update(model.getName(), model.getLocation(), description);
        return this;
    }

    public Room build() {
        return model;
    }
}
