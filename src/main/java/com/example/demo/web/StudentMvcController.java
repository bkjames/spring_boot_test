package com.example.demo.web;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.demo.exception.RecordNotFoundException;
import com.example.demo.model.StudentEntity;
import com.example.demo.service.StudentService;

@Controller
@RequestMapping("/")
public class StudentMvcController
{
	@Autowired
	StudentService service;

	@RequestMapping
	public String getAllStudents(Model model)
	{
		List<StudentEntity> list = service.getAllStudents();

		model.addAttribute("students", list);
		return "list_student";
	}

	@RequestMapping(path = {"/edit", "/edit/{id}"})
	public String editStudentById(Model model, @PathVariable("id") Optional<Long> id)
							throws RecordNotFoundException 
	{
		if (id.isPresent()) {
			StudentEntity entity = service.getStudentById(id.get());
			model.addAttribute("student", entity);
		} else {
			model.addAttribute("student", new StudentEntity());
		}
		return "add-edit-student";
	}
	
	@RequestMapping(path = "/delete/{id}")
	public String deleteStudentById(Model model, @PathVariable("id") Long id)
							throws RecordNotFoundException 
	{
		service.deleteStudentById(id);
		return "redirect:/";
	}

	@RequestMapping(path = "/createStudent", method = RequestMethod.POST)
	public String createOrUpdateStudent(StudentEntity student)
	{
		service.createOrUpdateStudent(student);
		return "redirect:/";
	}
}
