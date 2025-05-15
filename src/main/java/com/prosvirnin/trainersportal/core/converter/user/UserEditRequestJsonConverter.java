package com.prosvirnin.trainersportal.core.converter.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.prosvirnin.trainersportal.model.dto.user.UserEditRequest;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.io.IOException;

@Converter
public class UserEditRequestJsonConverter implements AttributeConverter<UserEditRequest, String> {

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(UserEditRequest userEditRequest) {
        try {
            return mapper.writeValueAsString(userEditRequest);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Cannot serialize object", e);
        }
    }

    @Override
    public UserEditRequest convertToEntityAttribute(String s) {
        try {
            return mapper.readValue(s, UserEditRequest.class);
        } catch (IOException e) {
            throw new IllegalArgumentException("Cannot deserialize JSON", e);
        }
    }
}
