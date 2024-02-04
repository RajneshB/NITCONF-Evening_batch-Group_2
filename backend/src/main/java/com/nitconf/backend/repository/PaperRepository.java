package com.nitconf.backend.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.nitconf.backend.models.Paper;

public interface PaperRepository extends MongoRepository<Paper,String>{
    List<Paper> findByTagsIn(List<String> tags);
    List<Paper> findByStatus(String status);
    List<Paper> findByDecision(String decision);
}
