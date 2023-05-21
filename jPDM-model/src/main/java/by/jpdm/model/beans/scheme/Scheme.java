package by.jpdm.model.beans.scheme;

/**
 * Holds name and description of type model incremental update. Any kind of type
 * model update has to be related to a certain scheme.
 * 
 * @author Peter Laptik
 */
public class Scheme {
    // Base scheme has name 'default': top level super-types belong to the
    // 'default'-scheme. 'Default'-scheme cannot be changed
    public static final String DEFAULT_NAME = "default";

    private String fullName;
    private String description;

    public Scheme() {

    }

    public Scheme(String fullName, String description) {
        this.fullName = fullName;
        this.description = description;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    public boolean isDefault() {
        return fullName.equals(DEFAULT_NAME);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Scheme))
            return false;

        return this.getFullName().equals(((Scheme) obj).getFullName());
    }

    @Override
    public int hashCode() {
        return getFullName().hashCode();
    }
}
