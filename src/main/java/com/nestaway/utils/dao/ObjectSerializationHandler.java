package com.nestaway.utils.dao;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class ObjectSerializationHandler<O>{

    private String filePath;

    public ObjectSerializationHandler(String filePath){
        this.filePath = filePath;
    }

    private void writeObjectsCleaned(List<O> objects) throws IOException {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(filePath)))){
            for (O object : objects) {
                outputStream.writeObject(object);
            }
        }
    }

    public void writeObjects(O object) throws IOException, ClassNotFoundException {
        List<O> objs = readObjects();
        objs.add(object);
        writeObjectsCleaned(objs);

    }

    public void writeObjects(List<O> objects) throws IOException, ClassNotFoundException {
        List<O> objs = readObjects();
        objs.addAll(objects);
        writeObjectsCleaned(objs);
    }

    public List<O> readObjects() throws IOException, ClassNotFoundException {
        List<O> objects = new ArrayList<>();
        try(ObjectInputStream inputStream = new ObjectInputStream(new BufferedInputStream(new FileInputStream(filePath)))) {
            O object;
            while (true) {
                object = (O) inputStream.readObject();
                objects.add(object);
            }
        } catch (EOFException e) {
            return objects;
        }
    }

    public List<O> findObject(Predicate<O> predicate) throws IOException, ClassNotFoundException {
        List<O> objects = readObjects();
        objects = objects.stream().filter(predicate).toList();
        return objects;
    }

    public void deleteObjects(Predicate<O> predicate) throws IOException, ClassNotFoundException {
        List<O> objects = readObjects();
        objects.removeIf(predicate);
        writeObjectsCleaned(objects);
    }

    public void deleteObjects(List<Predicate<O>> predicates) throws IOException, ClassNotFoundException {
        List<O> objs = readObjects();
        for (Predicate<O> p : predicates) {
            objs.removeIf(p);
        }
        writeObjectsCleaned(objs);
    }

}
