package com.sensei.search.nodes.impl;

import java.io.File;

import proj.zoie.api.DefaultDirectoryManager;
import proj.zoie.api.DirectoryManager;
import proj.zoie.api.DirectoryManager.DIRECTORY_MODE;
import proj.zoie.api.indexing.ZoieIndexableInterpreter;
import proj.zoie.impl.indexing.ZoieConfig;
import proj.zoie.impl.indexing.ZoieSystem;

import com.browseengine.bobo.api.BoboIndexReader;
import com.sensei.conf.ZoieFactoryFactory;
import com.sensei.search.nodes.SenseiIndexReaderDecorator;
import com.sensei.search.nodes.SenseiZoieFactory;
import com.sensei.search.nodes.SenseiZoieSystemFactory;

public class DemoZoieSystemFactory<T> extends SenseiZoieSystemFactory<T>
{
  private ZoieSystem<BoboIndexReader,T> _zoieSystem = null;
  
  public DemoZoieSystemFactory(File idxDir, ZoieIndexableInterpreter<T> interpreter, SenseiIndexReaderDecorator indexReaderDecorator,
                               ZoieConfig zoieConfig)
  {
    super(idxDir, DIRECTORY_MODE.SIMPLE, interpreter, indexReaderDecorator, zoieConfig);
  }
  
  @Override
  public ZoieSystem<BoboIndexReader,T> getZoieInstance(int nodeId,int partitionId)
  {
    if(_zoieSystem == null)
    {
      _zoieSystem = super.getZoieInstance(nodeId,partitionId);
    }
    return _zoieSystem;
  }
  
  @Override
  public File getPath(int nodeId,int partitionId)
  {
    return _idxDir;
  }
  
  public static class DemoZoieFactoryFactory implements ZoieFactoryFactory{

	@Override
	public SenseiZoieFactory<?> getZoieFactory(File idxDir,
			ZoieIndexableInterpreter<?> interpreter,
			SenseiIndexReaderDecorator decorator, ZoieConfig config) {
		return new DemoZoieSystemFactory(idxDir,interpreter,decorator,config);
	}
  }
}