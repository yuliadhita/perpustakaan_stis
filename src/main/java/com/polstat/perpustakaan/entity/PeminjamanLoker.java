package com.polstat.perpustakaan.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="peminjamanLoker")
public class PeminjamanLoker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="nim", nullable = false)
    private Mahasiswa mahasiswa;

    @ManyToOne
    @JoinTable(
            name = "peminjamanLoker_loker",
            joinColumns = @JoinColumn(name="peminjamanLoker_id"),
            inverseJoinColumns = @JoinColumn(name = "loker_id")
    )
    private Loker loker;

    @Column(nullable = false)
    private LocalDate tglPinjam;

    @Column(nullable = true)
    private LocalDate tglKembali;

    @Column(nullable = false)
    private String statusPeminjaman;

    @Column(nullable = true)
    private Integer terlambat;

}
