package hello.biblecashe.dto;

import hello.biblecashe.entity.Group;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class SignUpForm {
    private String userId;
    private String name;
    private Group group;

}
