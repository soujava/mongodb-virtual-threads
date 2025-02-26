package org.jnosql.demoee;

import jakarta.nosql.Column;
import jakarta.nosql.Entity;
import jakarta.nosql.Id;
import net.datafaker.Faker;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

@Entity
public record Camera(
        @Id String id,
        @Column String brand,
        @Column String model,
        @Column String brandWithModel
) {

    public static Camera of(Faker faker) {
        var camera = faker.camera();
        String brand = camera.brand();
        String model = camera.model();
        String brandWithModel = camera.brandWithModel();
        return new Camera(UUID.randomUUID().toString(), brand, model, brandWithModel);
    }

    public Camera update(Camera request) {
        return new Camera(this.id, request.brand, request.model, request.brandWithModel);
    }
}