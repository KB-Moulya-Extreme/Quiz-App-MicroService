package com.project.quiz_service.controller;


import com.project.quiz_service.entity.QuizDto;
import com.project.quiz_service.entity.QuestionWrapper;
import com.project.quiz_service.entity.Response;
import com.project.quiz_service.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("quiz")
public class QuizController {

    @Autowired
    QuizService quizService;

    //Getting the questions and passing to Quiz table. No json passed, questions are randomly generated.
    @PostMapping("create")
    public ResponseEntity<String> createQuiz(@RequestBody QuizDto quizDto)
    {
        String q= quizService.createQuiz(quizDto.getCategoryName(),quizDto.getNumQuestions(),quizDto.getTitle());
        return new ResponseEntity<>(q, HttpStatus.CREATED);
    }

    //Gives the list of questions based on quiz id
    @GetMapping("get/{id}")
    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(@PathVariable int id)
    {
        List<QuestionWrapper> question= quizService.getQuizQuestions(id);
        return new ResponseEntity<>(question,HttpStatus.OK);
    }

    //Takes the quiz id and list of responses and outputs score
    @PostMapping("submit/{id}")
    public ResponseEntity<Integer> submitQuiz(@PathVariable int id,@RequestBody List<Response> responses)
    {
        return quizService.calculateResult(id,responses);
    }


}
