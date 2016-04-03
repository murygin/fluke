package de.sernet.fluke.persistence;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import de.sernet.fluke.interfaces.IPlayer;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class Player implements IPlayer  {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
    
    private String firstName;
    private String lastName;

	/* (non-Javadoc)
     * @see de.sernet.fluke.persistence.IPlayer#getId()
     */
	@Override
    public long getId() {
        return id;
    }

    /* (non-Javadoc)
     * @see de.sernet.fluke.persistence.IPlayer#setId(long)
     */
    @Override
    public void setId(long id) {
        this.id = id;
    }

    /* (non-Javadoc)
     * @see de.sernet.fluke.persistence.IPlayer#getFirstName()
     */
    @Override
    public String getFirstName() {
		return firstName;
	}

    /* (non-Javadoc)
     * @see de.sernet.fluke.persistence.IPlayer#setFirstName(java.lang.String)
     */
    @Override
    public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

    /* (non-Javadoc)
     * @see de.sernet.fluke.persistence.IPlayer#getLastName()
     */
    @Override
    public String getLastName() {
		return lastName;
	}

    /* (non-Javadoc)
     * @see de.sernet.fluke.persistence.IPlayer#setLastName(java.lang.String)
     */
    @Override
    public void setLastName(String lastName) {
		this.lastName = lastName;
	}
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {    
        return getFirstName() + " " + getLastName();
    }
}
