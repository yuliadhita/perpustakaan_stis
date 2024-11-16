package com.polstat.perpustakaan.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="buku")
public class Buku {
    @Id
    private String id;

    @Column(nullable = false)
    private String judul;

    @Column(nullable = false)
    private String penulis;

    @Column(nullable = false)
    private Integer tahun_terbit;

    @Column(nullable = false)
    private String deskripsi;

    @Column(nullable = false)
    private Integer jumlah;
}
