package birdsnail.example.entity;

import lombok.Data;

@Data
public class ObjectWrapper {

    private Object object;

    public ObjectWrapper(Object object) {
        this.object = object;
    }

}
