package com.witcher.bestiary.service;

import com.witcher.bestiary.dao.MonsterDAO;
import com.witcher.bestiary.models.Monster;
import java.util.List;
import java.util.Optional;

public class MonsterService {
  private final MonsterDAO monsterDAO;

  public MonsterService(MonsterDAO monsterDAO) {
    this.monsterDAO = monsterDAO;
  }

  public List<Monster> getAllMonsters() {
    return monsterDAO.findAll();
  }

  public Optional<Monster> getMonsterById(int id) {
    return monsterDAO.findById(id);
  }

  public void addMonster(Monster monster) {
    monsterDAO.insert(monster);
  }

  public void updateMonster(Monster monster) {
    monsterDAO.update(monster);
  }

  public void deleteMonster(int id) {
    monsterDAO.delete(id);
  }
}