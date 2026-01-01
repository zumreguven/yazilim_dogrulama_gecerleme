package com.example.careermanagement.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "is_basvurulari")
public class IsBasvurusu extends TemelVarlik {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "basvuran_id", nullable = false)
    private Kullanici basvuran;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ilan_id", nullable = false)
    private IsIlanlari ilan;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String ozgecmis;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BasvuruDurumu durum = BasvuruDurumu.BEKLEMEDE;

    @Column(columnDefinition = "TEXT")
    private String degerlendirmeNotu;
}