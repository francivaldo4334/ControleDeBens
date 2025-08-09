package br.com.assetscontrol.app.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import br.com.assetscontrol.app.model.Asset;

@Stateless
public class AssetService {
  @PersistenceContext(unitName = "clinicplusPU")
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
    return manager.createQuery("SELECT a FROM Asset a", Asset.class).getResultList();
  }

  public List<Asset> search(String search) {
    if (search == null || search.trim().isEmpty()) {
      return getAll();
    }
    CriteriaBuilder cb = manager.getCriteriaBuilder();
    CriteriaQuery<Asset> cq = cb.createQuery(Asset.class);
    Root<Asset> root = cq.from(Asset.class);

    String pattern = "%" + search.trim().toLowerCase() + "%";
    cq.where(cb.like(cb.lower(root.get("name")), pattern));

    return manager.createQuery(cq).getResultList();
  }
}
