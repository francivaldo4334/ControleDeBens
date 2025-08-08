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
import br.com.assetscontrol.app.service.AssetService;

@Named
@ViewScoped
public class AssetBean implements Serializable {

  @Inject
  private AssetService service;

  private List<Asset> assets;
  private Asset assetSelected;

  @PostConstruct
  public void init() {
    assets = service.getAll();
    assetSelected = new Asset();
  }

  public void save() {
    service.save(assetSelected);
    assets = service.getAll();
    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Salvo com sucesso!"));
    assetSelected = new Asset();
  }

  public void delete(Asset asset) {
    service.delete(asset);
    assets = service.getAll();
    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Item Removido!"));
  }

}
