package org.jnosql.demoee;

import io.quarkus.virtual.threads.VirtualThreads;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import net.datafaker.Faker;
import org.eclipse.jnosql.mapping.document.DocumentTemplate;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.logging.Logger;

@ApplicationScoped
public class CameraService {

    private static final Logger LOGGER = Logger.getLogger(CameraService.class.getName());

    private static final Faker FAKER = new Faker();

    @Inject
    DocumentTemplate template;

    @Inject
    @VirtualThreads
    ExecutorService vThreads;

    public List<Camera> findAll() {
        LOGGER.info("Selecting all cameras");
        return template.select(Camera.class).result();
    }

    public List<Camera> findByBrand(String brand) {
        LOGGER.info("Selecting cameras by brand: " + brand);
        return template.select(Camera.class)
                .where("brand")
                .like(brand)
                .result();
    }

    public Optional<Camera> findById(String id) {
        var camera =  template.find(Camera.class, id);
        LOGGER.info("Selecting camera by id: " + id + " find? " + camera.isPresent());
        return camera;
    }

    public void deleteById(String id) {
        LOGGER.info("Deleting camera by id: " + id);
        template.delete(Camera.class, id);
    }

    public Camera insert(Camera camera) {
        LOGGER.info("Inserting camera: " + camera.id());
        return template.insert(camera);
    }

    public Camera update(Camera update) {
        LOGGER.info("Updating camera: " + update.id());
        return template.update(update);
    }

    public void insertAsync(int size) {
        LOGGER.info("Inserting cameras async the size: " + size);

        vThreads.submit(() -> {
            for (int index = 0; index < size; index++) {
                Camera camera = Camera.of(FAKER);
                template.insert(camera);
            }
        });
    }
}
