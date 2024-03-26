package com.ssafy.sample-service.db.entity;;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "recipe")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Recipe {

  @Column(name = "recipe_id")
  private int recipeId;
  @Column(name = "recipe_name")
  private String recipeName;
  @Column(name = "recipe_man_id")
  private String recipeManId;
  @Column(name = "recipe_description")
  private String recipeDescription;
  @Column(name = "recipe_image")
  private String recipeImage;
  @Column(name = "recipe_cooking_time")
  private String recipeCookingTime;
  @Column(name = "recipe_volume")
  private String recipeVolume;
  @Column(name = "recipe_visit_count")
  private int recipeVisitCount;
  @Column(name = "recipe_bookmark_count")
  private int recipeBookmarkCount;
  @Column(name = "recipe_type")
  private String recipeType;
  @Column(name = "recipe_situation")
  private String recipeSituation;
  @Column(name = "recipe_main_ingredient")
  private String recipeMainIngredient;
  @Column(name = "recipe_method")
  private String recipeMethod;
  @Column(name = "food_id")
  private int foodId;

}
