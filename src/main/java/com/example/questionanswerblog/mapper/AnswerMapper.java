package com.example.questionanswerblog.mapper;

import com.example.questionanswerblog.dto.response.AnswerResponseDTO;
import com.example.questionanswerblog.model.Answer;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AnswerMapper {

    private final ModelMapper modelMapper;

    public AnswerMapper(ModelMapper modelMapper){
        this.modelMapper = modelMapper;
    }

    // Map a single Answer to AnswerResponseDTO
    public AnswerResponseDTO convertToDTO(Answer answer){
        AnswerResponseDTO responseDTO = modelMapper.map(answer, AnswerResponseDTO.class);

        responseDTO.setId(answer.getId());
        responseDTO.setUsername(answer.getUser().getUsername());
        responseDTO.setQuestionTitle(answer.getQuestion().getTitle());

        return responseDTO;

    }

    // Map a list of Answers to a list of AnswerResponseDTOs using a for-loop
    public List<AnswerResponseDTO> convertToListDTO(List<Answer> answers) {

        List<AnswerResponseDTO> responseList = new ArrayList<>();

        if(answers!=null) {
            for (Answer answer : answers) {
                responseList.add(convertToDTO(answer)); // reuse single mapping
            }
        }
        return responseList;
    }
}
