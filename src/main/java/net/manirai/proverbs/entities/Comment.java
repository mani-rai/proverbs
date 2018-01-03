/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.manirai.proverbs.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

/**
 * Comment domain enity.
 *
 * @author mani
 */
@Entity
@Table(name = "comments")
public class Comment implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column(nullable = false)
    @NotNull
    @NotEmpty
    private String comment;

    @Column(nullable = false, length = 45)
    @NotNull
    @NotEmpty
    @Length(min = 3, max = 45, message = "Name length must be within a range of 3 to 45.")
    private String name;

    @Column(nullable = false, length = 254)
    @NotNull
    @NotEmpty
    @Length(min = 5, max = 254, message = "Email length must be within a range of 5 to 254.")
    @Email
    private String email;

    @ManyToOne
    @JoinColumn(name = "proverb_id")
    private Proverb proverb;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Proverb getProverb() {
        return proverb;
    }

    public void setProverb(Proverb proverb) {
        this.proverb = proverb;
    }
}
