package com.example.group.project.controller;

import com.example.group.project.exceptions.InvalidParameterException;
import com.example.group.project.exceptions.ResourceNotFoundException;
import com.example.group.project.service.impl.RecordServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Controller
public class RecordController {
    @Autowired
    private RecordServiceImpl recordServiceImpl;

    @GetMapping("/records")
    public String getRecords(@RequestParam(required = false) Map<String, String> requestParams, Model model) {
        try {
            Map<String, String> params = (requestParams == null) ? new HashMap<>() : requestParams;
            model.addAttribute("records", recordServiceImpl.requestHandler(params));
            return "records";
        } catch (InvalidParameterException e) {
            model.addAttribute("error", e.getMessage());
            return "records";
        } catch (ResourceNotFoundException e) {
            model.addAttribute("error", e.getMessage());
            return "records";
        }
    }
}
