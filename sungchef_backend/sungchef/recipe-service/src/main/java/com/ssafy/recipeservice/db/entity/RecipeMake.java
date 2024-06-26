package com.ssafy.recipeservice.db.entity;;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "recipe_make")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecipeMake {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "recipe_make_id")
  private int recipeMakeId;

  @Column(name = "suser_sns_id")
  private String userSnsId;

  @Column(name = "recipe_id")
  private int recipeId;

  @Column(name = "recipe_make_create_date")
  private String recipeMakeCreateDate;

  @Column(name = "recipe_make_image")
  private String recipeMakeImage;

  @Column(name = "recipe_make_review")
  private String recipeMakeReview;
}
