package com.cse545.hospitalSystem.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cse545.hospitalSystem.models.Logs;
import com.cse545.hospitalSystem.repositories.LogsRepository;

@RestController
@RequestMapping(path="api/logs")
public class LogsController {

	@Autowired
	private LogsRepository logsRepo;
	
	@CrossOrigin
	@GetMapping("/getAllLogs")
	private ResponseEntity<List<Logs>> getAllLogs(){
		List<Logs> logs = logsRepo.findAll();
		return new ResponseEntity<List<Logs>>(logs, HttpStatus.OK);
	}
}
