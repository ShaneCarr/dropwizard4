package com.witcher.bestiary.models;

import java.util.Objects;

public class Monster {
  private int id;
  private String name;
  private String type;
  private String habitat;
  private String weaknesses;
  private String strengths;
  private String description;
  private String imageUrl; // For media storage
  private int difficultyLevel;
  private String lore; // Background lore, can be generated with NLP later

  // Default constructor
  public Monster() {}

  // Full constructor
  public Monster(int id, String name, String type, String habitat, String weaknesses,
                 String strengths, String description, String imageUrl, int difficultyLevel, String lore) {
    this.id = id;
    this.name = name;
    this.type = type;
    this.habitat = habitat;
    this.weaknesses = weaknesses;
    this.strengths = strengths;
    this.description = description;
    this.imageUrl = imageUrl;
    this.difficultyLevel = difficultyLevel;
    this.lore = lore;
  }

  // Getters and Setters
  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getHabitat() {
    return habitat;
  }

  public void setHabitat(String habitat) {
    this.habitat = habitat;
  }

  public String getWeaknesses() {
    return weaknesses;
  }

  public void setWeaknesses(String weaknesses) {
    this.weaknesses = weaknesses;
  }

  public String getStrengths() {
    return strengths;
  }

  public void setStrengths(String strengths) {
    this.strengths = strengths;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getImageUrl() {
    return imageUrl;
  }

  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }

  public int getDifficultyLevel() {
    return difficultyLevel;
  }

  public void setDifficultyLevel(int difficultyLevel) {
    this.difficultyLevel = difficultyLevel;
  }

  public String getLore() {
    return lore;
  }

  public void setLore(String lore) {
    this.lore = lore;
  }

  // Override equals and hashCode for object comparison
  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Monster monster = (Monster) o;
    return id == monster.id &&
            difficultyLevel == monster.difficultyLevel &&
            Objects.equals(name, monster.name) &&
            Objects.equals(type, monster.type) &&
            Objects.equals(habitat, monster.habitat) &&
            Objects.equals(weaknesses, monster.weaknesses) &&
            Objects.equals(strengths, monster.strengths) &&
            Objects.equals(description, monster.description) &&
            Objects.equals(imageUrl, monster.imageUrl) &&
            Objects.equals(lore, monster.lore);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, type, habitat, weaknesses, strengths, description, imageUrl, difficultyLevel, lore);
  }

  // Override toString for debugging
  @Override
  public String toString() {
    return "Monster{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", type='" + type + '\'' +
            ", habitat='" + habitat + '\'' +
            ", weaknesses='" + weaknesses + '\'' +
            ", strengths='" + strengths + '\'' +
            ", description='" + description + '\'' +
            ", imageUrl='" + imageUrl + '\'' +
            ", difficultyLevel=" + difficultyLevel +
            ", lore='" + lore + '\'' +
            '}';
  }
}