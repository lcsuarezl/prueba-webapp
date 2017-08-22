package co.facturalo.project.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;

import co.facturalo.project.core.AbstractBean;
import co.facturalo.project.model.AppIssue;

/**
 * Backing bean for Group entities.
 * <p>
 * This class provides CRUD functionality for all Group entities. It focuses
 * purely on Java EE 6 standards (e.g. <tt>&#64;ConversationScoped</tt> for
 * state management, <tt>PersistenceContext</tt> for persistence,
 * <tt>CriteriaBuilder</tt> for searches) rather than introducing a CRUD
 * framework or custom base class.
 */

@Named
@Stateful
@SessionScoped
public class AppIssueManager extends AbstractBean<AppIssue, Long> implements AppIssueManagerI {

	private static final long serialVersionUID = 2097477146200841404L;

	private Date startDate;

	private Date endDate;

	@Override
	public Predicate[] getSearchPredicates(Root<AppIssue> root) {
		CriteriaBuilder builder = this.em.getCriteriaBuilder();
		List<Predicate> predicatesList = new ArrayList<Predicate>();

		if (startDate != null) {
			predicatesList.add(builder.greaterThanOrEqualTo(root.<Date> get("created"), startDate));
		}

		if (endDate != null) {
			predicatesList.add(builder.lessThanOrEqualTo(root.<Date> get("created"), endDate));
		}

		if (this.instance.getId() != null) {
			predicatesList.add(builder.equal(root.<Long> get("id"), this.instance.getId()));
		}

		if (this.instance.getPath() != null) {
			predicatesList.add(builder.like(root.<String> get("report"), "%" + this.instance.getPath() + "%"));
		}

		return predicatesList.toArray(new Predicate[predicatesList.size()]);
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	@PostConstruct
	public void postConstruct() {

	}

	@Override
	public void preDestroy() {
		super.preDestroy();
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	@Override
	public void prepareCreate() {
		if (FacesContext.getCurrentInstance().isPostback()) {
			return;
		}
		// cargamos el path actual del error
		HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
				.getRequest();
		String url = req.getRequestURL().toString();
		this.instance.setPath(url);
		this.instance.setCreated(new Date());
		this.instance.setId(this.incrementId());
	}

	@Override
	public String afterCreate() {
		// limpiamos la instancia para preparar la busqueda
		this.instance = new AppIssue();
		return super.afterCreate();
	}

	@Override
	public boolean validateCreate() {
		return super.validateCreate();
	}

	@Override
	public void prepareUpdate() {
		if (FacesContext.getCurrentInstance().isPostback()) {
			return;
		}
		retrieve();
		this.instance.setVerified(new Date());
		super.prepareUpdate();
	}

	@Override
	public boolean validateUpdate() {
		return super.validateUpdate();
	}

}