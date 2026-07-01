package com.example.newbot.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.newbot.service.DocumentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/document")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class DocumentController {

    private final DocumentService service;
    
    
    @GetMapping("/get/pdf")
    public Set<String> getPdf()
    {
    	return service.getFileNames();
    }


    @PostMapping(value = "/upload",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String upload(@RequestPart("file") MultipartFile file) throws Exception {

    	String fileName= file.getOriginalFilename();
    	Path folder = Paths.get("pdf");
    	if(!Files.exists(folder))
    	{
    		Files.createDirectories(folder);
    	}
    	Path path=folder.resolve(fileName);
    	
    	file.transferTo(path);
        service.processDocument(file);

        return "uploaded";
    }
    @DeleteMapping("/delete/{fileName}")
    public String delete(@PathVariable String fileName) throws IOException
    {
    	 service.deletePdf(fileName);
       	 return "Deleted";
    }
}