package com.yelp.supportclasses;

import java.util.HashSet;
import java.util.Set;

public class BusinessClass {
    Set<String> selectedMainCategories;
    Set<String> selectedSubCategories;
    Set<String> selectedAttributes;
    String And_Or_main;


    public BusinessClass(HashSet<String> selectedMainCategories, HashSet<String> selectedSubCategories, HashSet<String> selectedAttributes, String and_Or_main) {
        this.selectedMainCategories = selectedMainCategories;
        this.selectedSubCategories = selectedSubCategories;
        this.selectedAttributes = selectedAttributes;
        And_Or_main = and_Or_main;
    }


    public BusinessClass() {
        And_Or_main = "AND";
    }


    public Set<String> getSelectedMainCategories() {
        return selectedMainCategories;
    }

    public void setSelectedMainCategories(Set<String> selectedMainCategories) {
        this.selectedMainCategories = selectedMainCategories;
    }

    public Set<String> getSelectedSubCategories() {
        return selectedSubCategories;
    }

    public void setSelectedSubCategories(Set<String> selectedSubCategories) {
        this.selectedSubCategories = selectedSubCategories;
    }

    public Set<String> getSelectedAttributes() {
        return selectedAttributes;
    }

    public void setSelectedAttributes(Set<String> selectedAttributes) {
        this.selectedAttributes = selectedAttributes;
    }

    public String getAnd_Or_main() {
        return And_Or_main;
    }

    public void setAnd_Or_main(String and_Or_main) {
        And_Or_main = and_Or_main;
    }
}
