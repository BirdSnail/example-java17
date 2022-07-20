package birdsnail.example.entity;

import org.junit.jupiter.api.Test;

class ObjectWrapperTest {


    @Test
    void valueCopyTest() {
        Object one = new Object();
        ObjectWrapper wrapper = new ObjectWrapper(one);
        System.out.println(wrapper.getObject());
        setOne(wrapper.getObject());
        System.out.println(wrapper.getObject());
    }

    private void setOne(Object one) {
        one = new Object();
        System.out.println(one);
    }


}