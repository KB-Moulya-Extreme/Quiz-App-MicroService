package com.project.quiz_service.service;

import com.project.quiz_service.feign.QuizInterface;
import com.project.quiz_service.repository.QuizRepository;
import com.project.quiz_service.entity.QuestionWrapper;
import com.project.quiz_service.entity.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.project.quiz_service.entity.Quiz;

import java.util.List;

@Service
public class QuizService {

    //Many-to-many relationship
    @Autowired
    QuizRepository quizRepository;

    @Autowired
    QuizInterface quizInterface;

    public String createQuiz(String category, int numQ, String title) {
       // List<Integer> questions= call the generate url - RestTemplate http://localhost:8080/question/generate

        List<Integer> questions= quizInterface.getQuestionsForQuiz(category,numQ).getBody();
        Quiz quiz= new Quiz();
        quiz.setQuestionIds(questions);
        quiz.setTitle(title);
        quizRepository.save(quiz);
        return "success";
    }

    public List<QuestionWrapper> getQuizQuestions(int id) {
        Quiz quiz= quizRepository.findById(id).get();
        List<Integer> questionIds= quiz.getQuestionIds();
        List<QuestionWrapper> questionsForUser= quizInterface.getQuestionsFromId(questionIds).getBody();
        return questionsForUser;
    }

    public ResponseEntity<Integer> calculateResult(int id, List<Response> responses) {
        ResponseEntity<Integer> score= quizInterface.getScore(responses);
        return score;
    }
}
