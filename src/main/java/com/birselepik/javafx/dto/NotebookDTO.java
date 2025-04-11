package com.birselepik.javafx.dto;

import com.birselepik.javafx.utils.ERole;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor // Parametresiz Constructor
//@AllArgsConstructor // Parametreli Constructor
@Builder
@ToString

public class NotebookDTO {

    private Integer id;
    private String title;
    private String content;
    private String category;
    private Boolean pinned;
    private UserDTO username;
    public boolean isValid() {
        return title != null && category != null;
    }


    // Parametresiz Constructor
    // Parametreli Constructor
    public NotebookDTO(Integer id, String title, String content, String category, Boolean pinned, UserDTO username) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.category = category;
        this.pinned = pinned;
        this.username = username;
    }

    // Getter and Setter

}
