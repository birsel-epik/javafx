package com.birselepik.javafx.dto;

import com.birselepik.javafx.utils.ERole;
import lombok.*;

// Lombok
@Getter
@Setter
//@AllArgsConstructor // Parametreli Constructor
@NoArgsConstructor  // Parametresiz Constructor
@ToString
@Builder

// UserDTO
public class UserDTO {
    // Field
    private Integer id;
    private String username;
    private String password;
    private String email;
    private ERole role;


    // Parametresiz Constructor
    // Parametreli Constructor

    public UserDTO(Integer id, String username, String password, String email, ERole role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
    }

    public UserDTO(String birsel, String birselEpik, String mail) {
    }


    // Getter And Setter
    // Method
    @Override
    public String toString() {
        return username;
    }




    /*
    public static void main(String[] args) {
        UserDTO userDTO= UserDTO.builder()
                .id(0)
                .username("username")
                .email("binokta@hotmail.com")
                .password("root")
                .build();

        System.out.println(userDTO);
    }
    */


} //end Class
