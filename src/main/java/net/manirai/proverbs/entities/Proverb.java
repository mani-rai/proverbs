/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.manirai.proverbs.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Proverb domain entity.
 *
 * @author mani
 */
@Entity
@Table(name = "proverbs")
public class Proverb implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "proverb", nullable = false, length = 200, unique = true)
    private String phrase;

    @Lob
    @Column(nullable = false)
    private String meaning;

    @Column(length = 45)
    private String origin;

    @Column(length = 5)
    private String language;

    @OneToMany(mappedBy = "proverb")
    private List<Translation> translations;

    @OneToMany(mappedBy = "proverb")
    private List<Comment> comments;

//    Using unidirectional association for tags and category since, bidirectional association 
//    is not needed at the moment so avoiding unecessary mappings at the moment.
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "proverbs_tags",
            joinColumns = @JoinColumn(name = "proverb_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id", referencedColumnName = "id")
    )
    private List<Tag> tags;

    @ManyToMany
    @JoinTable(
            name = "proverbs_categories",
            joinColumns = @JoinColumn(name = "proverb_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "category_id", referencedColumnName = "id")
    )
    private List<Category> categories;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        
        this.id = id;
    }

    public String getPhrase() {
        return phrase;
    }

    public void setPhrase(String phrase) {
        this.phrase = phrase;
    }

    public String getMeaning() {
        return meaning;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public List<Translation> getTranslations() {
        return translations;
    }

    public void setTranslations(List<Translation> translations) {
        this.translations = translations;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }
}
