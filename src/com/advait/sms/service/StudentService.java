package com.advait.sms.service;

import com.advait.sms.model.Student;

import java.io.*;
import java.util.ArrayList;

public class StudentService {

    private static final String FILE_NAME = "students.txt";
    private ArrayList<Student> students = new ArrayList<>();

    // Load students when program starts
    public void loadFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");

                int id = Integer.parseInt(parts[0]);
                String name = parts[1];
                int age = Integer.parseInt(parts[2]);

                students.add(new Student(id, name, age));
            }

        } catch (IOException e) {
            // First time file may not exist
        }
    }

    // Save students to file
    private void saveToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Student s : students) {
                writer.write(s.getId() + "," + s.getName() + "," + s.getAge());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving data.");
        }
    }

    public void addStudent(Student student) {

        for (Student s : students) {
            if (s.getId() == student.getId()) {
                System.out.println("Student with this ID already exists.");
                return;
            }
        }

        if (student.getAge() <= 0) {
            System.out.println("Age cannot be negative or zero.");
            return;
        }

        students.add(student);
        saveToFile();
        System.out.println("Student added successfully.");
    }

    public void viewAllStudents() {

        if (students.isEmpty()) {
            System.out.println("No students available.");
            return;
        }

        for (Student s : students) {
            s.displayStudent();
        }
    }

    public void searchStudentById(int id) {

        for (Student s : students) {
            if (s.getId() == id) {
                s.displayStudent();
                return;
            }
        }

        System.out.println("Student not found.");
    }

    public void deleteStudentById(int id) {

        for (Student s : students) {
            if (s.getId() == id) {
                students.remove(s);
                saveToFile();
                System.out.println("Student deleted successfully.");
                return;
            }
        }

        System.out.println(".Student not found.");
    }

    public void updateStudentById(int id, String newName, int newAge) {

        for (Student s : students) {
            if (s.getId() == id) {

                if (newAge <= 0) {
                    System.out.println("Age must be positive.");
                    return;
                }

                s.setName(newName);
                s.setAge(newAge);
                saveToFile();
                System.out.println("Student updated successfully.");
                return;
            }
        }

        System.out.println("Student not found.");
    }
}