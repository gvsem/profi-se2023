package org.example.profise.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "participants")
public class Participant {

    @Id
    @GeneratedValue
    @Getter @Setter
    private Long id;

    @NotNull
    @Getter @Setter
    protected String name;

    @Nullable
    @Getter @Setter
    protected String wish;

    @OneToOne(fetch = FetchType.LAZY, optional = true)
    @Getter @Setter
    protected Participant recipient;

    @ManyToOne(optional = false)
    @JoinColumn(name = "group_id")
    @Getter @Setter
    protected Group group;


}
