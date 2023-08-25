package com.workintech.rest.controller.validation;

import com.workintech.rest.controller.entity.Animal;

import java.util.Map;

public class AnimalValidation {
    public static boolean isValid(int id){
        return !(id<0);
    }
    public static boolean isMapContainsKey(Map<Integer, Animal>animals,int id){
        return animals.containsKey(id);
    }
public static boolean isAnimalCredentialValid(Animal animal){
        return !(animal.getId() <= 0 || animal.getName() == null || animal.getName().isEmpty());
}
}
