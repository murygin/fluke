package de.sernet.fluke.persistence;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import de.sernet.fluke.interfaces.IPlayer;

@Entity
public class Player implements IPlayer  {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
    
    @Column ( name = "firstName")
    private String firstName;
    @Column ( name = "lastName")
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

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Player other = (Player) obj;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (id != other.id)
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		return true;
	}
}
