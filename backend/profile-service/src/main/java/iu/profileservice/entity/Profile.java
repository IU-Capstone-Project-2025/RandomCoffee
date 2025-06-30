package iu.profileservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Builder(toBuilder = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table
@Entity
public class Profile {

    @Id
    @Column(nullable = false, unique = true, updatable = false, insertable = false)
    private Long peerId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String surname;

    @Column(length = 3000, nullable = false)
    private String bio;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable
    private Set<Tag> tags;
}
