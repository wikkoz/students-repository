package com.web.configuration;

import com.google.common.base.MoreObjects;

public class TypeWrapper<T> {
    private T value;

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public static <T> TypeWrapper<T> of(T value){
        TypeWrapper<T> wrapper =  new TypeWrapper<>();
        wrapper.setValue(value);
        return wrapper;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("value", value)
                .toString();
    }
}
