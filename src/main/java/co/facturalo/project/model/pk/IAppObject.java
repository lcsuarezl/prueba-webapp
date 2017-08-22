package co.facturalo.project.model.pk;

/**
 * Id reserverd only for App level items, like master products, or master list,
 * b2cCustomer, etc.
 * 
 * @author leon
 * 
 */
public interface IAppObject {

	public Long getId();

	public void setId(Long tid);

}
