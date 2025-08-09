package br.com.assetscontrol.app.web;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.assetscontrol.app.model.AssetType;
import br.com.assetscontrol.app.service.AssetTypeService;

@SessionScoped
@Named
public class AssetTypeBean implements Serializable {

  @Inject
  private AssetTypeService service;

  private List<AssetType> assetTypes;
  private AssetType assetTypeSelected;
  private String searchName;

  @PostConstruct
  public void init() {
    loadNewItem();
  }

  public void loadAssetTypes() {
    assetTypes = service.getAll();
  }

  public void save() {
    service.save(assetTypeSelected);
    loadAssetTypes();
    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Salvo com sucesso!"));
    loadNewItem();
  }

  public void loadNewItem() {
    assetTypeSelected = new AssetType();
  }

  public void delete(AssetType assetType) {
    try {
      service.delete(assetType);
      loadAssetTypes();
      FacesContext.getCurrentInstance().addMessage(null,
          new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Tipo de ativo deletado."));
    } catch (IllegalStateException e) {
      FacesContext.getCurrentInstance().addMessage(null,
          new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", e.getMessage()));
    } catch (Exception e) {
      FacesContext.getCurrentInstance().addMessage(null,
          new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro inesperado", "Erro ao deletar item."));
    }
  }

  public List<AssetType> getAssetTypes() {
    return assetTypes;
  }

  public AssetType getAssetTypeSelected() {
    return assetTypeSelected;
  }

  public void setUpdateId(Long id) {
    assetTypeSelected = service.getById(id);
  }

  public void setSearchName(String searchName) {
    this.searchName = searchName;
  }

  public String getSearchName() {
    return searchName;
  }

  public void search() {
    assetTypes = service.search(searchName);
  }

}
