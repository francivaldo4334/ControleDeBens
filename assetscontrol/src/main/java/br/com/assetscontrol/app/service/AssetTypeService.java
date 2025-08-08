package br.com.assetscontrol.app.service;

import java.util.List;

import javax.persistence.EntityManager;

import br.com.assetscontrol.app.model.AssetType;

public class AssetTypeService {
  private EntityManager manager;

  public AssetType save(AssetType assetType) {
    if (assetType.getId() == null) {
      manager.persist(assetType);
      return assetType;
    } else {
      return manager.merge(assetType);
    }
  }

  public AssetType getById(Long id) {
    return manager.find(AssetType.class, id);
  }

  public void delete(AssetType assetType) {
    assetType = getById(assetType.getId()); 
    manager.remove(assetType);
  }

  public List<AssetType> getAll() {
    return manager.createQuery("SELECT AssetType FROM AssetType", AssetType.class).getResultList();
  }
}
