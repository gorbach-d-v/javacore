package ru.yusdm.javacore.lesson4oopinheritence.lesson.inheritence.visibility.packageprivate;

public class PersonWithPackagePrivateConstructor {
    private String name;

    PersonWithPackagePrivateConstructor(String name) {
        this.name = name;
    }

    public PersonWithPackagePrivateConstructor() {
    }

    public String getName() {
        return name;
    }
}
