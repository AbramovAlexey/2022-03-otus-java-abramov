package ru.otus.model;

import lombok.EqualsAndHashCode;

import java.util.List;
import java.util.Optional;

@EqualsAndHashCode
public class ObjectForMessage implements Cloneable{
    private List<String> data;

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }

    @Override
    public ObjectForMessage clone() throws CloneNotSupportedException {
        ObjectForMessage newObjectForMessage = (ObjectForMessage)super.clone();
        Optional.ofNullable(this.getData()).ifPresent(list ->  newObjectForMessage.setData(List.copyOf(list)));
        return newObjectForMessage;
    }

}
