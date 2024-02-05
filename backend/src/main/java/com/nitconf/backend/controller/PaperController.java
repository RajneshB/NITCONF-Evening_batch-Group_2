package com.nitconf.backend.controller;

import com.nitconf.backend.models.Paper;
import com.nitconf.backend.security.services.PaperService;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

// @CrossOrigin(origins = "", maxAge = 3600, allowCredentials = "true")
@RestController
@RequestMapping("/api/paper")
public class PaperController {

    private final PaperService paperService;

    public PaperController(PaperService paperService) {
        this.paperService = paperService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Paper>> getAllPapers() {
        try {
            List<Paper> papers = paperService.getAllPapers();
            return ResponseEntity.ok(papers);
        }
        catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Paper> getPaperById(@PathVariable String id) {
        Optional<Paper> paper = paperService.getPaperById(id);
        return ResponseEntity.of(paper);
    }

    @PutMapping("/savefile/{id}")
    public ResponseEntity<?> saveFile(
            @RequestPart("pdfFile") MultipartFile pdfFile,
            @PathVariable String id) {

        try {
            byte[] pdfBytes = pdfFile.getBytes();
            paperService.updatePaperPdfFile(id,pdfBytes);

            return ResponseEntity.status(HttpStatus.CREATED).body("Paper saved successfully");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error processing PDF file: " + e.getMessage());
        }
    }

    @PostMapping("/savedata")
    public void saveData(
            @RequestBody Paper paper) {
                paper.setRating();
                paperService.savePaper(paper);
    }

    @GetMapping("/tags")
    public ResponseEntity<List<Paper>> getPapersByTags(@RequestParam List<String> tags) {
        List<Paper> papers = paperService.getPapersByTags(tags);
        return ResponseEntity.ok(papers);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deletePaper(@PathVariable String id) {
        paperService.deletePaper(id);
        return ResponseEntity.ok("Paper deleted successfully");
    }

    @GetMapping("/status")
    public ResponseEntity<List<Paper>> getPapersByStatus(@RequestParam String status) {
        List<Paper> papers = paperService.getPapersByStatus(status);
        return ResponseEntity.ok(papers);
    }

    @GetMapping("/decision")
    public ResponseEntity<List<Paper>> getPapersByDecision(@RequestParam String decision) {
        List<Paper> papers = paperService.getPapersByDecision(decision);
        return ResponseEntity.ok(papers);
    }

    @PutMapping("/updateDecision/{id}")
    public ResponseEntity<String> updatePaperDecision(@PathVariable String id, @RequestParam String newDecision) {
        paperService.updatePaperDecision(id, newDecision);
        return ResponseEntity.ok("Paper decision updated successfully");
    }
}
