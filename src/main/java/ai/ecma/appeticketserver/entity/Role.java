package ai.ecma.appeticketserver.entity;

import ai.ecma.appeticketserver.entity.template.AbsEntity;
import ai.ecma.appeticketserver.enums.PermissionEnum;
import ai.ecma.appeticketserver.enums.RoleEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "roles")
@Where(clause = "deleted=false")
@SQLDelete(sql = "update roles set deleted=true where id=?")
public class Role extends AbsEntity  {

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", columnDefinition = "text")
    private String description;

    @Enumerated(EnumType.STRING)
    @ElementCollection
    private Set<PermissionEnum> permissionEnums;

    @Enumerated(EnumType.STRING)
    private RoleEnum type;
}
