package br.com.assetscontrol.app.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import br.com.assetscontrol.app.model.AssetType;

@Stateless
public class AssetTypeService {

  @PersistenceContext(unitName = "clinicplusPU")
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

  private boolean hasAssets(Long assetTypeId) {
    Long count = manager.createQuery(
        "SELECT COUNT(a) FROM Asset a WHERE a.assetType.id = :id", Long.class)
        .setParameter("id", assetTypeId)
        .getSingleResult();
    return count > 0;
  }

  public void delete(AssetType assetType) {
    if (hasAssets(assetType.getId())) {
      throw new IllegalStateException("Cannot delete AssetType because it is referenced by existing Assets.");
    }
    AssetType managed = manager.find(AssetType.class, assetType.getId());
    if (managed != null) {
      manager.remove(managed);
    }
  }

  public List<AssetType> getAll() {
    return manager.createQuery("SELECT t FROM AssetType t", AssetType.class).getResultList();
  }

  public List<AssetType> search(String search) {
    if (search == null || search.trim().isEmpty()) {
      return getAll();
    }
    CriteriaBuilder cb = manager.getCriteriaBuilder();
    CriteriaQuery<AssetType> cq = cb.createQuery(AssetType.class);
    Root<AssetType> root = cq.from(AssetType.class);

    String pattern = "%" + search.trim().toLowerCase() + "%";
    cq.where(cb.like(cb.lower(root.get("name")), pattern));

    return manager.createQuery(cq).getResultList();
  }
}
