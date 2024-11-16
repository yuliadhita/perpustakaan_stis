package com.polstat.perpustakaan.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="peminjamanBuku")
public class PeminjamanBuku {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="nim", nullable = false)
    private Mahasiswa mahasiswa;

    @ManyToMany
    @JoinTable(
            name="peminjamanBuku_buku",
            joinColumns = @JoinColumn(name = "peminjamanBuku_id"),
            inverseJoinColumns = @JoinColumn(name = "buku_id")
    )
    private List<Buku> buku; // Change from Set<Buku> to List<Buku>

    @Column(nullable = false)
    private LocalDate tglPinjam;

    @Column(nullable = true)
    private LocalDate tglKembali;

    @Column(nullable = false)
    private String statusPeminjaman;

    @Column(nullable = true)
    private Integer terlambat;
}
