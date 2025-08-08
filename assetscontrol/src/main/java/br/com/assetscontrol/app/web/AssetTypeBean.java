package br.com.assetscontrol.app.web;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.assetscontrol.app.model.AssetType;
import br.com.assetscontrol.app.service.AssetTypeService;

@Named
@ViewScoped
public class AssetTypeBean implements Serializable {

  @Inject
  private AssetTypeService service;

  private List<AssetType> assetTypes;
  private AssetType assetTypeSelected;

  @PostConstruct
  public void init() {
    assetTypes = service.getAll();
    assetTypeSelected = new AssetType();
  }

  public void save() {
    service.save(assetTypeSelected);
    assetTypes = service.getAll();
    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Salvo com sucesso!"));
    assetTypeSelected = new AssetType();
  }

  public void delete(AssetType assetType) {
    service.delete(assetType);
    assetTypes = service.getAll();
    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Item Removido!"));
  }

  public List<AssetType> getAssetTypes() {
    return assetTypes;
  }
  public AssetType getAssetTypeSelected() {
    return assetTypeSelected;
  }
}
