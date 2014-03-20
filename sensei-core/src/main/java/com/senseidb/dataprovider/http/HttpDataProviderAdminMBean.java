package com.senseidb.dataprovider.http;

import proj.zoie.mbean.DataProviderAdminMBean;

public interface HttpDataProviderAdminMBean extends DataProviderAdminMBean {

  public long getHttpGetLatency();

  public long getResponseParseLatency();

}
