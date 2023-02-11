package by.jpdm.jsf.model;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import jakarta.inject.Named;
import jpdm.db.modeller.tree.ModelDriver;

/**
 * Managed bean for model.xhtml
 * 
 * @author Peter Laptik
 */
@Named
@ManagedBean
@ViewScoped
public class ModelManager implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private ModelDriver modelDriver;
    
    
}
