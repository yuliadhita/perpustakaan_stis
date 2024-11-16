package com.polstat.perpustakaan.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="mahasiswa")
public class Mahasiswa {
    @Id
    private String nim;

    @Column(nullable = false)
    private String nama;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String alamat;

    @Column(nullable = false)
    private String noHp;

}
