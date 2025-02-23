package org.jnosql.demoee;

import jakarta.nosql.Column;
import jakarta.nosql.Entity;
import jakarta.nosql.Id;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

@Entity
public record Camera(
        @Id String id,
        @Column String name,
        @Column LocalDate birthday
) {

    public static Camera newDeveloper(String name, LocalDate birthday) {
        Objects.requireNonNull(name, "name is required");
        Objects.requireNonNull(birthday, "birthday is required");
        return new Camera(
                UUID.randomUUID().toString(),
                name,
                birthday);
    }

    public Camera update(String name, LocalDate birthday) {
        Objects.requireNonNull(name, "name is required");
        Objects.requireNonNull(birthday, "birthday is required");
        return new Camera(
                this.id(),
                name,
                birthday);
    }
}
