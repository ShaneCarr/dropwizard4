package com.witcher.bestiary.dao;

import com.witcher.bestiary.models.Monster;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import java.util.List;
import java.util.Optional;

public interface MonsterDAO {

  @SqlUpdate("CREATE TABLE IF NOT EXISTS monsters (id SERIAL PRIMARY KEY, name VARCHAR(100), type VARCHAR(50), habitat VARCHAR(100), weaknesses TEXT, strengths TEXT, description TEXT, imageUrl TEXT, difficultyLevel INT, lore TEXT)")
  void createTable();

  @SqlUpdate("INSERT INTO monsters (name, type, habitat, weaknesses, strengths, description, imageUrl, difficultyLevel, lore) VALUES (:name, :type, :habitat, :weaknesses, :strengths, :description, :imageUrl, :difficultyLevel, :lore)")
  void insert(@BindBean Monster monster);

  @SqlQuery("SELECT * FROM monsters WHERE id = :id")
  Optional<Monster> findById(@Bind("id") int id);

  @SqlQuery("SELECT * FROM monsters")
  List<Monster> findAll();

  @SqlUpdate("UPDATE monsters SET name = :name, type = :type, habitat = :habitat, weaknesses = :weaknesses, strengths = :strengths, description = :description, imageUrl = :imageUrl, difficultyLevel = :difficultyLevel, lore = :lore WHERE id = :id")
  void update(@BindBean Monster monster);

  @SqlUpdate("DELETE FROM monsters WHERE id = :id")
  void delete(@Bind("id") int id);
}