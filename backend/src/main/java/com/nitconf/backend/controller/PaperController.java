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

/**
 * Controller class for managing papers in the system.
 * Provides endpoints for retrieving, saving, and updating paper information.
 * Accessible through "/api/paper" base path.
 *
 * @author <a href="https://github.com/jj58dev">Joel Joseph</a>
 */
@CrossOrigin(origins = "https://nitconf.vercel.app", maxAge = 3600, allowCredentials = "true")
@RestController
@RequestMapping("/api/paper")
public class PaperController {

    private final PaperService paperService;

    /**
     * Constructor for PaperController.
     *
     * @param paperService The service responsible for handling paper-related operations.
     */
    public PaperController(PaperService paperService) {
        this.paperService = paperService;
    }

    /**
     * Retrieves all papers in the system.
     *
     * @return ResponseEntity containing a list of Paper objects if successful, otherwise returns not found.
     * @since 1.0
     * @author <a href="https://github.com/jj58dev">Joel Joseph</a>
     */
    @GetMapping("/all")
    public ResponseEntity<List<Paper>> getAllPapers() {
        try {
            List<Paper> papers = paperService.getAllPapers();
            return ResponseEntity.ok(papers);
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Retrieves a paper by its unique identifier.
     *
     * @param id The unique identifier of the paper.
     * @return ResponseEntity containing the Paper object if found, otherwise returns empty.
     * @since 1.0
     * @author <a href="https://github.com/jj58dev">Joel Joseph</a>
     */
    @SuppressWarnings("null")
    @GetMapping("/{id}")
    public ResponseEntity<Paper> getPaperById(@PathVariable String id) {
        Optional<Paper> paper = paperService.getPaperById(id);
        return ResponseEntity.of(paper);
    }

    /**
     * Updates the PDF file associated with a paper.
     *
     * @param pdfFile The PDF file to be saved.
     * @param id      The unique identifier of the paper.
     * @return ResponseEntity indicating the success of the operation or an error message.
     * @since 1.0
     * @author <a href="https://github.com/jj58dev">Joel Joseph</a>
     */
    @PutMapping("/savefile/{id}")
    public ResponseEntity<?> saveFile(
            @RequestPart("pdfFile") MultipartFile pdfFile,
            @PathVariable String id) {

        try {
            byte[] pdfBytes = pdfFile.getBytes();
            paperService.updatePaperPdfFile(id, pdfBytes);

            return ResponseEntity.status(HttpStatus.CREATED).body("Paper saved successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error processing PDF file: " + e.getMessage());
        }
    }

    /**
     * Saves paper data to the system.
     *
     * @param paper The Paper object containing data to be saved.
     * @since 1.0
     * @author <a href="https://github.com/jj58dev">Joel Joseph</a>
     */
    @PostMapping("/savedata")
    public void saveData(
            @RequestBody Paper paper) {
        paper.setRating();
        paperService.savePaper(paper);
    }

    /**
     * Retrieves papers based on provided tags.
     *
     * @param tags List of tags to filter papers.
     * @return ResponseEntity containing a list of Paper objects matching the provided tags.
     * @since 1.0
     * @author <a href="https://github.com/jj58dev">Joel Joseph</a>
     */
    @GetMapping("/tags")
    public ResponseEntity<List<Paper>> getPapersByTags(@RequestParam List<String> tags) {
        List<Paper> papers = paperService.getPapersByTags(tags);
        return ResponseEntity.ok(papers);
    }

    /**
     * Deletes a paper by its unique identifier.
     *
     * @param id The unique identifier of the paper to be deleted.
     * @return ResponseEntity indicating the success of the deletion operation.
     * @since 1.0
     * @author <a href="https://github.com/jj58dev">Joel Joseph</a>
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deletePaper(@PathVariable String id) {
        paperService.deletePaper(id);
        return ResponseEntity.ok("Paper deleted successfully");
    }

    /**
     * Retrieves papers based on the provided status.
     *
     * @param status The status to filter papers.
     * @return ResponseEntity containing a list of Paper objects matching the provided status.
     * @since 1.0
     * @author <a href="https://github.com/jj58dev">Joel Joseph</a>
     */
    @GetMapping("/status")
    public ResponseEntity<List<Paper>> getPapersByStatus(@RequestParam String status) {
        List<Paper> papers = paperService.getPapersByStatus(status);
        return ResponseEntity.ok(papers);
    }

    /**
     * Retrieves papers based on the provided decision.
     *
     * @param decision The decision to filter papers.
     * @return ResponseEntity containing a list of Paper objects matching the provided decision.
     * @since 1.0
     * @author <a href="https://github.com/jj58dev">Joel Joseph</a>
     */
    @GetMapping("/decision")
    public ResponseEntity<List<Paper>> getPapersByDecision(@RequestParam String decision) {
        List<Paper> papers = paperService.getPapersByDecision(decision);
        return ResponseEntity.ok(papers);
    }

    /**
     * Updates the decision of a paper based on its unique identifier.
     *
     * @param id          The unique identifier of the paper.
     * @param newDecision The new decision to be set for the paper.
     * @return ResponseEntity indicating the success of the operation.
     * @since 1.0
     * @author <a href="https://github.com/jj58dev">Joel Joseph</a>
     */
    @PutMapping("/updateDecision/{id}")
    public ResponseEntity<String> updatePaperDecision(@PathVariable String id, @RequestParam String newDecision) {
        paperService.updatePaperDecision(id, newDecision);
        return ResponseEntity.ok("Paper decision updated successfully");
    }

    /**
     * Adds a new reviewer to the reviews array of a paper based on its unique identifier,
     * only if the reviewer name is not already present.
     *
     * @param id              The unique identifier of the paper.
     * @param newReviewerName The name of the new reviewer to be added.
     * @return ResponseEntity indicating the success of the operation.
     * @since 1.0
     * @author <a href="https://github.com/jj58dev">Joel Joseph</a>
     */
    @PutMapping("/addReviewer/{id}")
    public ResponseEntity<String> addReviewerToPaper(@PathVariable String id, @RequestParam String newReviewerName) {
        return paperService.addReviewerToPaper(id, newReviewerName);
    }
}
