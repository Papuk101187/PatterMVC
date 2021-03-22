package org.example.projectjspandjstl.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RequestRespone {

    private String name;
    private String description;
    private String date;

    private String username;
    private String userpassword;
    private String dateborn;



}
