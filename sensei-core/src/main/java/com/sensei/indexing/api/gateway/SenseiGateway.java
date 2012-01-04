package com.sensei.indexing.api.gateway;

import java.util.Comparator;
import java.util.Set;

import org.json.JSONObject;

import proj.zoie.impl.indexing.StreamDataProvider;
import proj.zoie.impl.indexing.ZoieConfig;

import com.sensei.conf.SenseiSchema;
import com.sensei.indexing.api.DataSourceFilter;
import com.sensei.indexing.api.ShardingStrategy;
import com.sensei.plugin.AbstractSenseiPlugin;
import com.sensei.plugin.SenseiPluginRegistry;

public abstract class SenseiGateway<V> extends AbstractSenseiPlugin {

  public static Comparator<String> DEFAULT_VERSION_COMPARATOR = ZoieConfig.DEFAULT_VERSION_COMPARATOR;

  final public DataSourceFilter<V> getDataSourceFilter(SenseiSchema senseiSchema, SenseiPluginRegistry pluginRegistry) {
    DataSourceFilter<V> dataSourceFilter = pluginRegistry.getBeanByFullPrefix("sensei.gateway.filter",
        DataSourceFilter.class);
    if (dataSourceFilter != null) {
      dataSourceFilter.setSrcDataStore(senseiSchema.getSrcDataStore());
      dataSourceFilter.setSrcDataField(senseiSchema.getSrcDataField());
      return dataSourceFilter;
    }
    return null;
  }

  final public StreamDataProvider<JSONObject> buildDataProvider(SenseiSchema senseiSchema, String oldSinceKey,
      SenseiPluginRegistry pluginRegistry, ShardingStrategy shardingStrategy, Set<Integer> partitions) throws Exception {
    DataSourceFilter<V> filter = getDataSourceFilter(senseiSchema, pluginRegistry);
    return buildDataProvider(filter, oldSinceKey, shardingStrategy, partitions);
  }

  abstract public StreamDataProvider<JSONObject> buildDataProvider(DataSourceFilter<V> dataFilter, String oldSinceKey,
      ShardingStrategy shardingStrategy, Set<Integer> partitions) throws Exception;

  abstract public Comparator<String> getVersionComparator();
}