package com.witcher.bestiary;

import io.dropwizard.core.Configuration;
import io.dropwizard.db.DataSourceFactory;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;



public class BestiaryConfiguration extends Configuration {

  @Valid
  @NotNull
  private String databaseName;

  @Valid
  @NotNull
  private DataSourceFactory database = new DataSourceFactory();

  @JsonProperty("database")
  public DataSourceFactory getDatabase() {
    return database;
  }

  @JsonProperty("database")
  public void setDatabase(DataSourceFactory database) {
    this.database = database;
  }

  @JsonProperty("databaseName")
  public String getDatabaseName() {
    return databaseName;
  }

  @JsonProperty("databaseName")
  public void setDatabaseName(String database) {
    this.databaseName = database;
  }

  // Redis configuration
  @NotNull
  private String redisHost;

  @NotNull
  private int redisPort;

  @JsonProperty("redisHost")
  public void setRedisHost(String host) {
    this.redisHost = host;
  }

  @JsonProperty("redisHost")
  public String getRedisHost() {
    return redisHost;
  }

  @JsonProperty("redisPort")
  public void setRedisPort(int port) {
    this.redisPort = port;
  }

  @JsonProperty("redisPort")
  public int getRedisPort() {
    return redisPort;
  }

  // Cosmos DB configuration
  @NotNull
  private String cosmosEndpoint;

  @NotNull
  private String cosmosMasterKey;

  @NotNull
  private String cosmosDatabaseName;

  @JsonProperty("cosmosEndpoint")
  public void setCosmosEndpoint(String endpoint) {
    this.cosmosEndpoint = endpoint;
  }

  @JsonProperty("cosmosEndpoint")
  public String getCosmosEndpoint() {
    return cosmosEndpoint;
  }

  @JsonProperty("cosmosMasterKey")
  public void setCosmosMasterKey(String masterKey) {
    this.cosmosMasterKey = masterKey;
  }

  @JsonProperty("cosmosMasterKey")
  public String getCosmosMasterKey() {
    return cosmosMasterKey;
  }

  @JsonProperty("cosmosDatabaseName")
  public void setCosmosDatabaseName(String databaseName) {
    this.cosmosDatabaseName = databaseName;
  }

  @JsonProperty("cosmosDatabaseName")
  public String getCosmosDatabaseName() {
    return cosmosDatabaseName;
  }
}