package com.polstat.perpustakaan.entity;

import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="loker")
public class Loker {
    @Id
    private String id;

    @Column(nullable = false)
    private Integer noLoker;
}
