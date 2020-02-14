package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.example.demo.exception.RecordNotFoundException;
import com.example.demo.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.StudentEntity;

@Service
public class StudentService {
	
	@Autowired
	StudentRepository repository;
	
	public List<StudentEntity> getAllStudents()
	{
		List<StudentEntity> result = (List<StudentEntity>) repository.findAll();
		
		if(result.size() > 0) {
			return result;
		} else {
			return new ArrayList<StudentEntity>();
		}
	}
	
	public StudentEntity getStudentById(Long id) throws RecordNotFoundException
	{
		Optional<StudentEntity> student = repository.findById(id);
		
		if(student.isPresent()) {
			return student.get();
		} else {
			throw new RecordNotFoundException("No Student record exist for given id");
		}
	}
	
	public StudentEntity createOrUpdateStudent(StudentEntity entity)
	{
		if(entity.getId()  == null) 
		{
			entity = repository.save(entity);
			
			return entity;
		} 
		else 
		{
			Optional<StudentEntity> student = repository.findById(entity.getId());
			
			if(student.isPresent())
			{
				StudentEntity newEntity = student.get();
				newEntity.setEmail(entity.getEmail());
				newEntity.setFirstName(entity.getFirstName());
				newEntity.setLastName(entity.getLastName());

				newEntity = repository.save(newEntity);
				
				return newEntity;
			} else {
				entity = repository.save(entity);
				
				return entity;
			}
		}
	} 
	
	public void deleteStudentById(Long id) throws RecordNotFoundException
	{
		Optional<StudentEntity> student = repository.findById(id);
		
		if(student.isPresent())
		{
			repository.deleteById(id);
		} else {
			throw new RecordNotFoundException("No student record exist for given id");
		}
	} 
}