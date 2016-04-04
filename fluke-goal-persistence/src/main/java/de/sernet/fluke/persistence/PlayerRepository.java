/*******************************************************************************
 * Copyright (c) 2016 Sebastian Hagedorn.
 *
 * This program is free software: you can redistribute it and/or 
 * modify it under the terms of the GNU Lesser General Public License 
 * as published by the Free Software Foundation, either version 3 
 * of the License, or (at your option) any later version.
 * This program is distributed in the hope that it will be useful,    
 * but WITHOUT ANY WARRANTY; without even the implied warranty 
 * of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  
 * See the GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program. 
 * If not, see <http://www.gnu.org/licenses/>.
 * 
 * Contributors:
 *     Sebastian Hagedorn <sh[at]sernet[dot]de> - initial API and implementation
 ******************************************************************************/
package de.sernet.fluke.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import de.sernet.fluke.interfaces.IPlayer;
/**
 * @author Sebastian Hagedorn <sh[at]sernet[dot]de>
 */
public interface PlayerRepository extends CrudRepository<Player, Long> {

	List<Player> findByLastName(@Param("name") String name);
	
	List<Player> findById(@Param("id") long id);
	
	@Query("select p from Player p where p.firstName = :firstName AND p.lastName = :lastName")
	List<Player> findByFullQualifiedName(@Param("lastName") String lastName, @Param("firstName") String firstName);
}
