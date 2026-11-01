package com.cca2.studentmanagement;
import org.springframework.web.bind.annotation.*;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class StudentController {
    private final List<Student> students = new CopyOnWriteArrayList<>();
    private final AtomicLong ids = new AtomicLong(2);

    public StudentController() {
        students.add(new Student(1, "Aarav Sharma", "Computer Engineering"));
        students.add(new Student(2, "Ananya Patil", "Information Technology"));
    }

    @GetMapping("/")
    public String home() {
        return "<!doctype html><html><head><title>Student Management</title>" +
          "<style>body{font-family:Arial;max-width:760px;margin:50px auto;padding:0 20px;background:#f5f7fb;color:#182230}" +
          ".card{background:white;padding:24px;border-radius:14px;box-shadow:0 2px 12px #0001}" +
          "input,button{padding:11px;margin:5px;border:1px solid #ccd3df;border-radius:7px}button{background:#185adb;color:white;border:0}" +
          "li{padding:9px;border-bottom:1px solid #eee}</style></head><body><div class='card'>" +
          "<h1>Student Management System</h1><p>CCA 2 CI/CD deployment demo – Java Spring Boot</p>" +
          "<p><b>Version 1:</b> Application is running successfully on Kubernetes.</p>" +
          "<h2>Students</h2><ul id='students'></ul><h3>Add a student</h3>" +
          "<input id='name' placeholder='Student name'><input id='course' placeholder='Course'>" +
          "<button onclick='addStudent()'>Add Student</button><p id='msg'></p></div>" +
          "<script>async function load(){let r=await fetch('/api/students');let a=await r.json();" +
          "document.getElementById('students').innerHTML=a.map(s=>`<li>${s.id}. ${s.name} – ${s.course}</li>`).join('')}" +
          "async function addStudent(){let name=document.getElementById('name').value,course=document.getElementById('course').value;" +
          "if(!name||!course){document.getElementById('msg').innerText='Enter both fields';return;}" +
          "await fetch('/api/students',{method:'POST',headers:{'Content-Type':'application/json'},body:JSON.stringify({name,course})});" +
          "document.getElementById('msg').innerText='Student added';load()}load();</script></body></html>";
    }

    @GetMapping("/api/students")
    public List<Student> getStudents() { return students; }

    @PostMapping("/api/students")
    public Student addStudent(@RequestBody StudentInput input) {
        Student s = new Student(ids.incrementAndGet(), input.name(), input.course());
        students.add(s);
        return s;
    }

    public record Student(long id, String name, String course) {}
    public record StudentInput(String name, String course) {}
}
