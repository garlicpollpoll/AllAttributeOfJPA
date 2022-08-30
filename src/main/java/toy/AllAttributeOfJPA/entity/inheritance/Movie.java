package toy.AllAttributeOfJPA.entity.inheritance;

import lombok.Getter;
import lombok.Setter;
import toy.AllAttributeOfJPA.entity.Item;

import javax.persistence.Entity;

@Entity
@Getter @Setter
public class Movie extends Item {

    private String director;
    private String actor;
}
