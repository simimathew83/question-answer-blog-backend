package com.example.questionanswerblog.mapper;


import com.example.questionanswerblog.dto.response.QuestionResponseDTO;
import com.example.questionanswerblog.model.Question;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


/**
 * Mapper class for converting Question entities to QuestionResponseDTOs.
 * Handles both single object mapping and list mapping using simple for-loops.
 */
@Component
public class QuestionMapper {

    private final ModelMapper modelMapper;
    private final AnswerMapper answerMapper;

    public QuestionMapper(ModelMapper modelMapper, AnswerMapper answerMapper){
        this.modelMapper = modelMapper;
        this.answerMapper = answerMapper;
    }

    // Convert a single Question entity to QuestionResponseDTO
    public QuestionResponseDTO convertToDTO(Question question){

        QuestionResponseDTO responseDTO = modelMapper.map(question, QuestionResponseDTO.class);

        responseDTO.setUsername(question.getUser().getUsername());
        responseDTO.setAnswers(answerMapper.convertToListDTO(question.getAnswers()));

        return responseDTO;

    }

    // Convert a list of Question entities to a list of QuestionResponseDTOs
    public List<QuestionResponseDTO> convertToListDTO(List<Question> questions){

        List<QuestionResponseDTO> responseList = new ArrayList<>();

        if(questions!=null) {
            for (Question question : questions) {
                responseList.add(convertToDTO(question));
            }
        }
        return responseList;
    }
}
