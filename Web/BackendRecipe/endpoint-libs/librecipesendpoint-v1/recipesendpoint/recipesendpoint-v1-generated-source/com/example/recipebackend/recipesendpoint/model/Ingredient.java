/*
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
/*
 * This code was generated by https://github.com/google/apis-client-generator/
 * (build: 2016-07-08 17:28:43 UTC)
 * on 2016-08-29 at 14:43:27 UTC 
 * Modify at your own risk.
 */

package com.example.recipebackend.recipesendpoint.model;

/**
 * Model definition for Ingredient.
 *
 * <p> This is the Java data model class that specifies how to parse/serialize into the JSON that is
 * transmitted over HTTP when working with the recipesendpoint. For a detailed explanation see:
 * <a href="https://developers.google.com/api-client-library/java/google-http-java-client/json">https://developers.google.com/api-client-library/java/google-http-java-client/json</a>
 * </p>
 *
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public final class Ingredient extends com.google.api.client.json.GenericJson {

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key("id_ingredient")
  private java.lang.String idIngredient;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private Key key;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String name;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String nutritionaldesc;

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getIdIngredient() {
    return idIngredient;
  }

  /**
   * @param idIngredient idIngredient or {@code null} for none
   */
  public Ingredient setIdIngredient(java.lang.String idIngredient) {
    this.idIngredient = idIngredient;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public Key getKey() {
    return key;
  }

  /**
   * @param key key or {@code null} for none
   */
  public Ingredient setKey(Key key) {
    this.key = key;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getName() {
    return name;
  }

  /**
   * @param name name or {@code null} for none
   */
  public Ingredient setName(java.lang.String name) {
    this.name = name;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getNutritionaldesc() {
    return nutritionaldesc;
  }

  /**
   * @param nutritionaldesc nutritionaldesc or {@code null} for none
   */
  public Ingredient setNutritionaldesc(java.lang.String nutritionaldesc) {
    this.nutritionaldesc = nutritionaldesc;
    return this;
  }

  @Override
  public Ingredient set(String fieldName, Object value) {
    return (Ingredient) super.set(fieldName, value);
  }

  @Override
  public Ingredient clone() {
    return (Ingredient) super.clone();
  }

}
