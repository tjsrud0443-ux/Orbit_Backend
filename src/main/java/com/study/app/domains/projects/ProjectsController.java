package com.study.app.domains.projects;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.study.app.domains.users.UsersDTO;

@RestController
@RequestMapping("/project")
public class ProjectsController {
	
	@Autowired
	private ProjectsService projectServ;
	
	@GetMapping("/allEmployee")
	public ResponseEntity<List<UsersDTO>> allEmployee() {
		return ResponseEntity.ok(projectServ.allEmployee());
	}
	
	@PostMapping("/insertProjectAndMembers")
	public ResponseEntity<Void> insertProjectAndMembers(@RequestAttribute String loginId, @RequestBody ProjectsDTO dto) {
		projectServ.insertProjectAndMembers(loginId, dto);
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/getAllProject")
	public ResponseEntity<List<ProjectsDTO>> getAllProject(@RequestAttribute String loginId) {
		return ResponseEntity.ok(projectServ.getAllProject(loginId));
	}
	
	@PutMapping("/projectUpdate")
	public ResponseEntity<Void> projectUpdate(@RequestAttribute String loginId, @RequestBody ProjectsDTO dto) {
		projectServ.projectUpdate(loginId, dto);
		return ResponseEntity.ok().build();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
