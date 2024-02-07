package com.nitconf.backend.security.services;

import com.nitconf.backend.models.Paper;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.nitconf.backend.repository.PaperRepository;

@Service
public class PaperService {

    private final PaperRepository paperRepository;

    public PaperService(PaperRepository paperRepository) {
        this.paperRepository = paperRepository;
    }

    public List<Paper> getAllPapers(){
        return paperRepository.findAll();
    }

    @SuppressWarnings("null")
    public Optional<Paper> getPaperById(String id) {
        return paperRepository.findById(id);
    }

    @SuppressWarnings("null")
    public void savePaper(Paper paper) {
        paperRepository.save(paper);
    }

     public List<Paper> getPapersByTags(List<String> tags) {
        return paperRepository.findByTagsIn(tags);
    }

    @SuppressWarnings("null")
    public void deletePaper(String id) {
        paperRepository.deleteById(id);
    }

    public List<Paper> getPapersByStatus(String status) {
        return paperRepository.findByStatus(status);
    }

    public List<Paper> getPapersByDecision(String decision) {
        return paperRepository.findByDecision(decision);
    }

    @SuppressWarnings("null")
    public void updatePaperDecision(String id, String newDecision) {
        Optional<Paper> optionalPaper = paperRepository.findById(id);

        if (optionalPaper.isPresent()) {
            Paper existingPaper = optionalPaper.get();

            paperRepository.deleteById(id);

            Paper updatedPaper = new Paper(
                existingPaper.getId(),
                existingPaper.getStatus(),
                existingPaper.getPaperName(),
                existingPaper.getRating(),
                existingPaper.getAuthorName(),
                existingPaper.getTags(),
                existingPaper.getReviews(),
                newDecision,
                existingPaper.getPdfFile()
             );

            paperRepository.save(updatedPaper);
        }
    }

    @SuppressWarnings("null")
    public void updatePaperPdfFile(String id, byte[] newPdfFile) {
        Optional<Paper> optionalPaper = paperRepository.findById(id);
    
        if (optionalPaper.isPresent()) {
            Paper existingPaper = optionalPaper.get();
    
            paperRepository.deleteById(id);
    
            Paper updatedPaper = new Paper(
                existingPaper.getId(),
                existingPaper.getStatus(),
                existingPaper.getPaperName(),
                existingPaper.getRating(),
                existingPaper.getAuthorName(),
                existingPaper.getTags(),
                existingPaper.getReviews(),
                existingPaper.getDecision(),
                newPdfFile  // Set the new pdfFile here
            );
    
            paperRepository.save(updatedPaper);
        }
    }

}
