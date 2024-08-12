package com.project.question_service.controller;


import com.project.question_service.entity.Question;
import com.project.question_service.entity.QuestionWrapper;
import com.project.question_service.entity.Response;
import com.project.question_service.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("question")
public class QuestionController {

    @Autowired
    QuestionService questionService;

    @GetMapping("allQuestions")
    public ResponseEntity<List<Question>> getAllQuestion(){
        List<Question> q= questionService.getAllQuestions();
        return new ResponseEntity<>(q, HttpStatus.OK);
    }

    @GetMapping("category/{category}")
    public ResponseEntity<List<Question>> getQuestionsByCategory(@PathVariable String category)
    {
        return new ResponseEntity<>(questionService.getQuestionsByCategory(category),HttpStatus.OK);
    }

    @PostMapping("add")
    public ResponseEntity<Question> createQuestion(@RequestBody Question q)
    {
       Question saved_q= questionService.createQuestion(q);
       return new ResponseEntity<>(saved_q,HttpStatus.CREATED);
    }

    @PutMapping("update/id")
    public ResponseEntity<Question> updateQuestion(@PathVariable Integer id,@RequestBody Question q)
    {
        Question saved_q= questionService.updateQuestion(id,q);
        return new ResponseEntity<>(saved_q,HttpStatus.CREATED);
    }

    @DeleteMapping("delete/id")
    public String deleteQuestion(@PathVariable Integer id)
    {
        questionService.deleteQuestion(id);
        return "Deleted successfully";
    }

    // generate- Returns randomly generated question ids.
    // getQuestions {questionid}- Takes questionids and outputs list of questionWrappers.
    // getScore- Takes list of

    @GetMapping("generate")
    public ResponseEntity<List<Integer>> getQuestionsForQuiz(@RequestParam String categoryName,@RequestParam int numQuestions)
    {
        return questionService.getQuestionsForQuiz(categoryName,numQuestions);
    }

    @PostMapping("getQuestions")
    public ResponseEntity<List<QuestionWrapper>> getQuestionsFromId(@RequestBody List<Integer> questionIds){
        return questionService.getQuestionsFromId(questionIds);
    }

    @PostMapping("getScore")
    public ResponseEntity<Integer> getScore(@RequestBody List<Response> responses)
    {
        return questionService.getScore(responses);
    }

}
