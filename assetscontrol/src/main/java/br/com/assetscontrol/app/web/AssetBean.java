package br.com.assetscontrol.app.web;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.assetscontrol.app.model.Asset;
import br.com.assetscontrol.app.model.AssetType;
import br.com.assetscontrol.app.service.AssetService;
import br.com.assetscontrol.app.service.AssetTypeService;

@Named
@ViewScoped
public class AssetBean implements Serializable {

  @Inject
  private AssetService service;
  @Inject
  private AssetTypeService assetTypeService;

  private List<Asset> assets;
  private Asset assetSelected;
  private Long formAssetTypeSelected;
  private String searchName;

  @PostConstruct
  public void init() {
    loadNewItem();
    searchName = "";
  }

  public void loadNewItem() {
    formAssetTypeSelected = 0L;
    assetSelected = new Asset();
  }

  public void loadAssets() {
    assets = service.getAll();
  }

  public void save() {
    if (formAssetTypeSelected == null || formAssetTypeSelected <= 0) {
      FacesContext.getCurrentInstance().addMessage(null,
          new FacesMessage(FacesMessage.SEVERITY_ERROR, "Selecione um tipo de ativo!", null));
      return;
    }

    AssetType assetType = assetTypeService.getById(formAssetTypeSelected);
    if (assetType == null) {
      FacesContext.getCurrentInstance().addMessage(null,
          new FacesMessage(FacesMessage.SEVERITY_ERROR, "Tipo de ativo não encontrado!", null));
      return;
    }

    assetSelected.setAssetType(assetType);
    service.save(assetSelected);
    assets = service.getAll();
    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Salvo com sucesso!"));
    loadNewItem();
  }

  public void delete(Asset asset) {
    service.delete(asset);
    assets = service.getAll();
    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Item Removido!"));
  }

  public void loadUpdateId(Long id) {
    assetSelected = service.getById(id);
    formAssetTypeSelected = assetSelected.getAssetType().getId();
  }

  public List<Asset> getAssets() {
    return assets;
  }

  public Asset getAssetSelected() {
    return assetSelected;
  }

  public Long getFormAssetTypeSelected() {
    return formAssetTypeSelected;
  }

  public void setFormAssetTypeSelected(Long formAssetTypeSelected) {
    this.formAssetTypeSelected = formAssetTypeSelected;
  }

  public void setSearchName(String searchName) {
    this.searchName = searchName;
  }

  public String getSearchName() {
    return searchName;
  }

  public void search() {
    assets = service.search(searchName);
  }

}
