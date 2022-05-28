package com.javabiz.auctionsite.service.auctionmanagement.model;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.OffsetDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Auction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    private UserModel owner;
    @NotNull
    @Size(min=3, max=20)
    private String title;
    @NotNull
    @Size(min=3, max=500, message = "{err.string.noteContent}")
    private String content;
    // If auction is active
    private boolean ongoing;
    @OneToOne
    private Offer winningOffer;
    @Column(name = "created_at")
    private OffsetDateTime createdAt;
    @Column(name = "updated_at")
    private OffsetDateTime updatedAt;

    //Only for initializer purpose
    public Auction(UserModel owner, String title, String content) {
        this.owner = owner;
        this.title = title;
        this.content = content;
        this.ongoing = true;
        this.winningOffer = null;
        this.createdAt = OffsetDateTime.now();
        this.updatedAt = OffsetDateTime.now();
    }
}
