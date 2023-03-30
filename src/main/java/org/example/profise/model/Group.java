package org.example.profise.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "groups")
public class Group {

    @Id
    @GeneratedValue
    @Getter @Setter
    private Long id;

    @NotNull
    @Getter @Setter
    protected String name;

    @Nullable
    @Getter @Setter
    protected String description;

    @OneToMany(mappedBy = "group", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @Getter
    List<Participant> participants = new ArrayList<>();

}
