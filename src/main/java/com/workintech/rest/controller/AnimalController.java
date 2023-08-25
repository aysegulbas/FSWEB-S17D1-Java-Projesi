package com.workintech.rest.controller;


import com.workintech.rest.controller.entity.Animal;
import com.workintech.rest.controller.mapping.AnimalResponse;
import com.workintech.rest.controller.validation.AnimalValidation;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController//jackson library, getter ve setterlar ile requestlere göre ekrana basar
@RequestMapping("/workintech/animal")// url uzantısı
public class AnimalController {
    @Value("${instructor.name}")
    private String name;
    @Value("${instructor.surname}")
    private String surname;
    private Map<Integer, Animal> animalMap;//=new HasMap hard dependency bunu sevmeyiz

    @PostConstruct//constructor ilk önce çalışır, ondan hemen sonra bu çalışır. bir defa çalışır useeffect gibi
    public void init() {
        animalMap = new HashMap<>();
    }

    @GetMapping("/welcome")
    public String welcome() {
        return name + " " + surname + " says hi";
    }

    @GetMapping("/")
    public List<Animal> get() {
        return animalMap.values().stream().toList();
    }

    @GetMapping("/{id}")
    public AnimalResponse get(@PathVariable int id) {
        if (!AnimalValidation.isValid(id)) {
            return new AnimalResponse(null, "Id is not valid", 400);
        }
        if (!AnimalValidation.isMapContainsKey(animalMap,id)) {
            return new AnimalResponse(null, "Animal is not exist", 400);
        }
        return new AnimalResponse(animalMap.get(id), "Success", 200);
    }

    @PostMapping("/")
    public AnimalResponse save(@RequestBody Animal animal) {
        if (AnimalValidation.isMapContainsKey(animalMap,animal.getId())) {
            return new AnimalResponse(null, "animal is already exist", 400);
        }
        if (!AnimalValidation.isAnimalCredentialValid(animal)) {
            return new AnimalResponse(null, "animal credential are not valid", 400);
        }
        animalMap.put(animal.getId(), animal);
//return animal, da olur, yada aşağıdaki olur
        return new AnimalResponse(animalMap.get(animal.getId()), "Success", 201);
    }

    @PutMapping("/{id}")
    public AnimalResponse update(@PathVariable int id, @RequestBody Animal animal) {
        if (!AnimalValidation.isMapContainsKey(animalMap,id)) {
            return new AnimalResponse(null, "Animal is not exist", 400);
        }
        if (!AnimalValidation.isAnimalCredentialValid(animal)) {
            return new AnimalResponse(null, "animal credential are not valid", 400);
        }
        //animalMap.put(id,animal) eğer id'nin set edilmesini istemiyorsak
        animalMap.put(id, new Animal(id, animal.getName()));//eski id'i ver ismini de yeni requestteki ismi ver
        return new AnimalResponse(animalMap.get(id), "Success", 201);
    }

    @DeleteMapping("/{id}")
    public AnimalResponse delete(@PathVariable int id) {
        if (!AnimalValidation.isMapContainsKey(animalMap,id)) {
            return new AnimalResponse(null, "Animal is not exist", 400);
        }
        Animal foundAnimal = animalMap.get(id);
        animalMap.remove(id);
        return new AnimalResponse(foundAnimal, "Success", 201);
    }

    @PreDestroy//ölüm anında yani garbage'a gitmeden önce çalışır
    public void destroy() {
        System.out.println("Animal controller has been destroyed");
    }
}
