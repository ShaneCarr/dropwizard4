package com.witcher.bestiary;

import io.dropwizard.core.Configuration;
import io.dropwizard.db.DataSourceFactory;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;



public class BestiaryConfiguration extends Configuration {

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

  // Redis configuration
  @NotNull
  private String redisHost;

  @NotNull
  private int redisPort;

  @JsonProperty("redis")
  public void setRedisHost(String host) {
    this.redisHost = host;
  }

  @JsonProperty("redis")
  public String getRedisHost() {
    return redisHost;
  }

  @JsonProperty("redis")
  public void setRedisPort(int port) {
    this.redisPort = port;
  }

  @JsonProperty("redis")
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

  @JsonProperty("cosmos")
  public void setCosmosEndpoint(String endpoint) {
    this.cosmosEndpoint = endpoint;
  }

  @JsonProperty("cosmos")
  public String getCosmosEndpoint() {
    return cosmosEndpoint;
  }

  @JsonProperty("cosmos")
  public void setCosmosMasterKey(String masterKey) {
    this.cosmosMasterKey = masterKey;
  }

  @JsonProperty("cosmos")
  public String getCosmosMasterKey() {
    return cosmosMasterKey;
  }

  @JsonProperty("cosmos")
  public void setCosmosDatabaseName(String databaseName) {
    this.cosmosDatabaseName = databaseName;
  }

  @JsonProperty("cosmos")
  public String getCosmosDatabaseName() {
    return cosmosDatabaseName;
  }
}