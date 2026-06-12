package com.study.app.domains.projects;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
	
	@GetMapping("/getMyAllProject")
	public ResponseEntity<List<ProjectsDTO>> getProject(@RequestAttribute String loginId) {
		return ResponseEntity.ok(projectServ.getProject(loginId));
	}
	
	@PutMapping("/projectUpdate")
	public ResponseEntity<Void> updateProject(@RequestAttribute String loginId, @RequestBody ProjectsDTO dto) {
		projectServ.updateProject(loginId, dto);
		return ResponseEntity.ok().build();
	}
	
	@DeleteMapping("/projectDelete/{project_seq}")
	public ResponseEntity<Void> deleteProject(@PathVariable Long project_seq) {
		projectServ.deleteProject(project_seq);
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/getKanbanTaskList/{project_seq}")
	public ResponseEntity<List<KanbanTaskDTO>> getKanbanTaskList(@PathVariable Long project_seq) {
		return ResponseEntity.ok(projectServ.getKanbanTaskList(project_seq));
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
