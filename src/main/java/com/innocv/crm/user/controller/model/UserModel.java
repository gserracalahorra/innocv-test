package com.innocv.crm.user.controller.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value = "User model", description = "Complete data of a model User")
public class UserModel {

    @ApiModelProperty(value = "The id of the user")
    private String id;

    @NotNull
    @NotEmpty
    @ApiModelProperty(value = "The name of the user", required = true)
    private String name;

    @NotNull
    @NotEmpty
    @ApiModelProperty(value = "The birthday of the user", required = true)
    private LocalDate birthday;

}