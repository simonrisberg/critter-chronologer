package com.simonrisberg.critterchronologer.entity;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "schedule")
public class Schedule {

    @Id
    @GeneratedValue
    private long id;

    @ManyToMany
    private List<Employee> employees = new LinkedList<>();

    @ManyToMany
    private List<Pet> pets = new LinkedList<>();

    private LocalDate date;

    @ElementCollection
    private Set<EmployeeSkill> skills = new HashSet<>();

}
