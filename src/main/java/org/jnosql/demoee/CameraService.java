package org.jnosql.demoee;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.jnosql.mapping.document.DocumentTemplate;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@ApplicationScoped
public class CameraService {

    private static final Logger LOGGER = Logger.getLogger(CameraService.class.getName());

    @Inject
    DocumentTemplate template;

    public List<Camera> findAll() {
        return template.select(Camera.class).result();
    }

    public List<Camera> findByName(String name) {
        return template.select(Camera.class)
                .where("name")
                .like(name)
                .result();
    }

    public Optional<Camera> findById(String id) {
        return template.find(Camera.class, id);
    }

    public void deleteById(String id) {
        template.delete(Camera.class, id);
    }



}
