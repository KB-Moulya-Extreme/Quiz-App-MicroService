package com.project.question_service.service;

import com.project.question_service.entity.Question;
import com.project.question_service.entity.QuestionWrapper;
import com.project.question_service.entity.Response;
import com.project.question_service.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService{

    @Autowired
    QuestionRepository questionRepository;


    public List<Question> getAllQuestions()
    {
        List<Question> q= questionRepository.findAll();
        return q;
    }

    public List<Question> getQuestionsByCategory(String category) {
        return questionRepository.findByCategoryIgnoreCase(category);
    }

    public Question createQuestion(Question question) {
        return questionRepository.save(question);
    }

    public ResponseEntity<List<Integer>> getQuestionsForQuiz(String categoryName,Integer numQuestions) {
        List<Integer> questions = questionRepository.findRandomQuestionsByCategory(categoryName,numQuestions);
        return new ResponseEntity<>(questions, HttpStatus.OK);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuestionsFromId(List<Integer> questionIds) {
        List<QuestionWrapper> wrappers= new ArrayList<>();
        List<Question> questions= new ArrayList<>();

        for(Integer id:questionIds)
        {
            questions.add(questionRepository.findById(id).get());
        }
        for(Question q: questions)
        {
            QuestionWrapper wrapper= new QuestionWrapper();
            wrapper.setId(q.getId());
            wrapper.setQuestionTitle(q.getQuestionTitle());
            wrapper.setOption1(q.getOption1());
            wrapper.setOption2(q.getOption2());
            wrapper.setOption3(q.getOption3());
            wrapper.setOption4(q.getOption4());
            wrappers.add(wrapper);
        }
        return new ResponseEntity<>(wrappers,HttpStatus.OK);
    }

    public ResponseEntity<Integer> getScore(List<Response> responses) {

        int right=0;
        for(Response response: responses)
        {
            Question question= questionRepository.findById(response.getId()).get();
            if(response.getResponse().equalsIgnoreCase(question.getRightAnswer()))
            {
                right++;
            }
        }
        return new ResponseEntity<>(right, HttpStatus.OK);
    }

    public Question updateQuestion(Integer id,Question q) {
        Question question= questionRepository.findById(id).get();
        question.setQuestionTitle(q.getQuestionTitle());
        question.setDifficultylevel(q.getDifficultylevel());
        question.setRightAnswer(q.getRightAnswer());
        question.setOption1(q.getOption1());
        question.setOption2(q.getOption2());
        question.setOption3(q.getOption3());
        question.setOption4(q.getOption4());
        question.setCategory(q.getCategory());
        Question saved_q= questionRepository.save(question);
        return saved_q;
    }

    public void deleteQuestion(Integer id) {
        questionRepository.deleteById(id);
    }
}
