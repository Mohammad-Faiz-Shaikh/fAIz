package com.example.newbot.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DocumentService {
	private final VectorStore vectorStore;
	private final JdbcClient jdbcClient;
	
@Value("${app.pdf-folder}")
private String pdfFolder;

Set<String> fileNames = new HashSet<>();

@PostConstruct
public void init() throws IOException
{
	File folder = new File("pdf");

	File[] pdfFiles = folder.listFiles((dir, name) ->
	        name.toLowerCase().endsWith(pdfFolder));

	if (pdfFiles != null) {
	    for (File pdfFile : pdfFiles) {
	    	fileNames.add(pdfFile.getName());
	        loadPdf(pdfFile);
	    }
	}
}
public void loadPdf(File file) throws IOException
{
	 try (PDDocument pdf = Loader.loadPDF(file)) {

	        PDFTextStripper stripper = new PDFTextStripper();
	        String text = stripper.getText(pdf);

	        Document document = new Document(text);

	        vectorStore.add(List.of(document));

	        System.out.println(file.getName() + " processed successfully.");
	    }
	
}

public String deletePdf(String fileName) throws IOException
{
	Path path = Paths.get(pdfFolder , fileName);
	if(!Files.exists(path))
	{
		return "File not found";
	}
	Files.delete(path);
	
	fileNames.remove(fileName);
	return "PDF deleted successfully";
}


public void processDocument(MultipartFile file)throws Exception {

PDDocument pdf =Loader.loadPDF(file.getBytes());
PDFTextStripper stripper =new PDFTextStripper();
String text =stripper.getText(pdf);
Document doc =new Document(text);
vectorStore.add(List.of(doc));
fileNames.add(file.getOriginalFilename());
System.out.println("Document processed successfully");
}

public Set<String> getFileNames()
{
	return fileNames;
}
}
