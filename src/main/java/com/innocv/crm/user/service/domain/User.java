package com.innocv.crm.user.service.domain;

import lombok.*;

import java.time.LocalDate;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private String id;

    private String name;

    private LocalDate birthday;

}