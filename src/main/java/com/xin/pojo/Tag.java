package com.xin.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "t_tag")
@Table
public class Tag {

    @Id
    @GeneratedValue
    private Long id;
    @NotEmpty(message = "非空，别开玩笑")
    private String name;

    @ManyToMany(mappedBy = "tags")
    private List<Blog> blogs= new ArrayList();

}
