package com.nitconf.backend.controller;

import com.nitconf.backend.models.Paper;
import com.nitconf.backend.security.services.PaperService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class PaperControllerTest {

    @Mock
    private PaperService paperService;

    @InjectMocks
    private PaperController paperController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllPapers() {
        List<Paper> mockPapers = new ArrayList<>();
        when(paperService.getAllPapers()).thenReturn(mockPapers);

        ResponseEntity<List<Paper>> response = paperController.getAllPapers();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockPapers, response.getBody());
    }

    @Test
    void testGetPaperById() {
        String paperId = "1";
        String samplePdfContent = "%PDF-1.4\n" +
            "1 0 obj\n" +
            "<< /Title (Sample PDF) /Creator (Your Application) >>\n" +
            "endobj\n" +
            "2 0 obj\n" +
            "<< /Type /Catalog /Pages 3 0 R >>\n" +
            "endobj\n" +
            "3 0 obj\n" +
            "<< /Type /Pages /Kids [4 0 R] /Count 1 >>\n" +
            "endobj\n" +
            "4 0 obj\n" +
            "<< /Type /Page /Parent 3 0 R /Contents 5 0 R /MediaBox [0 0 612 792] >>\n" +
            "endobj\n" +
            "5 0 obj\n" +
            "<< /Length 44 >>\n" +
            "stream\n" +
            "BT /F1 12 Tf 0 0 Td (Hello, this is a sample PDF file!) Tj ET\n" +
            "endstream\n" +
            "endobj";

        byte[] mocksampledata=samplePdfContent.getBytes();
        Paper mockPaper = new Paper("1", "Published", "Sample Paper", 5, "John Doe",
            List.of("tag1", "tag2"), new ArrayList<>(), "Accept", mocksampledata);
        when(paperService.getPaperById(paperId)).thenReturn(Optional.of(mockPaper));

        ResponseEntity<Paper> response = paperController.getPaperById(paperId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockPaper, response.getBody());
    }

    @Test
    void testSaveFile() throws IOException {
        String paperId = "1";
        byte[] pdfBytes = "Mock PDF Content".getBytes();
        MockMultipartFile mockMultipartFile = new MockMultipartFile("pdfFile", "test.pdf", "application/pdf", pdfBytes);
        doNothing().when(paperService).updatePaperPdfFile(paperId, pdfBytes);

        ResponseEntity<?> response = paperController.saveFile(mockMultipartFile, paperId);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Paper saved successfully", response.getBody());
    }

    @Test
    void testSaveData() {
        String samplePdfContent = "%PDF-1.4\n" +
            "1 0 obj\n" +
            "<< /Title (Sample PDF) /Creator (Your Application) >>\n" +
            "endobj\n" +
            "2 0 obj\n" +
            "<< /Type /Catalog /Pages 3 0 R >>\n" +
            "endobj\n" +
            "3 0 obj\n" +
            "<< /Type /Pages /Kids [4 0 R] /Count 1 >>\n" +
            "endobj\n" +
            "4 0 obj\n" +
            "<< /Type /Page /Parent 3 0 R /Contents 5 0 R /MediaBox [0 0 612 792] >>\n" +
            "endobj\n" +
            "5 0 obj\n" +
            "<< /Length 44 >>\n" +
            "stream\n" +
            "BT /F1 12 Tf 0 0 Td (Hello, this is a sample PDF file!) Tj ET\n" +
            "endstream\n" +
            "endobj";

        byte[] mocksampledata=samplePdfContent.getBytes();
        Paper mockPaper = new Paper("1", "Published", "Sample Paper", 5, "John Doe",
            List.of("tag1", "tag2"), new ArrayList<>(), "Accept", mocksampledata);
        doNothing().when(paperService).savePaper(mockPaper);

        paperController.saveData(mockPaper);

        verify(paperService, times(1)).savePaper(mockPaper);
    }

    @Test
    void testGetPapersByTags() {
        List<String> tags = List.of("tag1", "tag2");
        List<Paper> mockPapers = new ArrayList<>();
        when(paperService.getPapersByTags(tags)).thenReturn(mockPapers);

        ResponseEntity<List<Paper>> response = paperController.getPapersByTags(tags);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockPapers, response.getBody());
    }

    @Test
    void testDeletePaper() {
        String paperId = "1";
        doNothing().when(paperService).deletePaper(paperId);

        ResponseEntity<String> response = paperController.deletePaper(paperId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Paper deleted successfully", response.getBody());
    }

    @Test
    void testGetPapersByStatus() {
        String status = "Published";
        List<Paper> mockPapers = new ArrayList<>();
        when(paperService.getPapersByStatus(status)).thenReturn(mockPapers);

        ResponseEntity<List<Paper>> response = paperController.getPapersByStatus(status);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockPapers, response.getBody());
    }

    @Test
    void testGetPapersByDecision() {
        String decision = "Accept";
        List<Paper> mockPapers = new ArrayList<>();
        when(paperService.getPapersByDecision(decision)).thenReturn(mockPapers);

        ResponseEntity<List<Paper>> response = paperController.getPapersByDecision(decision);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockPapers, response.getBody());
    }

    @Test
    void testUpdatePaperDecision() {
        String paperId = "1";
        String newDecision = "Reject";
        doNothing().when(paperService).updatePaperDecision(paperId, newDecision);

        ResponseEntity<String> response = paperController.updatePaperDecision(paperId, newDecision);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Paper decision updated successfully", response.getBody());
    }
}

