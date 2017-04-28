/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fitec.dba.dao;

import java.sql.ResultSet;
import java.util.List;

import fitec.dba.metier.Metier;

/**
 *
 * @author Fitec
 * 
 * Interface genérique qui prend en paramétre une classe héritant de metier
 */
public interface IDao<T extends Metier> {

	//   Le type (T) mentionn�e dans l'implémentation  remplacera tout les endroits où T se trouvera

	public List<T> selectAll();

	public ResultSet selectAllByResultSet();

	public T selectById(T objet);

	// Renvoie l'ID de l'objet crée
	public int insert(T objet);

	public boolean update(T objet);

	public boolean delete(T objet) throws Exception ;

	public List<T> searchLike(String str);

}
