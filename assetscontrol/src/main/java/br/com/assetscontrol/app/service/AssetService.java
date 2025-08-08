package br.com.assetscontrol.app.service;


import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.com.assetscontrol.app.model.Asset;

@Stateless
public class AssetService {
  @PersistenceContext(unitName = "assetscontrol")
  private EntityManager manager;

  public Asset save(Asset asset) {
    if (asset.getId() == null) {
      manager.persist(asset);
      return asset;
    } else {
      return manager.merge(asset);
    }
  }

  public Asset getById(Long id) {
    return manager.find(Asset.class, id);
  }

  public void delete(Asset asset) {
    asset = getById(asset.getId()); 
    manager.remove(asset);
  }

  public List<Asset> getAll() {
    return manager.createQuery("SELECT Asset FROM Asset", Asset.class).getResultList();
  }
}
