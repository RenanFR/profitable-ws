package com.profitable.ws.resource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.List;

import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;

import com.profitable.ws.model.entity.EntityType;
import com.profitable.ws.model.entity.ProfitableEntity;

public interface GenericController<E extends ProfitableEntity> {
	
	ResponseEntity<E> searchById(Long id);
	
	ResponseEntity<List<E>> list();
	
	ResponseEntity<E> save(E entity);
	
	ResponseEntity<Boolean> remove(Long id);
	
	default Link addSelfLink(GenericController<E> controller, ProfitableEntity entity) {
		return linkTo(methodOn(controller.getClass()).searchById(entity.getEntityId())).withSelfRel();
	}
	
	default Link addListLink(GenericController<E> controller, EntityType type) {
		return linkTo(methodOn(controller.getClass()).list()).withRel(String.format("List of all %s in the database", type.getDescription()));
	}	

}
