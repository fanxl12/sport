package com.agitation.sport.entity;

import java.util.List;

import javax.persistence.Table;

//JPA标识
@Table(name = "sport_catalog")
public class Catalog extends BaseEntity {

	private Catalog parentCatalog;
	private List<Catalog> childCatalogs;
	private String url;

//	@ManyToOne
//	@JoinColumn(name = "parent_id") 
	public Catalog getParentCatalog() {
		return parentCatalog;
	}

	public void setParentCatalog(Catalog parentCatalog) {
		this.parentCatalog = parentCatalog;
	}

	public void setParentCatalogId(Long id){
		if(this.parentCatalog==null) parentCatalog = new Catalog();
		parentCatalog.setId(id);
	}
	
	public Long getParentCatalogId(){
		return (this.parentCatalog==null)?null:parentCatalog.getId();
	}

	public List<Catalog> getChildCatalogs() {
		return childCatalogs;
	}

	public void setChildCatalogs(List<Catalog> childCatalogs) {
		this.childCatalogs = childCatalogs;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
