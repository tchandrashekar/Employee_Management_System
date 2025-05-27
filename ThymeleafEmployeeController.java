
package com.example.Jala_Assignmet_Project;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/employees")
public class ThymeleafEmployeeController {
    
      @Autowired
    private EmployeeService service;

    private boolean isLoggedIn(HttpSession session) {
        return session.getAttribute("username") != null;
    }

    @GetMapping
    public String listEmployees(Model model, HttpSession session) {
        if (!isLoggedIn(session)) {
            return "redirect:/login";
        }
        model.addAttribute("employees", service.getAll());
        return "employee-list";
    }

    @GetMapping("/new")
    public String createForm(Model model, HttpSession session) {
        if (!isLoggedIn(session)) {
            return "redirect:/login";
        }
        model.addAttribute("employee", new Employee());
        return "employee-form";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model, HttpSession session) {
        if (!isLoggedIn(session)) {
            return "redirect:/login";
        }
        model.addAttribute("employee", service.getById(id).orElse(new Employee()));
        return "employee-form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Employee employee, HttpSession session) {
        if (!isLoggedIn(session)) {
            return "redirect:/login";
        }
        service.save(employee);
        return "redirect:/employees";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, HttpSession session) {
        if (!isLoggedIn(session)) {
            return "redirect:/login";
        }
        service.delete(id);
        return "redirect:/employees";
    }
}
